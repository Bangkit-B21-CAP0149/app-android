package com.arjuna.capstoneproject.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arjuna.capstoneproject.utils.Status
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class RegisterViewModel: ViewModel() {
    private val firebaseAuth = FirebaseAuth.getInstance()

    private val _registerStatus = MutableLiveData<Status<String>>()
    val registerStatus: LiveData<Status<String>> = _registerStatus

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun registerWithEmail(name: String, email: String, password: String) {
        _isLoading.value = true
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val request = UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build()
                    firebaseAuth.currentUser?.updateProfile(request)
                    firebaseAuth.signOut()
                    _registerStatus.postValue(Status.Success("Register Success"))
                } else {
                    _registerStatus.postValue(Status.Failure("Register Failed ${it.exception}"))
                }
                _isLoading.value = false
            }
    }
}