package com.Hammadkhan950.myfoodapp.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.Hammadkhan950.myfoodapp.R
import com.Hammadkhan950.myfoodapp.adapter.QuestionAdapter

class FAQsFragment : Fragment() {


    lateinit var recyclerViewQuestions: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: QuestionAdapter
    val questionList = ArrayList<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_f_a_qs, container, false)
        questionList.add(
            0, "Ques-Do you sell regular groceries too?\n" +
                    "Ans-While we take a great deal of effort, care and joy in tracking down hard to find items from all over the world, we also provide staple items, which can be found in most mainstream supermarkets. In addition, we take pride in our own Bogopa brand, a product line which contains high-quality items, such as macaroni and cheese, rice, canned mixed vegetables and ketchup, to name just a few. So we are able to serve as a one-stop shopping experience for our customers."
        )
        questionList.add(1, "Ques-Do you have weekly specials or sales on your products?\n" +
                "Ans-Yes, weekly, our circulars reflect amazing sales as well as low prices on thousands of products each and every day.")
        questionList.add(2,"Ques-How can I download the App?\n" +
                "Ans-\n"+
                "1.Go to the App Store (iPhone users) or Google Play (Android Users)\n" +
                "2.Search for “My Food App” and download the app \n" +
                "3.Open the App and sign up for a new account. (Follow the instructions)")

        recyclerViewQuestions = view.findViewById(R.id.recyclerViewQuestions)
        layoutManager = LinearLayoutManager(activity)

        if (activity != null) {
            recyclerAdapter = QuestionAdapter(activity as Context, questionList)
            recyclerViewQuestions.adapter = recyclerAdapter
            recyclerViewQuestions.layoutManager = layoutManager
        }

        return view
    }


}