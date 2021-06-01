package com.arjuna.capstoneproject.data.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FaqEntity(
    var id: Int,
    var questions: String,
    var answerFaq: String
) : Parcelable