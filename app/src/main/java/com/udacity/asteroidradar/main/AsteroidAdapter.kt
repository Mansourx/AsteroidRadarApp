package com.udacity.asteroidradar.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.LayoutItemAsteroidBinding
import com.udacity.asteroidradar.db.DatabaseAsteroid
import com.udacity.asteroidradar.main.AsteroidAdapter.AsteroidViewHolder


/**
 * Created by Ahmad Mansour on 23/10/2021
 * Dubai, UAE.
 */


class AsteroidAdapter(private val listener: OnClickListener) :
    ListAdapter<DatabaseAsteroid, AsteroidViewHolder>(DiffCallback) {

    /**
     * The asteroids that our Adapter will show
     */
    var asteroids: List<DatabaseAsteroid> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            // For an extra challenge, update this to use the paging library.

            // Notify any registered observers that the data set has changed. This will cause every
            // element in our RecyclerView to be invalidated.
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder(
            LayoutItemAsteroidBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroid = getItem(position)
        holder.itemView.setOnClickListener {
            listener.onClick(asteroid)
        }
        holder.bind(asteroid)
    }

    inner class AsteroidViewHolder(private var binding: LayoutItemAsteroidBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(asteroid: DatabaseAsteroid) {
            binding.databaseAsteroid = asteroid
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (databaseAsteroid: DatabaseAsteroid) -> Unit) {
        fun onClick(databaseAsteroid: DatabaseAsteroid) = clickListener(databaseAsteroid)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<DatabaseAsteroid>() {
        override fun areItemsTheSame(
            oldItem: DatabaseAsteroid,
            newItem: DatabaseAsteroid
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: DatabaseAsteroid,
            newItem: DatabaseAsteroid
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }
}