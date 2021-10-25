package com.udacity.asteroidradar.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.LayoutItemAsteroidBinding
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.main.AsteroidAdapter.AsteroidViewHolder


/**
 * Created by Ahmad Mansour on 23/10/2021
 * Dubai, UAE.
 */


class AsteroidAdapter(private val listener: OnClickListener) :
    ListAdapter<Asteroid, AsteroidViewHolder>(DiffCallback) {

    /**
     * The asteroids that our Adapter will show
     */
    var asteroids: List<Asteroid> = emptyList()
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
        fun bind(asteroid: Asteroid) {
            binding.asteroid = asteroid
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (databaseAsteroid: Asteroid) -> Unit) {
        fun onClick(databaseAsteroid: Asteroid) = clickListener(databaseAsteroid)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }
    }
}