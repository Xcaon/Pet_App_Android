package com.fernando.petapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.fernando.petapp.databinding.ActivityLoginBinding
import com.fernando.petapp.databinding.ActivityMainBinding

class activityPerfil : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        var logoutText = findViewById<TextView>(R.id.logout)

        logoutText.setOnClickListener {
            val intent = Intent(this, activityLogin::class.java)
            startActivity(intent)
        }
    }


}