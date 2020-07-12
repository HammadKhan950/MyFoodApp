package com.Hammadkhan950.myfoodapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    lateinit var etMobile: EditText
    lateinit var etPassword: EditText
    lateinit var btnLogin: Button
    lateinit var tvForgotPassword: TextView
    lateinit var tvSignUp: TextView
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        title = "Log in page"
        etMobile = findViewById(R.id.etmobile)
        etPassword = findViewById(R.id.etpassword)
        btnLogin = findViewById(R.id.btnlogin)
        tvForgotPassword = findViewById(R.id.tvforgotpassword)
        tvSignUp = findViewById(R.id.tvSignup)
        sharedPreferences =
            getSharedPreferences(getString(R.string.preferences_file_name), Context.MODE_PRIVATE)

        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        if (isLoggedIn) {
            val intent = Intent(this, NewActivity::class.java)
            startActivity(intent)
            finish()
        }
        btnLogin.setOnClickListener {
            val mobileNumber = etMobile.text.toString()
            val password = etPassword.text.toString()
            savePreferences(mobileNumber, password)
            val intent = Intent(this, NewActivity::class.java)
            startActivity(intent)
        }
        tvSignUp.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
        tvForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    fun savePreferences(mobileNumber: String, password: String) {
        sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
        sharedPreferences.edit().putString("MobileNumber", mobileNumber).apply()
        sharedPreferences.edit().putString("Password", password).apply()
    }

}