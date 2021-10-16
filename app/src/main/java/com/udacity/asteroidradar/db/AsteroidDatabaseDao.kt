package com.udacity.asteroidradar.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.Asteroid


/**
 * Created by Ahmad Mansour on 17/10/2021
 * NAMSHI General Trading,
 * Dubai, UAE.
 */


@Dao
interface AsteroidDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(asteroid: Asteroid)

    @Update
    fun update(asteroid: Asteroid)

    @Query("SELECT * FROM asteroids_table WHERE id = :key")
    fun get(key: Long): Asteroid

    @Query("DELETE FROM asteroids_table")
    fun clear()

    @Query("SELECT * FROM asteroids_table ORDER BY id DESC")
    fun getAllAsteroids(): LiveData<List<Asteroid>>

    @Query("SELECT * FROM asteroids_table ORDER BY id DESC LIMIT 1")
    fun getAnAsteroid(): Asteroid?
}
