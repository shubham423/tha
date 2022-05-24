package com.example.tha

import android.content.Context
import android.content.SharedPreferences
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tha.utils.Constants.MAX_USERNAME_LENGTH
import com.example.tha.utils.Constants.MIN_USERNAME_LENGTH
import com.example.tha.utils.Extensions.isEmailValid
import com.example.tha.utils.Extensions.isNumberValid
import com.example.tha.utils.Extensions.isPasswordValid
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val applicationContext: Context,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {


    private val _registerStatus = MutableLiveData<String>()
    val registerStatus: LiveData<String> = _registerStatus

    private val _loginStatus = MutableLiveData<String>()
    val loginStatus: LiveData<String> = _loginStatus

    fun login(emailOrMobile: String, password: String) {
        if(emailOrMobile.isEmpty() || password.isEmpty()) {
            val error = applicationContext.getString(R.string.error_input_empty)
            _loginStatus.postValue(error)
        } else {
            val mobile = sharedPreferences.getString("mobile", "")
            val email = sharedPreferences.getString("email", "")
            val passwordSaved = sharedPreferences.getString("password", "")

            if ((emailOrMobile == mobile || emailOrMobile==email)&& password==passwordSaved){
                _loginStatus.postValue("success")
            }else{
                _loginStatus.postValue(applicationContext.getString(R.string.error_invalid_credentials))
            }
        }
    }

    fun register(email: String, username: String, password: String, mobile: String) {
        val error = if(email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            applicationContext.getString(R.string.error_input_empty)
        }
        else if(username.length < MIN_USERNAME_LENGTH) {
            applicationContext.getString(R.string.error_username_too_short, MIN_USERNAME_LENGTH)
        } else if(username.length > MAX_USERNAME_LENGTH) {
            applicationContext.getString(R.string.error_username_too_long, MAX_USERNAME_LENGTH)
        }
        else if (!mobile.isNumberValid()){
            applicationContext.getString(R.string.error_not_a_valid_mobile_no)
        }
        else if(!email.isEmailValid()) {
            applicationContext.getString(R.string.error_not_a_valid_email)
        }else if (!password.isPasswordValid()){
            applicationContext.getString(R.string.error_not_a_valid_password)
        }
        else null

        error?.let {
            _registerStatus.postValue(error as String?)
            return
        }
        if (error==null){
            sharedPreferences.edit().putString("email",email)
                .apply()

            sharedPreferences.edit().putString("mobile",email)
                .apply()

            sharedPreferences.edit().putString("password",password)
                .apply()

            _registerStatus.postValue("success")
        }
    }
}