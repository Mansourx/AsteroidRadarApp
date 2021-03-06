package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.db.AsteroidsDatabase
import com.udacity.asteroidradar.db.asDomainModel
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.network.AsteroidApi
import com.udacity.asteroidradar.network.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject


/**
 * Created by Ahmad Mansour on 23/10/2021
 * Dubai, UAE.
 */


/**
 * Repository Stores the Asteroids History form the network and stores them to the Disk
 */

class AsteroidsRepository(private val database: AsteroidsDatabase) {

    /**
     * A list of Asteroids that can be shown on the screen.
     */
    private val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidsDao.getAllAsteroids()) {
            it.asDomainModel()
        }

    /**
     * Refresh the Asteroids stored in the offline cache.
     *
     * This function uses the IO dispatcher to ensure the database insert database operation
     * happens on the IO dispatcher. By switching to the IO dispatcher using `withContext` this
     * function is now safe to call from any thread including the Main thread.
     *
     * To actually load the videos for use, observe [asteroids]
     */
    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val asteroidsList = parseAsteroidsJsonResult(
                JSONObject(AsteroidApi.retrofitService.getAsteroidsListAsync().await())
            )
            database.asteroidsDao.insert(*asteroidsList.toTypedArray())
        }
    }

    suspend fun deleteAsteroidsFromPreviousDay() {
        withContext(Dispatchers.IO) {
            database.asteroidsDao.clear()
        }
    }
}