package com.udacity.asteroidradar.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


/**
 * Created by Ahmad Mansour on 16/10/2021
 * Dubai, UAE.
 */

@Parcelize
data class Links(val url: List<String>? = null) : Parcelable