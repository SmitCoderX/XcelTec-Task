package com.smitcoderx.learn.xceltecproject.Db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.smitcoderx.learn.xceltecproject.model.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: MutableList<Article>)

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

}