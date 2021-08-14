package com.smitcoderx.learn.xceltecproject.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.smitcoderx.learn.xceltecproject.R
import com.smitcoderx.learn.xceltecproject.databinding.ActivityLoginBinding
import com.smitcoderx.learn.xceltecproject.ui.MainActivity

class LoginActivity : AppCompatActivity(), LoginHandler {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val binding =
            DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)

        viewModel =
            ViewModelProvider(this)[LoginViewModel::class.java]

        binding.viewModel = viewModel
        binding.handler = this


    }

    override fun onLoginClicked() {
        if (viewModel.performValidation()) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            viewModel.getLoginResult().observe(this, { result ->
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
            })
        }
    }
}