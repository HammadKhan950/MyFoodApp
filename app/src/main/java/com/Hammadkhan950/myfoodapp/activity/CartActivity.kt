package com.Hammadkhan950.myfoodapp.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Paint
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.Hammadkhan950.myfoodapp.R
import com.Hammadkhan950.myfoodapp.adapter.CartItemAdapter
import com.Hammadkhan950.myfoodapp.adapter.DishAdapter
import com.Hammadkhan950.myfoodapp.database.DishDatabase
import com.Hammadkhan950.myfoodapp.database.FoodEntity
import com.Hammadkhan950.myfoodapp.model.FoodItem
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject

class CartActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerCart: RecyclerView
    private lateinit var cartItemAdapter: CartItemAdapter
    private var orderList = ArrayList<FoodItem>()
    private lateinit var txtResName: TextView
    private lateinit var rlLoading: ProgressBar
    private lateinit var rlCart: RelativeLayout
    private lateinit var btnPlaceOrder: Button
    private var resId: String = ""
    private var resName: String = ""
    var userId: String = ""
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        init()
        setupToolbar()
        setUpCartList()
        placeOrder()

    }

    private fun init() {
        rlLoading = findViewById(R.id.progressBar)
        rlCart = findViewById(R.id.progressLayout)
        txtResName = findViewById(R.id.txtCartResName)
        btnPlaceOrder = findViewById(R.id.btnConfirmOrder)
        if (intent != null) {
            val bundle = intent.getBundleExtra("data")
            resId = bundle.getString("resId", "")
            resName = bundle.getString("resName", "")
        }
        txtResName.text = resName

        sharedPreferences =
            getSharedPreferences(getString(R.string.preferences_file_name), Context.MODE_PRIVATE)
        userId = sharedPreferences.getString("userID", "").toString()
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Cart Items"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setUpCartList() {
        recyclerCart = findViewById(R.id.recyclerCartItems)
        val dbList = GetItemsFromDBAsync(applicationContext).execute().get()
        for (element in dbList) {
            orderList.addAll(
                Gson().fromJson(element.foodItems, Array<FoodItem>::class.java).asList()
            )

        }

        cartItemAdapter = CartItemAdapter(orderList, this@CartActivity)
        val mLayoutManager = LinearLayoutManager(this@CartActivity)
        recyclerCart.layoutManager = mLayoutManager
        recyclerCart.itemAnimator = DefaultItemAnimator()
        recyclerCart.adapter = cartItemAdapter
    }


    private fun placeOrder() {
        btnPlaceOrder = findViewById(R.id.btnConfirmOrder)
        var sum = 0
        for (i in 0 until orderList.size) {
            sum += orderList[i].foodCostForOne
        }
        btnPlaceOrder.setPaintFlags(btnPlaceOrder.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
        val total = "Amount to be paid : Rs. $sum"
        btnPlaceOrder.text = total

        btnPlaceOrder.setOnClickListener {
            rlLoading.visibility = View.VISIBLE
            rlCart.visibility = View.INVISIBLE
            sendServerRequest()
        }
    }

    private fun sendServerRequest() {
        val queue = Volley.newRequestQueue(this)

        val jsonParams = JSONObject()
        jsonParams.put("user_id", userId)
        jsonParams.put("restaurant_id", resId)
        var sum = 0
        for (i in 0 until orderList.size) {
            sum += orderList[i].foodCostForOne
        }
        jsonParams.put("total_cost", sum.toString())
        val foodArray = JSONArray()
        for (i in 0 until orderList.size) {
            val foodId = JSONObject()
            foodId.put("food_item_id", orderList[i].foodID)
            foodArray.put(i, foodId)
        }
        jsonParams.put("food", foodArray)


        val jsonObjectRequest =
            object : JsonObjectRequest(
                Method.POST,
                "http://13.235.250.119/v2/place_order/fetch_result/",
                jsonParams,
                Response.Listener {

                    try {
                        val data = it.getJSONObject("data")
                        val success = data.getBoolean("success")
                        if (success) {
                            ClearDBAsync(applicationContext).execute().get()
                            DishAdapter.isCartEmpty = true
                            val dialog = Dialog(
                                this@CartActivity,
                                android.R.style.Theme_Black_NoTitleBar_Fullscreen
                            )
                            dialog.setContentView(R.layout.order_placed_dialog)
                            dialog.show()
                            dialog.setCancelable(false)
                            val btnOk = dialog.findViewById<Button>(R.id.btnOk)
                            btnOk.setOnClickListener {
                                dialog.dismiss()
                                startActivity(
                                    Intent(
                                        this@CartActivity,
                                        DashboardActivity::class.java
                                    )
                                )
                                ActivityCompat.finishAffinity(this@CartActivity)
                            }
                        } else {
                            rlCart.visibility = View.VISIBLE
                            Toast.makeText(
                                this@CartActivity,
                                "Some Error occurred",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }

                    } catch (e: Exception) {
                        rlCart.visibility = View.VISIBLE
                        e.printStackTrace()
                    }

                },
                Response.ErrorListener {
                    rlCart.visibility = View.VISIBLE
                    Toast.makeText(this@CartActivity, it.message, Toast.LENGTH_SHORT).show()
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-type"] = "application/json"
                    headers["token"] = "7648815128647f"
                    return headers
                }
            }

        queue.add(jsonObjectRequest)

    }

    class GetItemsFromDBAsync(context: Context) : AsyncTask<Void, Void, List<FoodEntity>>() {
        val db = Room.databaseBuilder(context, DishDatabase::class.java, "dishes-db").build()
        override fun doInBackground(vararg params: Void?): List<FoodEntity> {
            return db.foodDao().getAllFood()
        }

    }

    class ClearDBAsync(context: Context) : AsyncTask<Void, Void, Boolean>() {
        val db = Room.databaseBuilder(context, DishDatabase::class.java, "dishes-db").build()
        override fun doInBackground(vararg params: Void?): Boolean {
            db.foodDao().nukeTable()
            db.close()
            return true

        }
    }
    override fun onSupportNavigateUp(): Boolean {
        ClearDBAsync(applicationContext).execute().get()
        DishAdapter.isCartEmpty = true
        onBackPressed()
        return true
    }
}