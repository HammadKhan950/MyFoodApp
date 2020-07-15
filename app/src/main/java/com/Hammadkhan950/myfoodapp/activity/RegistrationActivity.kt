package com.Hammadkhan950.myfoodapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.Hammadkhan950.myfoodapp.R

class RegistrationActivity : AppCompatActivity() {
    lateinit var etProfile:EditText
    lateinit var etEmail:EditText
    lateinit var etAddress:EditText
    lateinit var etMobile:EditText
    lateinit var etPassword:EditText
    lateinit var etConfirmPassword:EditText
    lateinit var btnRegister:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        title="Register Yourself"
        etProfile=findViewById(R.id.etprofile)
        etEmail=findViewById(R.id.etemail)
        etAddress=findViewById(R.id.etaddress)
        etMobile=findViewById(R.id.etmobile)
        etPassword=findViewById(R.id.etPassword)
        etConfirmPassword=findViewById(R.id.etconfirmPassword)
        btnRegister=findViewById(R.id.btnRegister)
        btnRegister.setOnClickListener {
            val profile=etProfile.text.toString()
            val email=etEmail.text.toString()
            val address=etAddress.text.toString()
            val mobileNumber = etMobile.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword=etConfirmPassword.text.toString()
            val intent=Intent(this, ViewActivity::class.java)
            intent.putExtra("profile",profile)
            intent.putExtra("email",email)
            intent.putExtra("address",address)
            intent.putExtra("mobileNumber",mobileNumber)
            intent.putExtra("password",password)
            intent.putExtra("confirmPassword",confirmPassword)
            startActivity(intent)

        }



    }
}