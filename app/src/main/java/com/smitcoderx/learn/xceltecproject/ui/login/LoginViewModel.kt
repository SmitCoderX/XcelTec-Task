package com.smitcoderx.learn.xceltecproject.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    var username: String = ""
    var password: String = ""

    private val logInResult = MutableLiveData<String>()

    fun getLoginResult(): LiveData<String> = logInResult

    fun performValidation(): Boolean {

        if (username.isBlank() && password.isBlank()) {
            logInResult.value = "Field's Cannot be Empty"
            return false
        } else if (username != "test@android.com") {
            logInResult.value = "Invalid Username"
            return false
        } else if (password != "123456") {
            logInResult.value = "Invalid Password"
            return false
        }
        logInResult.value = "Valid Credentials"
        return true
    }
}