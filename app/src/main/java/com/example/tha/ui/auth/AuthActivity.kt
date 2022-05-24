package com.example.tha.ui.auth

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tha.ui.MainActivity
import com.example.tha.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mobile = sharedPreferences.getString("mobile", "")
        val email = sharedPreferences.getString("email", "")
        if (!(mobile.isNullOrEmpty() && email.isNullOrEmpty())) {
            startActivity(Intent(this, MainActivity::class.java)).also {
                finish()
            }
        }
    }
}