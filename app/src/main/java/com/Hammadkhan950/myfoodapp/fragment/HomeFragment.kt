package com.Hammadkhan950.myfoodapp.fragment

import android.app.Activity
import android.app.AlertDialog
import android.app.DownloadManager
import android.app.VoiceInteractor
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.Hammadkhan950.myfoodapp.R
import com.Hammadkhan950.myfoodapp.adapter.HomeRecyclerAdapter
import com.Hammadkhan950.myfoodapp.database.DishDatabase
import com.Hammadkhan950.myfoodapp.database.DishEntity
import com.Hammadkhan950.myfoodapp.model.Dish
import com.Hammadkhan950.myfoodapp.util.ConnectionManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException


class HomeFragment : Fragment() {

    lateinit var recyclerViewHome: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var recyclerAdapter: HomeRecyclerAdapter
    lateinit var progressBar: ProgressBar
    lateinit var progressLayout: RelativeLayout
    val dishInfoList = arrayListOf<Dish>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home_fragement, container, false)

        layoutManager = LinearLayoutManager(activity)
        recyclerViewHome = view.findViewById(R.id.homeRecyclerView)
        progressBar = view.findViewById(R.id.progressBar)
        progressLayout = view.findViewById(R.id.progressLayout)
        progressLayout.visibility = View.VISIBLE

        val queue = Volley.newRequestQueue(activity as Context)
        val url = "http://13.235.250.119/v2/restaurants/fetch_result/"

        if (ConnectionManager().checkConnectivity(activity as Context)) {
            val jsonObjectRequest =
                object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
                    try {
                        progressLayout.visibility = View.GONE
                        val data = it.getJSONObject("data")
                        val success = data.getBoolean("success")
                        Log.i("Message", success.toString())
                        if (success) {
                            val dishData = data.getJSONArray("data")
                            for (i in 0 until dishData.length()) {
                                val dishJsonObject = dishData.getJSONObject(i)
                                val dishObject = Dish(
                                    dishJsonObject.getString("id"),
                                    dishJsonObject.getString("name"),
                                    dishJsonObject.getString("rating"),
                                    dishJsonObject.getString("cost_for_one"),
                                    dishJsonObject.getString("image_url")
                                )

                                dishInfoList.add(dishObject)

                                recyclerAdapter =
                                    HomeRecyclerAdapter(activity as Context, dishInfoList)
                                recyclerViewHome.adapter = recyclerAdapter
                                recyclerViewHome.layoutManager = layoutManager
                            }

                        } else {
                            Toast.makeText(
                                activity as Context,
                                "some error occurred",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } catch (e: JSONException) {
                        Toast.makeText(
                            activity as Context,
                            "Some error occurred",
                            Toast.LENGTH_LONG
                        ).show()

                    }

                }, Response.ErrorListener {

                    if (activity != null) {
                        Toast.makeText(
                            activity as Context,
                            "Some error occurred",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }

                }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Content-type"] = "application/json"
                        headers["token"] = "7648815128647f"
                        return headers
                    }
                }
            queue.add(jsonObjectRequest)

        } else {

            val diadlog = AlertDialog.Builder(activity as Context)
            diadlog.setTitle("Error")
            diadlog.setMessage("Internet Connection is not found")
            diadlog.setPositiveButton("Open Settings") { text, listener ->
                val settingIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingIntent)
                activity?.finish()
            }
            diadlog.setNegativeButton("Exit") { text, listener ->

                ActivityCompat.finishAffinity(activity as Activity)
            }
            diadlog.create()
            diadlog.show()
        }
        return view
    }

    class DbAsyncTask(val context: Context, val dishEntity: DishEntity, val mode: Int) :
        AsyncTask<Void, Void, Boolean>() {
        val db = Room.databaseBuilder(context, DishDatabase::class.java, "dishes-db").build()
        override fun doInBackground(vararg params: Void?): Boolean {
            when (mode) {
                1 -> {
                    val dish: DishEntity? = db.dishDao().getDishById(dishEntity.id.toString())
                    db.close()
                    return dish != null

                }
                2 -> {
                    db.dishDao().insertDish(dishEntity)
                    db.close()
                    return true

                }
                3 -> {
                    db.dishDao().deleteDish(dishEntity)
                    db.close()
                    return true
                }
            }

            return false
        }

    }
}