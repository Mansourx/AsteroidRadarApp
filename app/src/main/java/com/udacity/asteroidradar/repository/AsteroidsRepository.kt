package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.db.DatabaseAsteroids
import com.udacity.asteroidradar.db.asDomainModel
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.network.AsteroidApi
import com.udacity.asteroidradar.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await


/**
 * Created by Ahmad Mansour on 23/10/2021
 * Dubai, UAE.
 */


/**
 * Repository Stores the Asteroids History form the network and stores them to the Disk
 */

class AsteroidsRepository(private val database: DatabaseAsteroids) {

    /**
     * A playlist of videos that can be shown on the screen.
     */
    val asteroids: LiveData<List<Asteroid>> =
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
     * To actually load the videos for use, observe [videos]
     */
    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val asteroidsList = AsteroidApi.retrofitService.getAsteroidsListAsync().await()
            database.asteroidsDao.insert(*asteroidsList.asDatabaseModel())
        }
    }
}