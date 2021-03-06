package com.arjuna.capstoneproject.ui.auth

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.arjuna.capstoneproject.R
import com.arjuna.capstoneproject.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.functions.Function3

class RegisterActivity : AppCompatActivity() {

    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val emailStream = RxTextView.textChanges(binding.edtEmail)
            .skipInitialValue()
            .map { email ->
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }

        emailStream.subscribe {
            showEmailAlert(it)
        }

        val passwordStream = RxTextView.textChanges(binding.edtPassword)
            .skipInitialValue()
            .map { password->
                password.length < 6
            }
        passwordStream.subscribe {
            showPasswordAlert(it)
        }

        val passwordConfirmationStream = Observable.merge(
            RxTextView.textChanges(binding.edtPassword)
                .map { password ->
                    password.toString() != binding.edtRePassword.text.toString()
                },
            RxTextView.textChanges(binding.edtRePassword)
                .map { confirmPassword ->
                    confirmPassword.toString() != binding.edtPassword.text.toString()
                }
        )
        passwordConfirmationStream.subscribe {
            showPasswordConfirmAlert(it)
        }

        val invalidFieldsStream = Observable.combineLatest(
            emailStream,
            passwordStream,
            passwordConfirmationStream,
            Function3 { emailInvalid: Boolean, passwordInvalid: Boolean, passwordConfirmationInvalid: Boolean ->
                !emailInvalid && !passwordInvalid && !passwordConfirmationInvalid
            }
        )
        invalidFieldsStream.subscribe { isValid ->
            if (isValid) {
                binding.btnRegister.isEnabled = true
                binding.btnRegister.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_500))

                binding.btnRegister.setOnClickListener {
                    signUpFlow()
                }
            } else {
                binding.btnRegister.isEnabled = false
                binding.btnRegister.setBackgroundColor(ContextCompat.getColor(this, R.color.common_google_signin_btn_text_dark_disabled))
            }
        }
    }

    private fun signUpFlow() {
        val firebaseAuth = FirebaseAuth.getInstance()

        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Log.d("SignUpWithEmail", "createUserWithEmail: success")
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.w("SignUpWithEmail", "createUserWithEmail: failed")
                    Toast.makeText(baseContext, "Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun showEmailAlert(isNotValid: Boolean) {
        binding.edtEmail.error = if (isNotValid) "Email tidak valid!" else null
    }

    private fun showPasswordAlert(isNotValid: Boolean) {
        binding.edtPassword.error = if (isNotValid) "Password kurang dari 6 karakter!" else null
    }

    private fun showPasswordConfirmAlert(isNotValid: Boolean) {
        binding.edtRePassword.error = if (isNotValid) "Password tidak sama!" else null
    }
}