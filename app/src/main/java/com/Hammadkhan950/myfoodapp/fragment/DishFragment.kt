package com.Hammadkhan950.myfoodapp.fragment

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.Hammadkhan950.myfoodapp.activity.CartActivity
import com.Hammadkhan950.myfoodapp.R
import com.Hammadkhan950.myfoodapp.adapter.DishAdapter
import com.Hammadkhan950.myfoodapp.database.DishDatabase
import com.Hammadkhan950.myfoodapp.database.FoodEntity
import com.Hammadkhan950.myfoodapp.model.FoodItem
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONException

class DishFragment : Fragment() {


    lateinit var recyclerViewDish: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var recyclerAdapter: DishAdapter
    lateinit var progressBar: ProgressBar
    lateinit var progressLayout: RelativeLayout
    val menuList = arrayListOf<FoodItem>()
    val orderList = arrayListOf<FoodItem>()
    var resId: String? = ""
    var resName: String? = ""
    lateinit var goToCart: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dish, container, false)
        recyclerViewDish = view.findViewById(R.id.dishRecyclerView)
        progressBar = view.findViewById(R.id.progressBar)
        progressLayout = view.findViewById(R.id.progressLayout)
        progressLayout.visibility = View.VISIBLE
        progressBar.visibility=View.VISIBLE
        goToCart = view.findViewById(R.id.btnAddToCart)
        goToCart.visibility = View.GONE
        goToCart.setOnClickListener {
            val gson = Gson()
            val foodItems = gson.toJson(orderList)
            val asyncTask = resId?.let { DbAsyncTask(activity as Context, it, foodItems, 1).execute() }
            val result = asyncTask?.get()
            if (result!!) {
                val bundle = Bundle()
                bundle.putString("resId", resId)
                bundle.putString("resName", resName)
                val intent = Intent(activity, CartActivity::class.java)
                intent.putExtra("data", bundle)
                startActivity(intent)
            } else {
                Toast.makeText(
                    activity as Context,
                    "Some error occurred!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        resId = arguments?.getString("restaurant_id", "")
        resName = arguments?.getString("restaurant_name", "")
        val queue = Volley.newRequestQueue(activity as Context)
        val url = "http://13.235.250.119/v2/restaurants/fetch_result/$resId"
        val jsonObjectRequest =
            object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
                try {
                    progressLayout.visibility = View.GONE
                    progressBar.visibility=View.GONE
                    recyclerViewDish.visibility=View.VISIBLE
                    val data = it.getJSONObject("data")
                    val success = data.getBoolean("success")
                    if (success) {
                        val dishData = data.getJSONArray("data")
                        for (i in 0 until dishData.length()) {
                            val dishJsonObject = dishData.getJSONObject(i)
                            val foodItem = FoodItem(
                                dishJsonObject.getString("id"),
                                dishJsonObject.getString("name"),
                                dishJsonObject.getInt("cost_for_one")
                            )
                            menuList.add(foodItem)
                            recyclerAdapter =
                                DishAdapter(activity as Context, menuList,
                                    object : DishAdapter.OnItemClickListener {
                                        override fun onAddItemClick(foodItem: FoodItem) {
                                            orderList.add(foodItem)
                                            if (orderList.size > 0) {
                                                goToCart.visibility = View.VISIBLE
                                                DishAdapter.isCartEmpty = false
                                            }
                                        }

                                        override fun onRemoveItemClick(foodItem: FoodItem) {
                                            orderList.remove(foodItem)
                                            if (orderList.isEmpty()) {
                                                goToCart.visibility = View.GONE
                                                DishAdapter.isCartEmpty = true
                                            }
                                        }
                                    })
                            layoutManager = LinearLayoutManager(activity)
                            recyclerViewDish.layoutManager = layoutManager
                            recyclerViewDish.adapter = recyclerAdapter
                        }

                    } else {
                        Toast.makeText(
                            activity as Context,
                            "some error occurred!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: JSONException) {
                    Toast.makeText(
                        activity as Context,
                        "Some error occurred!",
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
        return view
    }

    class DbAsyncTask(
        val context: Context,
        val restaurantID: String,
        val foodItems: String,
        val mode: Int
    ) :
        AsyncTask<Void, Void, Boolean>() {
        val db = Room.databaseBuilder(context, DishDatabase::class.java, "dishes-db").build()
        override fun doInBackground(vararg params: Void?): Boolean {
            when (mode) {
                1 -> {
                    db.foodDao().insertFood(FoodEntity(restaurantID, foodItems))
                    db.close()
                    return true

                }
                2 -> {
                    db.foodDao().deleteFood(FoodEntity(restaurantID, foodItems))
                    db.close()
                    return true
                }

            }

            return false
        }

    }

}
