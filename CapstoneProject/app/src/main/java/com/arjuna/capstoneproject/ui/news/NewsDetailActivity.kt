package com.arjuna.capstoneproject.ui.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arjuna.capstoneproject.R
import com.arjuna.capstoneproject.databinding.ActivityNewsBinding

class NewsDetailActivity : AppCompatActivity() {

    private  val binding: ActivityNewsBinding by lazy {
        ActivityNewsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }
}