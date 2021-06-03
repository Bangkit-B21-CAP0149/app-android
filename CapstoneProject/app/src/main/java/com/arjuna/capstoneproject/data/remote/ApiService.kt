package com.arjuna.capstoneproject.data.remote

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("everything?q=perempuan+dan+anak&language=id&apiKey=39b56226edf1424285d3b9110187c035")
    fun getNews(): Call<NewsResponse>

    @FormUrlEncoded
    @POST("vr/input_report")
    fun postReport(
        @Field("nik") nik: String,
        @Field("relation") relation: String,
        @Field("violence_type") violence_type: String,
        @Field("victim_age") victim_age: String,
        @Field("agressor_age") aggressor: String,
        @Field("previous_abuse_report") prev_abuse_report: String,
        @Field("living_together") living_together: String,
        @Field("short_chronology") short_chronology: String
    ): Call<PostReportResponse>
}
