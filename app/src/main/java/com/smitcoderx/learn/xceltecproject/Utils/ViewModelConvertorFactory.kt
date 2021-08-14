package com.smitcoderx.learn.xceltecproject.Utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.smitcoderx.learn.xceltecproject.Repository.NewsRepository
import com.smitcoderx.learn.xceltecproject.ui.NewsViewModel

class ViewModelConvertorFactory(
    val app: Application,
    val newsRepository: NewsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(app, newsRepository) as T
    }
}