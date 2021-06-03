package com.arjuna.capstoneproject.utils

sealed class Status<out T> {
    data class Success<out R>(val value: R): Status<R>()
    data class Failure<out R>(val message: String): Status<R>()
}
