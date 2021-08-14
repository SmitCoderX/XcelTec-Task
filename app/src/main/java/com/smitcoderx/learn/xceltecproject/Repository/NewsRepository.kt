package com.smitcoderx.learn.xceltecproject.Repository

import androidx.lifecycle.MutableLiveData
import com.smitcoderx.learn.xceltecproject.API.RetrofitInstance
import com.smitcoderx.learn.xceltecproject.Db.ArticleDatabase
import com.smitcoderx.learn.xceltecproject.model.Article

class NewsRepository(
    val db: ArticleDatabase
) {

    suspend fun getItNews() =
        RetrofitInstance.api.getNews()

    suspend fun upsert(article: MutableList<Article>) = db.getArticleDao().upsert(article)

    fun getSaved() = db.getArticleDao().getAllArticles()
}