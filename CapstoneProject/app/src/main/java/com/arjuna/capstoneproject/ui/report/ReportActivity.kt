package com.arjuna.capstoneproject.ui.report

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.arjuna.capstoneproject.R
import com.arjuna.capstoneproject.data.remote.Result
import com.arjuna.capstoneproject.databinding.ActivityReportBinding
import com.arjuna.capstoneproject.ui.main.MainActivity
import com.arjuna.capstoneproject.utils.Status

class ReportActivity : AppCompatActivity() {

    private val binding: ActivityReportBinding by lazy {
        ActivityReportBinding.inflate(layoutInflater)
    }

    private val viewModel: ReportViewModel by viewModels()

    private var victimAge: String? = null
    private var violenceType: String? = null
    private var relation: String? = null
    private var aggressorAge: String? = null
    private var prevReport: String? = null
    private var livingTogether: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSubmit.setOnClickListener {
            if (binding.edtKtp.text.isNullOrEmpty()) {
                binding.edtKtp.error = "This field can not be blank!"
                binding.edtKtp.requestFocus()
                return@setOnClickListener
            }
            if (binding.edtShortChronology.text.isNullOrEmpty()) {
                binding.edtShortChronology.error = "This field can not be blank!"
                binding.edtShortChronology.requestFocus()
                return@setOnClickListener
            }
            if (binding.edtAge.text.isNullOrEmpty()) {
                binding.edtAge.error = "This field can not be blank!"
                binding.edtAge.requestFocus()
                return@setOnClickListener
            }
            submitReport()
        }
    }


    private fun getUserAnswer() {
        violenceType = when(binding.rgType.checkedRadioButtonId) {
            R.id.rbPhysical -> "Physical Abuse"
            R.id.rbPsychological -> "Psychological Abuse"
            R.id.rbSexual -> "Sexual Abuse"
            R.id.rbEmotional -> "Emotional Abuse"
            else -> "Unknown"
        }

        relation = when(binding.rgRelation.checkedRadioButtonId) {
            R.id.rbFamily -> "Family"
            R.id.rbStranger -> "Stranger"
            R.id.rbAcquaintance -> "Acquaintance"
            else -> "Unknown"
        }

        aggressorAge = when(binding.rgAggressorAge.checkedRadioButtonId) {
            R.id.rb16Years -> "<16 years"
            R.id.rb17Years -> "16-17 years"
            R.id.rb20Years -> "18-20 years"
            R.id.rb30Years -> "21-30 years"
            R.id.rb40Years -> "31-40 years"
            else -> "Unknown"
        }

        prevReport = when(binding.rgPrevReport.checkedRadioButtonId) {
            R.id.rbPrevReportYes -> "Yes"
            R.id.rbPrevReportNo -> "No"
            else -> "Unknown"
        }

        livingTogether = when(binding.rgLivingTogether.checkedRadioButtonId) {
            R.id.rbLivingTogetherYes -> "Yes"
            R.id.rbLivingTogetherNo -> "No"
            else -> "Unknown"
        }

        if (binding.edtAge.text.toString().isNotEmpty()) {
            victimAge = when(Integer.valueOf(binding.edtAge.text.toString())) {
                in 0..15 -> "<16 years"
                in 16..17 -> "16-17 years"
                in 18..20 -> "18-20 years"
                in 21..30 -> "21-30 years"
                in 31..40 -> "31-40 years"
                in 41..50 -> "41-50 years"
                in 51..64 -> "51-64 years"
                in 65..74 -> "65-74 years"
                in 75..84 -> "75-84 years"
                else -> ">85 years"
            }
        }

    }

    private fun submitReport() {
        getUserAnswer()
        val nik = binding.edtKtp.text
        val shortChronology = binding.edtShortChronology.text
        val reportValue = Result(
            nik = nik.toString(),
            relation = relation.toString(),
            violenceType = violenceType.toString(),
            victimAge = victimAge.toString(),
            aggressorAge = aggressorAge.toString(),
            prevReport = prevReport.toString(),
            livingTogether = livingTogether.toString(),
            shortChronology = shortChronology.toString()
        )
        viewModel.postReport(reportValue)
        viewModel.report.observe(this, { result ->
            result?.let {
                when (it) {
                    is Status.Success -> {
                        val intent = Intent(this@ReportActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    is Status.Failure -> Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}