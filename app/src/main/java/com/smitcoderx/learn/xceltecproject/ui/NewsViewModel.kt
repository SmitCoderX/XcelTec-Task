package com.smitcoderx.learn.xceltecproject.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.smitcoderx.learn.xceltecproject.Repository.NewsRepository
import com.smitcoderx.learn.xceltecproject.Utils.MyApplication
import com.smitcoderx.learn.xceltecproject.Utils.Resource
import com.smitcoderx.learn.xceltecproject.model.Article
import com.smitcoderx.learn.xceltecproject.model.NewsResponse
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class NewsViewModel(
    app: Application,
    val newsRepository: NewsRepository
) : AndroidViewModel(app) {

    val itNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var itNewsResponse: NewsResponse? = null


    init {
        getNews()
    }

    fun getNews() = viewModelScope.launch {
        safeSearchNewsCall()
    }

    private fun handleItNews(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if (itNewsResponse == null) {
                    itNewsResponse = resultResponse
                } else {
                    val oldArticle = itNewsResponse?.articles
                    val newArticle = resultResponse.articles
                    oldArticle?.addAll(newArticle)
                }
                return Resource.Success(itNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


    private suspend fun safeSearchNewsCall() {
        itNews.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = newsRepository.getItNews()
                itNews.postValue(handleItNews(response))
            } else {
                itNews.postValue(Resource.Error("No Internet Connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> itNews.postValue(Resource.Error("Network Failure"))
                else -> itNews.postValue(Resource.Error("Conversion Error: ${t.message}"))
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<MyApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

    fun saveNews(article: MutableList<Article>) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun allNews() = newsRepository.getSaved()
}