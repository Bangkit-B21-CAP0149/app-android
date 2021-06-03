package com.arjuna.capstoneproject.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.arjuna.capstoneproject.R
import com.arjuna.capstoneproject.databinding.ActivityLoginBinding
import com.arjuna.capstoneproject.ui.main.MainActivity
import com.arjuna.capstoneproject.utils.Status
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.*

class LoginActivity : AppCompatActivity() {

    private val firebaseAuth = FirebaseAuth.getInstance()

    private val viewModel: LoginViewModel by viewModels()

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    companion object {
        const val SIGN_IN_RESULT_CODE = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.title = "Login"

        if (firebaseAuth.currentUser != null) {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        observeLoading()

        binding.btnLogin.setOnClickListener {

            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            if (email.isEmpty()) {
                binding.edtEmail.error = "This field can not be blank"
                binding.edtEmail.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.edtPassword.error = "This field can not be blank"
                binding.edtPassword.requestFocus()
                return@setOnClickListener
            }
            loginFlow(email, password)
        }

        binding.btnLoginWithGoogle.setOnClickListener {
            googleSignIn()
        }
        binding.txtRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SIGN_IN_RESULT_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                Log.d("ActivityResult", "authWithGoogle: ${account?.id}")
                account?.idToken?.let { signInWithGoogle(it) }
            } catch (e: Exception) {
                Log.d("ActivityResult", "authWithGoogle: failed", e)
                Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun googleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)

        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, SIGN_IN_RESULT_CODE)
    }

    private fun signInWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Log.d("SignInWithGoogle", "signInWithGoogle: success")
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Log.w("FirebaseAuth", "signInWithEmail:failure", it.exception)
                    Toast.makeText(
                        applicationContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun loginFlow(email: String, password: String) {
        viewModel.loginWithEmail(email, password)
        observeLoginWithEmail()
    }

    private fun observeLoginWithEmail() {
        viewModel.authStatus.observe(this, { status ->
            status?.let {
                when (it) {
                    is Status.Success -> {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    is Status.Failure -> {
                        Log.e("TAG", it.message)
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                        binding.btnLogin.text = "Login"
                    }
                }
            }
        })
    }

    private fun observeLoading() {
        viewModel.isLoading.observe(this, {
            binding.btnLogin.text = if (it) "Authenticating..." else "Login"
        })
    }

}