package com.Hammadkhan950.myfoodapp.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.Hammadkhan950.myfoodapp.R
import com.Hammadkhan950.myfoodapp.util.ConnectionManager
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class RegistrationActivity : AppCompatActivity() {
    lateinit var etProfile: EditText
    lateinit var etEmail: EditText
    lateinit var etAddress: EditText
    lateinit var etMobile: EditText
    lateinit var etPassword: EditText
    lateinit var etConfirmPassword: EditText
    lateinit var btnRegister: Button
    lateinit var sharedPreferences: SharedPreferences
    lateinit var sharedPreferences1: SharedPreferences
    lateinit var toolbar:androidx.appcompat.widget.Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        toolbar=findViewById(R.id.toolbar)
        setUpToolBar()
        etProfile = findViewById(R.id.etprofile)
        etEmail = findViewById(R.id.etemail)
        etAddress = findViewById(R.id.etaddress)
        etMobile = findViewById(R.id.etmobile)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etconfirmPassword)
        btnRegister = findViewById(R.id.btnRegister)
        sharedPreferences1=getSharedPreferences(getString(R.string.preferences_file_name),Context.MODE_PRIVATE)
        sharedPreferences =
            getSharedPreferences("Registration", Context.MODE_PRIVATE)
        btnRegister.setOnClickListener {
            val profile = etProfile.text.toString()
            val email = etEmail.text.toString()
            val address = etAddress.text.toString()
            val mobileNumber = etMobile.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()
            if (password.equals(confirmPassword)) {
                savePreferences(mobileNumber, profile, email, address)
                Toast.makeText(this, "Details saved", Toast.LENGTH_LONG).show()
                val queue = Volley.newRequestQueue(this)
                val url = "http://13.235.250.119/v2/register/fetch_result"
                val jsonParams = JSONObject()
                jsonParams.put("name", profile)
                jsonParams.put("mobile_number", mobileNumber)
                jsonParams.put("password", password)
                jsonParams.put("address", address)
                jsonParams.put("email", email)
                if (ConnectionManager().checkConnectivity(this)) {
                    val jsonRequest = object : JsonObjectRequest(Method.POST, url, jsonParams,
                        Response.Listener {
                            val data = it.getJSONObject("data")
                            val success = data.getBoolean("success")
                            if (success) {
                                sharedPreferences1.edit().putBoolean("isLoggedIn",true).apply()

                                val intent = Intent(this, DashboardActivity::class.java)
                                startActivity(intent)
                            } else {

                                val errorMessage=data.getString("errorMessage")
                                Toast.makeText(
                                    this,
                                    errorMessage,
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
                }
            } else {
                Toast.makeText(this, "Password did not match", Toast.LENGTH_SHORT).show()
                etPassword.text.clear()
                etConfirmPassword.text.clear()
            }
        }

    }

    fun savePreferences(mobileNumber: String, name: String, email: String, address: String) {
        sharedPreferences.edit().putString("mobileNumber", mobileNumber).apply()
        sharedPreferences.edit().putString("name", name).apply()
        sharedPreferences.edit().putString("email", email).apply()
        sharedPreferences.edit().putString("address", address).apply()
    }
    fun setUpToolBar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title = "  Register Here"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.getItemId()) {
            android.R.id.home -> {
                val homeIntent = Intent(this, LoginActivity::class.java)
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(homeIntent)
            }
        }
        return super.onOptionsItemSelected(menuItem)
    }


}