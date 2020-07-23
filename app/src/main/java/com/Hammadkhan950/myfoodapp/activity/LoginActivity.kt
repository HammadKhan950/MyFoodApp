package com.Hammadkhan950.myfoodapp.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.*
import androidx.appcompat.widget.Toolbar
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
    lateinit var toolbar: Toolbar
    var userId:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        etMobile = findViewById(R.id.etmobile)
        etPassword = findViewById(R.id.etpassword)
        btnLogin = findViewById(R.id.btnlogin)
        tvForgotPassword = findViewById(R.id.tvforgotpassword)
        tvSignUp = findViewById(R.id.tvSignup)
        toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "  Log in Here"
        sharedPreferences =
            getSharedPreferences(getString(R.string.preferences_file_name), Context.MODE_PRIVATE)

        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        if (isLoggedIn) {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        }
        btnLogin.setOnClickListener {
            val mobileNumber = etMobile.text.toString()
            val password = etPassword.text.toString()
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
                            if (success) {
                                val dishJsonObject = data.getJSONObject("data")
                                userId=dishJsonObject.getString("user_id")
                                savePreferences(mobileNumber, password,userId)
                                Toast.makeText(
                                    this,
                                    userId,
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent = Intent(this, DashboardActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(
                                    this,
                                    "Invalid details",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }, Response.ErrorListener {
                            Toast.makeText(this, "Some error occurred!", Toast.LENGTH_SHORT).show()
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
                    dialog.setMessage("Internet connection is not Found")
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

    fun savePreferences(mobileNumber: String, password: String,userId:String) {
        sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
        sharedPreferences.edit().putString("MobileNumber", mobileNumber).apply()
        sharedPreferences.edit().putString("Password", password).apply()
        sharedPreferences.edit().putString("userID",userId).apply()
    }

    override fun onBackPressed() {
        ActivityCompat.finishAffinity(this)

    }
}