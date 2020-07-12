package com.Hammadkhan950.myfoodapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class ForgotPasswordActivity : AppCompatActivity() {
    lateinit var etMobile:EditText
    lateinit var etEmail:EditText
    lateinit var btnNext:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        etMobile=findViewById(R.id.etmobile)
        etEmail=findViewById(R.id.etemail)
        btnNext=findViewById(R.id.btnNext)
        btnNext.setOnClickListener {
            val mobileNumber = etMobile.text.toString()
            val email = etEmail.text.toString()
            val intent = Intent(this, ForgotPasswordViewActivity::class.java)
            intent.putExtra("MobileNumber", mobileNumber)
            intent.putExtra("Email", email)
            startActivity(intent)
        }
    }
}