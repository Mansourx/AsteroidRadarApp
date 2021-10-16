package com.udacity.asteroidradar.model

import android.os.Parcelable
import com.udacity.asteroidradar.Asteroid
import kotlinx.android.parcel.Parcelize


/**
 * Created by Ahmad Mansour on 16/10/2021
 * Dubai, UAE.
 */

// NEO = Near Earth Object
@Parcelize
data class NEO(val nearEarthObject: HashMap<String, List<Asteroid>>): Parcelable