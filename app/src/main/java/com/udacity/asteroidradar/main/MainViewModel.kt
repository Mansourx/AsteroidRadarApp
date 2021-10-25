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
import com.udacity.asteroidradar.network.parseAsteroidsJsonResult
import com.udacity.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel(application: Application) :
    AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val asteroidsRepository = AsteroidsRepository(database)

    private var _networkFailure = MutableLiveData<String>()
    val netWorkFailure: LiveData<String>
        get() = _networkFailure

    private var _asteroids = MutableLiveData<List<DatabaseAsteroid>?>()
    val asteroids = database.asteroidsDao.getAllAsteroids()

    private val _pictureOfTheDay = MutableLiveData<PictureOfDay>()
    val pictureOfTheDay: LiveData<PictureOfDay>
        get() = _pictureOfTheDay

    private val _navigateToAsteroidDetails = MutableLiveData<DatabaseAsteroid?>()
    val navigateToDatabaseAsteroidDetails: MutableLiveData<DatabaseAsteroid?>
        get() = _navigateToAsteroidDetails


    init {
        viewModelScope.launch {
            asteroidsRepository.refreshAsteroids()
        }

        getPictureOfTheDay()
        pullAsteroidsFromAPi()
    }

    val asteroidsList = asteroidsRepository.asteroids

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

    private fun pullAsteroidsFromAPi() {
        AsteroidApi.retrofitService.getAsteroids().enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                _asteroids.value = response.body()?.let {
                    parseAsteroidsJsonResult(JSONObject(it))
                }
                viewModelScope.launch {
                    insert(_asteroids.value)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
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

    private suspend fun insert(databaseAsteroids: List<DatabaseAsteroid>?) {
        withContext(Dispatchers.IO) {
            databaseAsteroids?.forEach {
                database.asteroidsDao.insert(it)
            }
        }
    }
}