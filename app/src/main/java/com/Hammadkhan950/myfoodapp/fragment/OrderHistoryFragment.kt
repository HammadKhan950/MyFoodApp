package com.Hammadkhan950.myfoodapp.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.Hammadkhan950.myfoodapp.R
import com.Hammadkhan950.myfoodapp.adapter.OrderHistoryAdapter
import com.Hammadkhan950.myfoodapp.model.OrderHistory
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class OrderHistoryFragment : Fragment() {

    lateinit var sharedPreferences: SharedPreferences
    val orderHistoryList = ArrayList<OrderHistory>()
    lateinit var orderHistoryAdapter: OrderHistoryAdapter
    lateinit var orderHistoryRecyclerView: RecyclerView
    lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_order_history, container, false)
        layoutManager = LinearLayoutManager(activity)
        orderHistoryRecyclerView = view.findViewById(R.id.orderHistoryRecyclerView)
        sharedPreferences = this.activity?.getSharedPreferences(getString(R.string.preferences_file_name), Context.MODE_PRIVATE)!!
        val userId = sharedPreferences.getString("userID", "0")
        val queue = Volley.newRequestQueue(activity as Context)
        val url = "http://13.235.250.119/v2/orders/fetch_result/$userId "
        val jsonObjectRequest =
            object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
                val data = it.getJSONObject("data")
                val success = data.getBoolean("success")
                if (success) {
                    val dishData = data.getJSONArray("data")
                    for (i in 0 until dishData.length()) {
                        val orderObject = dishData.getJSONObject(i)
                        val foodItems = orderObject.getJSONArray("food_items")
                        val orderHistory = OrderHistory(
                            orderObject.getInt("order_id"),
                            orderObject.getString("restaurant_name"),
                            orderObject.getString("order_placed_at"),
                            foodItems
                        )
                        orderHistoryList.add(orderHistory)
                        orderHistoryAdapter =
                            OrderHistoryAdapter(activity as Context, orderHistoryList)
                        orderHistoryRecyclerView.layoutManager = layoutManager
                        orderHistoryRecyclerView.itemAnimator = DefaultItemAnimator()
                        orderHistoryRecyclerView.adapter = orderHistoryAdapter
                    }
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


}