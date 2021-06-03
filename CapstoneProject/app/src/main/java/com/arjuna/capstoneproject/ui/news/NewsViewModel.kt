package com.arjuna.capstoneproject.ui.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arjuna.capstoneproject.data.remote.ApiConfig
import com.arjuna.capstoneproject.data.remote.ArticlesItem
import com.arjuna.capstoneproject.data.remote.NewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {
    private var _newsList = MutableLiveData<List<ArticlesItem>>()
    val newsList: LiveData<List<ArticlesItem>> = _newsList

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getNews() {
        _isLoading.value = true
        ApiConfig.getNews().getNews().enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    _isLoading.value = false

                    val news = response.body()?.articles

                    _newsList.postValue(news)
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Log.d("onFailure: ", t.message.toString())
            }
        })
    }
}