package com.Hammadkhan950.myfoodapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class NewActivity : AppCompatActivity() {
    lateinit var tvMobileNumber: TextView
    lateinit var tvPassword: TextView
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)
        sharedPreferences=getSharedPreferences(getString(R.string.preferences_file_name), Context.MODE_PRIVATE)
        tvMobileNumber = findViewById(R.id.tvMobileNumber)
        tvPassword = findViewById(R.id.tvPassword)


        val mobileNumber = sharedPreferences.getString("MobileNumber", 100.toString())
        val password = sharedPreferences.getString("Password","")
        tvMobileNumber.text = mobileNumber
        tvPassword.text = password

    }
}