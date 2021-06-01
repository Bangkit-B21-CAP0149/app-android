package com.arjuna.capstoneproject.ui.faq

import com.arjuna.capstoneproject.data.local.entity.FaqEntity

interface FaqCallback {
    fun onItemClicked(dataFaq: FaqEntity)
}