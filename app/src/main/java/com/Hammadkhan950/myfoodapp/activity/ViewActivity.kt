package com.Hammadkhan950.myfoodapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.Hammadkhan950.myfoodapp.R

class ViewActivity : AppCompatActivity() {
    lateinit var tvMobileNumber: TextView
    lateinit var tvPassword: TextView
    lateinit var tvProfile: TextView
    lateinit var tvEmail: TextView
    lateinit var tvConfirmPassword: TextView
    lateinit var tvAddress: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        tvMobileNumber = findViewById(R.id.tvMobileNumber)
        tvPassword = findViewById(R.id.tvPassword)
        tvProfile = findViewById(R.id.tvProfile)
        tvEmail = findViewById(R.id.tvemail)
        tvConfirmPassword = findViewById(R.id.tvconfirmPassword)
        tvAddress = findViewById(R.id.tvAddress)
        val mobileNumber = intent.getStringExtra("mobileNumber")
        val password = intent.getStringExtra("password")
        val profile = intent.getStringExtra("profile")
        val email = intent.getStringExtra("email")
        val address = intent.getStringExtra("address")
        val ConfirmPassword = intent.getStringExtra("confirmPassword")

        tvMobileNumber.text = mobileNumber
        tvPassword.text = password
        tvEmail.text = email
        tvProfile.text = profile
        tvConfirmPassword.text = ConfirmPassword
        tvAddress.text = address

    }
}