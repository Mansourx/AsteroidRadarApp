package com.udacity.asteroidradar.network

import android.os.Build
import androidx.annotation.RequiresApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by Ahmad Mansour on 16/10/2021
 * Dubai, UAE.
 */


/**
 *  We use Moshi Library Object to convert the JSON response to
 *  kotlin Object instead of String Object
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val next7DaysFormattedDates: List<String>
    @RequiresApi(Build.VERSION_CODES.N)
    get() = getNextSevenDaysFormattedDates()

/**
 *  create the retrofit with ScalerConverterFactory and Base_Url
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    // .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(Constants.BASE_URL)
    .build()

interface AsteroidApiService {
    @GET(Constants.IMAGE_OF_DAY_LINK)
    fun getTodayImage(
        @Query(Constants.API_KEY_NAME) apiKey: String = BuildConfig.NASA_API_KEY
    ): Call<PictureOfDay>

    @GET(Constants.ASTEROID_LINK)
    fun getAsteroidsListAsync(
        @Query(Constants.START_DATE_KEY) startDate: String = next7DaysFormattedDates[0],
        @Query(Constants.END_DATE_KEY) endDate: String = next7DaysFormattedDates[6],
        @Query(Constants.API_KEY_NAME) apiKey: String = BuildConfig.NASA_API_KEY
    ): Deferred<String>
}


/**
 *  we create retrofit using object to expose it to the hole application because
 *  it's an expensive process*
 * */
object AsteroidApi {
    val retrofitService: AsteroidApiService by lazy {
        retrofit.create(AsteroidApiService::class.java)
    }
}

