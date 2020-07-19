package com.Hammadkhan950.myfoodapp.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.Hammadkhan950.myfoodapp.R
import org.w3c.dom.Text


class ProfileFragment : Fragment() {

    lateinit var txtProfileName: TextView
    lateinit var txtProfileNumber: TextView
    lateinit var txtProfileEmail: TextView
    lateinit var txtProfileAddress: TextView
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        txtProfileName = view.findViewById(R.id.txtProfileName)
        txtProfileNumber = view.findViewById(R.id.txtProfileNumber)
        txtProfileEmail = view.findViewById(R.id.txtProfileEmail)
        txtProfileAddress = view.findViewById(R.id.txtProfileAddress)
        sharedPreferences = this.activity?.getSharedPreferences("Registration", Context.MODE_PRIVATE)!!
        val mobileNumber = sharedPreferences.getString("mobileNumber", "-")
        val name = sharedPreferences.getString("name", "-")
        val email = sharedPreferences.getString("email", "-")
        val address = sharedPreferences.getString("address", "-")
        txtProfileName.text = name
        txtProfileNumber.text = mobileNumber
        txtProfileEmail.text = email
        txtProfileAddress.text =address


        return view
    }


}