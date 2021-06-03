package com.arjuna.capstoneproject.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.arjuna.capstoneproject.R
import com.arjuna.capstoneproject.databinding.ActivityMainBinding
import com.arjuna.capstoneproject.ui.faq.FaqFragment
import com.arjuna.capstoneproject.ui.home.HomeFragment
import com.arjuna.capstoneproject.ui.news.NewsFragment
import com.arjuna.capstoneproject.ui.profile.ProfileFragment
import com.arjuna.capstoneproject.ui.report.ReportActivity

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }

        binding.bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home_nav -> loadFragment(HomeFragment())
                R.id.news_nav -> loadFragment(NewsFragment())
                R.id.profile_nav -> loadFragment(ProfileFragment())
                R.id.faq_nav -> loadFragment(FaqFragment())
                R.id.report_nav -> {
                    val intent = Intent(Intent(this@MainActivity, ReportActivity::class.java))
                    startActivity(intent)
                }
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit()
            return true
        }
        return false
    }
}