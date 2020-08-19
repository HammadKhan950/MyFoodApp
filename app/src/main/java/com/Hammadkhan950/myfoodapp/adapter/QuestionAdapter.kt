package com.Hammadkhan950.myfoodapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.Hammadkhan950.myfoodapp.R
import kotlinx.android.synthetic.main.recycler_view_single_question.view.*

class QuestionAdapter(val context: Context, val questionList: ArrayList<String>) :
    RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    class QuestionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtQuestion: TextView = view.findViewById(R.id.txtQuestions)

    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val ques = questionList[position]
        holder.txtQuestion.text = ques
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_single_question, parent, false)
        return QuestionViewHolder(view)

    }

    override fun getItemCount(): Int {
       return questionList.size
    }

}
