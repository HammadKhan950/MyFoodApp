package com.Hammadkhan950.myfoodapp.fragment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.Hammadkhan950.myfoodapp.R
import com.Hammadkhan950.myfoodapp.adapter.FavouriteRecyclerAdapter
import com.Hammadkhan950.myfoodapp.database.DishDatabase
import com.Hammadkhan950.myfoodapp.database.DishEntity


class FavouriteFragment : Fragment() {


    lateinit var recyclerFavourite: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var progressLayout: RelativeLayout
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: FavouriteRecyclerAdapter
    var dbDishList = listOf<DishEntity>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_favourite, container, false)


        recyclerFavourite = view.findViewById(R.id.recyclerViewFavourite)
        progressLayout = view.findViewById(R.id.progressLayoutFavourite)
        progressLayout.visibility = View.VISIBLE
        progressBar = view.findViewById(R.id.progressBarFavourite)
        layoutManager = LinearLayoutManager(activity)
        dbDishList = RetrieveFavourites(activity as Context).execute().get()
        if (activity != null) {
            progressLayout.visibility = View.GONE
            recyclerAdapter = FavouriteRecyclerAdapter(activity as Context, dbDishList)
            recyclerFavourite.adapter = recyclerAdapter
            recyclerFavourite.layoutManager = layoutManager
        }

        return view
    }

    class RetrieveFavourites(val context: Context) : AsyncTask<Void, Void, List<DishEntity>>() {
        override fun doInBackground(vararg params: Void?): List<DishEntity> {
            val db = Room.databaseBuilder(context, DishDatabase::class.java, "dishes-db").build()
            return db.dishDao().getAllDishes()
        }

    }

}