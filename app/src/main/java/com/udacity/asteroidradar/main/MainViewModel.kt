package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.db.DatabaseAsteroid
import com.udacity.asteroidradar.db.getDatabase
import com.udacity.asteroidradar.network.AsteroidApi
import com.udacity.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber


class MainViewModel(application: Application) :
    AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val asteroidsRepository = AsteroidsRepository(database)

    private var _networkFailure = MutableLiveData<String>()
    val netWorkFailure: LiveData<String>
        get() = _networkFailure

    val asteroids: LiveData<List<DatabaseAsteroid>> = database.asteroidsDao.getAllAsteroids()

    private val _pictureOfTheDay = MutableLiveData<PictureOfDay>()
    val pictureOfTheDay: LiveData<PictureOfDay>
        get() = _pictureOfTheDay

    private val _navigateToAsteroidDetails = MutableLiveData<DatabaseAsteroid?>()
    val navigateToDatabaseAsteroidDetails: MutableLiveData<DatabaseAsteroid?>
        get() = _navigateToAsteroidDetails

    init {
        getPictureOfTheDay()

        viewModelScope.launch {
            try {
                asteroidsRepository.refreshAsteroids()
            } catch (e: Exception) {
                Timber.e(e.toString())
            }
        }
    }

    private fun getPictureOfTheDay() {
        AsteroidApi.retrofitService.getTodayImage().enqueue(object : Callback<PictureOfDay> {
            override fun onResponse(call: Call<PictureOfDay>, response: Response<PictureOfDay>) {
                _pictureOfTheDay.value = response.body()
            }

            override fun onFailure(call: Call<PictureOfDay>, t: Throwable) {
                _networkFailure.value = t.message
            }
        })
    }

    fun displayAsteroid(databaseAsteroid: DatabaseAsteroid) {
        _navigateToAsteroidDetails.value = databaseAsteroid
    }

    fun displayAsteroidCompleted() {
        _navigateToAsteroidDetails.value = null
    }
}