package com.arjuna.capstoneproject.data.remote

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("everything?q=kekerasan+perempuan&language=id&apiKey=39b56226edf1424285d3b9110187c035")
    fun getNews(): Call<NewsResponse>
}
