package com.udacity.asteroidradar.db

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*


/**
 * Created by Ahmad Mansour on 17/10/2021
 * Dubai, UAE.
 */


@Dao
interface AsteroidDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg databaseAsteroid: DatabaseAsteroid)

    @Update
    fun update(databaseAsteroid: DatabaseAsteroid)

    @Query("SELECT * FROM asteroids_table WHERE id = :key")
    fun get(key: Long): DatabaseAsteroid

    @Query("DELETE FROM asteroids_table")
    fun clear()

    @Query("SELECT * FROM asteroids_table ORDER BY id DESC")
    fun getAllAsteroids(): LiveData<List<DatabaseAsteroid>>

    @Query("SELECT * FROM asteroids_table ORDER BY id DESC LIMIT 1")
    fun getAnAsteroid(): DatabaseAsteroid?
}


@Database(entities = [DatabaseAsteroids::class], version = 1)
abstract class DatabaseAsteroids : RoomDatabase() {
    abstract val asteroidsDao: AsteroidDatabaseDao
}

private lateinit var INSTANCE: DatabaseAsteroids

fun getDatabase(context: Context): DatabaseAsteroids {
    synchronized(DatabaseAsteroids::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                DatabaseAsteroids::class.java,
                "asteroids_history_database"
            ).build()
        }
    }
    return INSTANCE
}

