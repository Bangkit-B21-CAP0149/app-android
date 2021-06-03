package com.arjuna.capstoneproject.ui.auth

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.arjuna.capstoneproject.R
import com.arjuna.capstoneproject.databinding.ActivityRegisterBinding
import com.arjuna.capstoneproject.utils.Status
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.functions.Function3
import io.reactivex.functions.Function4

class RegisterActivity : AppCompatActivity() {

    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    private val viewModel: RegisterViewModel by viewModels()

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.title = "Register"

        observeLoading()

        val nameStream = RxTextView.textChanges(binding.edtName)
            .skipInitialValue()
            .map { name ->
                name.isEmpty()
            }
        nameStream.subscribe {
            showNameAlert(it)
        }

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
            nameStream,
            emailStream,
            passwordStream,
            passwordConfirmationStream,
            Function4 { nameEmpty: Boolean, emailInvalid: Boolean, passwordInvalid: Boolean, passwordConfirmationInvalid: Boolean ->
                !nameEmpty && !emailInvalid && !passwordInvalid && !passwordConfirmationInvalid
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
        val name = binding.edtName.text.toString()
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()

        viewModel.registerWithEmail(name, email, password)
        observeRegisterWithEmail()
    }

    private fun observeRegisterWithEmail() {
        viewModel.registerStatus.observe(this, { status ->
            status?.let {
                when(it) {
                    is Status.Success -> {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    is Status.Failure -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun observeLoading() {
        viewModel.isLoading.observe(this, {
            binding.btnRegister.text = if (it) "Authenticating..." else "Register"
        })
    }

    private fun showNameAlert(isEmpty: Boolean) {
        binding.edtEmail.error = if (isEmpty) "Nama tidak boleh kosong!" else null
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