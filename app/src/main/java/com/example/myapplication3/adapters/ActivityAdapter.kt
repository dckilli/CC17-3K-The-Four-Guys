package com.example.myapplication3.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication3.models.Activity
import com.example.myapplication3.R

class ActivityAdapter(
    private val activities: MutableList<Activity>,
    private val onDeleteClick: (Activity) -> Unit
) : RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder>() {

    inner class ActivityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDay: TextView = itemView.findViewById(R.id.tvDay)
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val btnDelete: Button = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itinerary_item_activity, parent, false)
        return ActivityViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        val activity = activities[position]
        activity.day.toString().also { holder.tvDay.text = it }
        holder.tvTime.text = activity.time
        holder.tvDescription.text = activity.description

        holder.btnDelete.setOnClickListener {
            onDeleteClick(activity)
            notifyDataSetChanged()  // Refresh the RecyclerView to reflect the deleted item
        }
    }

    override fun getItemCount(): Int = activities.size
}
