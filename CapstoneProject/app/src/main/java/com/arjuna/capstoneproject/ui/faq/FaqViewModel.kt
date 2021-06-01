package com.arjuna.capstoneproject.ui.faq

import androidx.lifecycle.ViewModel
import com.arjuna.capstoneproject.data.local.entity.FaqEntity
import com.arjuna.capstoneproject.utils.FaqDummy

class FaqViewModel : ViewModel() {
    fun getListFaq(): List<FaqEntity> = FaqDummy.generateFaqDummy()
}