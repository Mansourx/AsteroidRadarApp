package com.udacity.asteroidradar

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.db.DatabaseAsteroid
import com.udacity.asteroidradar.main.AsteroidAdapter

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.apply {
            setImageResource(R.drawable.ic_status_potentially_hazardous)
            contentDescription =
                imageView.context.getString(R.string.potentially_hazardous_asteroid_image)
        }
    } else {
        imageView.apply {
            setImageResource(R.drawable.ic_status_normal)
            contentDescription = imageView.context.getString(R.string.not_hazardous_asteroid_image)
        }
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.apply {
            setImageResource(R.drawable.asteroid_hazardous)
            contentDescription =
                imageView.context.getString(R.string.potentially_hazardous_asteroid_image)
        }
    } else {
        imageView.apply {
            setImageResource(R.drawable.asteroid_safe)
            contentDescription = imageView.context.getString(R.string.not_hazardous_asteroid_image)
        }
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<DatabaseAsteroid>?) {
    val adapter = recyclerView.adapter as? AsteroidAdapter
    adapter?.submitList(data)
}

/**
 * Binding adapter used to hide the spinner once data is available
 */
@BindingAdapter("goneIfNotNull")
fun goneIfNotNull(view: View, it: Any?) {
    view.visibility = if (it != null) View.GONE else View.VISIBLE
}