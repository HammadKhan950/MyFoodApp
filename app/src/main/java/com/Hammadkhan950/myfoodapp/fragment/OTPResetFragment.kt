package com.Hammadkhan950.myfoodapp.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.Hammadkhan950.myfoodapp.R
import com.Hammadkhan950.myfoodapp.activity.LoginActivity
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class OTPResetFragment : Fragment() {


    lateinit var etOTP: EditText
    lateinit var etPassword: EditText
    lateinit var etConfirmPassword: EditText
    lateinit var btnSubmit: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_o_t_p_reset, container, false)
        etOTP = view.findViewById(R.id.etOTP)
        etPassword = view.findViewById(R.id.etPassword)
        etConfirmPassword = view.findViewById(R.id.etConfirmPassword)
        btnSubmit = view.findViewById(R.id.btnSubmit)
        val mobileNumber = arguments?.getString("mobile_number", "")
        btnSubmit.setOnClickListener {
            val OTP = etOTP.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()
            val queue = Volley.newRequestQueue(activity as Context)
            val url = "http://13.235.250.119/v2/reset_password/fetch_result"
            if (password.equals(confirmPassword)) {
                val jsonParams = JSONObject()
                jsonParams.put("mobile_number", mobileNumber)
                jsonParams.put("password", password)
                jsonParams.put("otp", OTP)
                val jsonRequest = object : JsonObjectRequest(
                    Method.POST, url, jsonParams,
                    Response.Listener {
                        val data=it.getJSONObject("data")
                        val success=data.getBoolean("success")
                        if(success) {
                            val successMessage = data.getString("successMessage")

                            Toast.makeText(activity as Context, successMessage, Toast.LENGTH_SHORT)
                                .show()
                            val intent = Intent(activity as Context, LoginActivity::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(activity as Context,"Some error occurred!" , Toast.LENGTH_SHORT)
                                .show()
                        }

                    }, Response.ErrorListener {
                        Toast.makeText(
                            activity as Context,
                            "Some error occurred!",
                            Toast.LENGTH_SHORT
                        )
                            .show()
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
                Toast.makeText(activity as Context, "Password did not match", Toast.LENGTH_SHORT)
                    .show()
                etPassword.text.clear()
                etConfirmPassword.text.clear()
            }
        }

        return view
    }

}