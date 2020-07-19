package com.Hammadkhan950.myfoodapp.activity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentTransaction
import com.Hammadkhan950.myfoodapp.R

import com.Hammadkhan950.myfoodapp.fragment.FAQsFragment
import com.Hammadkhan950.myfoodapp.fragment.OTPResetFragment
import com.Hammadkhan950.myfoodapp.fragment.ResetFragment
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class ForgotPasswordActivity : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        toolbar = findViewById(R.id.toolbar)
        setUpToolbar()
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, ResetFragment()).commit()

    }

    fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Reset Password"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
//
//    override fun passDataCom(mobileNumber: String) {
//        val bundle = Bundle()
//        bundle.putString("mobile_number",mobileNumber)
//
//        val transaction = this.supportFragmentManager.beginTransaction()
//        val otpFragment = OTPResetFragment()
//        otpFragment.arguments = bundle
//
//        transaction.replace(R.id.frameLayout, OTPResetFragment())
//        transaction.addToBackStack(null)
//        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//        transaction.commit()
//    }

}