package com.example.myapplication3.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication3.models.*
import com.example.myapplication3.databinding.ItineraryItemLayoutBinding

abstract class ItineraryAdapter(private val items: MutableList<Any>) : RecyclerView.Adapter<ItineraryAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItineraryItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Any) {
            when (item) {
                is TouristSpot -> {
                    binding.itemName.text = item.name
                    binding.itemDescription.text = item.description
                }

                is Restaurant -> {
                    binding.itemName.text = item.name
                    binding.itemDescription.text = item.cuisine
                }

                is Accommodation -> {
                    binding.itemName.text = item.name
                    binding.itemDescription.text = item.type
                }
            }
        }

        fun bind(item: ItineraryItem) {
            binding.itemDay.text = item.day
            binding.itemTime.text = item.time
            binding.itemActivity.text = item.activity
            binding.itemPlace.text = item.place
        }
    }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = ItineraryItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(items[position])
        }

        override fun getItemCount(): Int = items.size

        // Function to move items
        fun onItemMoved(fromPosition: Int, toPosition: Int) {
            val movedItem = items.removeAt(fromPosition)
            items.add(toPosition, movedItem)
            notifyItemMoved(fromPosition, toPosition)
        }

    }


