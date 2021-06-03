package com.arjuna.capstoneproject.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arjuna.capstoneproject.data.remote.ApiConfig
import com.arjuna.capstoneproject.data.remote.PostReportResponse
import com.arjuna.capstoneproject.data.remote.Result
import com.arjuna.capstoneproject.utils.Status
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReportViewModel : ViewModel() {

    private val _report = MutableLiveData<Status<String>>()
    val report: LiveData<Status<String>> = _report

    fun postReport(result: Result) {
        ApiConfig.reportApi().postReport(
            nik = result.nik,
            relation = result.relation,
            violence_type = result.violenceType,
            victim_age = result.victimAge,
            aggressor = result.aggressorAge,
            prev_abuse_report = result.prevReport,
            living_together = result.livingTogether,
            short_chronology = result.shortChronology
        ).enqueue(object : Callback<PostReportResponse> {
            override fun onResponse(
                call: Call<PostReportResponse>,
                response: Response<PostReportResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    _report.postValue(Status.Success("Success!"))
                } else {
                    _report.postValue(Status.Failure("Mohon lengkapi data laporan!"))
                }
            }

            override fun onFailure(call: Call<PostReportResponse>, t: Throwable) {
                _report.postValue(Status.Failure("${t.message}"))
            }

        })
    }
}