package com.Hammadkhan950.myfoodapp.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.Hammadkhan950.myfoodapp.fragment.ProfileFragment
import com.Hammadkhan950.myfoodapp.R
import com.Hammadkhan950.myfoodapp.fragment.FAQsFragment
import com.Hammadkhan950.myfoodapp.fragment.FavouriteFragment
import com.Hammadkhan950.myfoodapp.fragment.HomeFragment
import com.google.android.material.navigation.NavigationView

class NewActivity : AppCompatActivity() {


    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    var previousMenuItem: MenuItem? = null

     lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)
        sharedPreferences=getSharedPreferences(getString(R.string.preferences_file_name), Context.MODE_PRIVATE)
//        val mobileNumber = sharedPreferences.getString("MobileNumber", 100.toString())
//        val password = sharedPreferences.getString("Password","")


        drawerLayout = findViewById(R.id.drawerLayout)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        frameLayout = findViewById(R.id.frameLayout)
        navigationView = findViewById(R.id.navigationView)
        toolbar = findViewById(R.id.toolbar)
        setUpToolbar()
        openDashboard()
        val actionBarDrawerToggle =
            ActionBarDrawerToggle(
                this, drawerLayout,
                R.string.open_drawer,
                R.string.close_drawer
            )

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {


            if (previousMenuItem != null) {
                previousMenuItem?.isChecked = false
            }
            it.isCheckable = true
            it.isChecked = true
            previousMenuItem = it

            when (it.itemId) {
                R.id.home -> {
                    openDashboard()
                }
                R.id.favouriteRestaurants -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frameLayout,
                            FavouriteFragment()
                        )
                        .commit()
                    supportActionBar?.title = "Favourite Restaurant"
                    drawerLayout.closeDrawers()

                }
                R.id.myprofile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frameLayout,
                            ProfileFragment()
                        )
                        .commit()
                    supportActionBar?.title = "My Profile"
                    drawerLayout.closeDrawers()

                }
                R.id.faqs -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frameLayout,
                            FAQsFragment()
                        )
                        .commit()
                    supportActionBar?.title = "Frequently Asked Questions"
                    drawerLayout.closeDrawers()

                }
                R.id.logout -> {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Confirmation")
                        .setMessage("Are you sure you want exit?")
                        .setPositiveButton("Yes") { _, _ ->
                            sharedPreferences.edit().putBoolean("isLoggedIn", false).apply()
                            val intent= Intent(this,LoginActivity::class.java)
                            startActivity(intent)
                        }
                        .setNegativeButton("No") { _, _ ->
                            openDashboard()
                        }
                        .create()
                        .show()
                    drawerLayout.closeDrawers()

                }
            }
            return@setNavigationItemSelectedListener true
        }

    }

    fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Toolbar Title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)

    }

    fun openDashboard() {
        val fragment = HomeFragment()
        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(R.id.frameLayout, fragment)
        transaction.commit()
        supportActionBar?.title = "All Restaurants"
        navigationView.setCheckedItem(R.id.home)
    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.frameLayout)

        when (frag) {
            !is HomeFragment -> openDashboard()

            else -> super.onBackPressed()
        }


    }


}
