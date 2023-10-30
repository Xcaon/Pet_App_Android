package com.fernando.petapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fernando.petapp.databinding.ActivityLoginBinding

class activityLogin : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}