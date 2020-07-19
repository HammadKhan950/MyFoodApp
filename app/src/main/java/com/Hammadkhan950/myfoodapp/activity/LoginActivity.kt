package com.Hammadkhan950.myfoodapp.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.Hammadkhan950.myfoodapp.R
import com.Hammadkhan950.myfoodapp.util.ConnectionManager
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

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
            if (mobileNumber.length != 10) {
                Toast.makeText(this, "Enter correct 10 digit mobile number", Toast.LENGTH_SHORT)
                    .show()
            } else if (password.length != 6) {
                Toast.makeText(this, "Enter 6 digits password", Toast.LENGTH_SHORT).show()
            } else {
                val queue = Volley.newRequestQueue(this)
                val url = "http://13.235.250.119/v2/login/fetch_result"
                val jsonParams = JSONObject()
                jsonParams.put("mobile_number", mobileNumber)
                jsonParams.put("password", password)
                if (ConnectionManager().checkConnectivity(this)) {
                    val jsonRequest = object : JsonObjectRequest(Method.POST, url, jsonParams,
                        Response.Listener {
                            val data = it.getJSONObject("data")
                            val success = data.getBoolean("success")
                            Log.i("Message", success.toString())
                            if (success) {
                                val intent = Intent(this, NewActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(
                                    this,
                                    "Invalid details",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }, Response.ErrorListener {
                            Toast.makeText(this, "Some error occured!", Toast.LENGTH_SHORT).show()
                        }) {

                        override fun getHeaders(): MutableMap<String, String> {
                            val headers = HashMap<String, String>()
                            headers["Content-type"] = "application/json"
                            headers["token"] = "7648815128647f"
                            return headers
                        }
                    }
                    queue.add(jsonRequest)
                } else {
                    val dialog = AlertDialog.Builder(this)
                    dialog.setTitle("Error")
                    dialog.setMessage("Internet Connection is not Found")
                    dialog.setPositiveButton("Open Settings") { text, listener ->
                        val settingIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                        startActivity(settingIntent)
                        finish()
                    }
                    dialog.setNegativeButton("Exit") { text, listener ->
                        ActivityCompat.finishAffinity(this)
                    }
                    dialog.create()
                    dialog.show()

                }
            }

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

    override fun onBackPressed() {
        ActivityCompat.finishAffinity(this)

    }
}