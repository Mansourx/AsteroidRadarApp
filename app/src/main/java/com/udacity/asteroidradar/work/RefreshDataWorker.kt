package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.db.getDatabase
import com.udacity.asteroidradar.repository.AsteroidsRepository


/**
 * Created by Ahmad Mansour on 25/10/2021
 * Dubai, UAE.
 */


class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = AsteroidsRepository(database)
        return try {
            repository.refreshAsteroids()
            Result.success()
        } catch (exception: Exception) {
            Result.retry()
        }
    }
}