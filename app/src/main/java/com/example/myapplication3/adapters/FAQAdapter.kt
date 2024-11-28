package com.example.myapplication3.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication3.MainActivityFAQs
import com.example.myapplication3.R



class FAQAdapter(private val faqs: List<MainActivityFAQs.FAQ>) : RecyclerView.Adapter<FAQAdapter.FAQViewHolder>() {

    class FAQViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionText: TextView = itemView.findViewById(R.id.questionText)
        val answerText: TextView = itemView.findViewById(R.id.answerText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FAQViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_faq, parent, false)
        return FAQViewHolder(view)
    }

    override fun onBindViewHolder(holder: FAQViewHolder, position: Int) {
        holder.questionText.text = faqs[position].question
        holder.answerText.text = faqs[position].answer
    }

    override fun getItemCount() = faqs.size


    data class FAQ(
        val question: String,
        val answer: String
    )
}
