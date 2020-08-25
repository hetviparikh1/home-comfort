package com.example.homecomfort

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}