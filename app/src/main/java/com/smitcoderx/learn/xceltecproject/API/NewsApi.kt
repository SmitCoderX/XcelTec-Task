package com.smitcoderx.learn.xceltecproject.API

import com.smitcoderx.learn.xceltecproject.Utils.Constants.APIKEY
import com.smitcoderx.learn.xceltecproject.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/everything")
    suspend fun getNews(
        @Query("q") searchQuery: String = "IT",
        @Query("from") fromDate: String = "2021-08-14",
        @Query("sortBy") sort: String = "publishedAt",
        @Query("apiKey") apiKey: String = APIKEY
    ): Response<NewsResponse>

}