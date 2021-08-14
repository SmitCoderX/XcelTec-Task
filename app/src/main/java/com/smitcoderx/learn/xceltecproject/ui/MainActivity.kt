package com.smitcoderx.learn.xceltecproject.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.smitcoderx.learn.xceltecproject.Db.ArticleDatabase
import com.smitcoderx.learn.xceltecproject.R
import com.smitcoderx.learn.xceltecproject.Repository.NewsRepository
import com.smitcoderx.learn.xceltecproject.Utils.ViewModelConvertorFactory
import com.smitcoderx.learn.xceltecproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelConvertorFactory = ViewModelConvertorFactory(application, newsRepository)
        viewModel =
            ViewModelProvider(this, viewModelConvertorFactory)[NewsViewModel::class.java]
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}