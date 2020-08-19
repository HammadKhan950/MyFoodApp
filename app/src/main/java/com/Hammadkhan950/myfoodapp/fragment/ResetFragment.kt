package com.Hammadkhan950.myfoodapp.fragment

import android.content.Context
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
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


class ResetFragment : Fragment() {
    lateinit var etMobile: EditText
    lateinit var etEmail: EditText
    lateinit var btnNext: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reset, container, false)
        etMobile = view.findViewById(R.id.etmobile)
        etEmail = view.findViewById(R.id.etemail)
        btnNext = view.findViewById(R.id.btnNext)
        btnNext.setOnClickListener {
            val mobileNumber = etMobile.text.toString()
            val email = etEmail.text.toString()
            val queue = Volley.newRequestQueue(activity as Context)
            val url = "http://13.235.250.119/v2/forgot_password/fetch_result "
            val jsonParams = JSONObject()
            jsonParams.put("mobile_number", mobileNumber)
            jsonParams.put("email", email)
            val jsonRequest = object : JsonObjectRequest(
                Method.POST, url, jsonParams,
                Response.Listener {
                    val data = it.getJSONObject("data")
                    val success = data.getBoolean("success")
                    if (success) {
                        val otpResetFragment=OTPResetFragment()
                        val args=Bundle()
                        args.putString("mobile_number",mobileNumber)
                        otpResetFragment.arguments=args
                        val transaction =
                            activity!!.supportFragmentManager.beginTransaction()
                        transaction.replace(R.id.frameLayout, otpResetFragment)
                        transaction.commit()
                    } else {
                        Toast.makeText(
                            activity as Context,
                            "Some error occurred!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }, Response.ErrorListener {
                    Toast.makeText(activity as Context, "Some error occurred!", Toast.LENGTH_SHORT)
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
        }
        return view
    }
}
