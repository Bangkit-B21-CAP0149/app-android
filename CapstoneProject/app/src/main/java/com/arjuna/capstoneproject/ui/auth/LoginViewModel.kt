package com.arjuna.capstoneproject.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arjuna.capstoneproject.utils.Status
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {
    private val firebaseAuth = FirebaseAuth.getInstance()

    private val _authStatus = MutableLiveData<Status<String>>()
    val authStatus: LiveData<Status<String>> = _authStatus

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun loginWithEmail(email: String, password: String) {
        _isLoading.value = true
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authStatus.postValue(Status.Success("Login Success"))
                } else {
                    _authStatus.postValue(Status.Failure("Login Failed ${task.exception}"))
                }
                _isLoading.value = false
            }
    }
}