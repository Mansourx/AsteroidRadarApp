package com.udacity.asteroidradar.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET


/**
 * Created by Ahmad Mansour on 16/10/2021
 * NAMSHI General Trading,
 * Dubai, UAE.
 */


private const val BASE_URL = "https://api.nasa.gov"


// create the retrofit with ScalerConverterFactory and Base_Url
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()


interface AsteroidApiService {
    // the key in the get annotation meant to be appended to the base
    // url to initiate the network call
    @GET("apod?api_key=YsXKeWV1QrknzaZTicw2eY3nmAgRNz4hxmelbb5O")
    fun getAsteroids(): Call<String>
}

// we create retrofit using object to expose it to the hole application because
// it's an expensive process
object AsteroidApi {
    val retrofitService: AsteroidApiService by lazy {
        retrofit.create(AsteroidApiService::class.java)
    }
}