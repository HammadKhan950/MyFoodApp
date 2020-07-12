package com.Hammadkhan950.myfoodapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ForgotPasswordViewActivity : AppCompatActivity() {
    lateinit var tvEmail: TextView
    lateinit var tvMobileNumber: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password_view)
        tvEmail = findViewById(R.id.tvEmail)
        tvMobileNumber = findViewById(R.id.tvMobileNumber)
        val mobileNumber = intent.getStringExtra("MobileNumber")
        val email = intent.getStringExtra("Email")
        tvMobileNumber.text = mobileNumber
        tvEmail.text = email
    }
}