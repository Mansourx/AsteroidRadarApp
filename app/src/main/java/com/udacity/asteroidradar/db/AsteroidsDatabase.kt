//package com.udacity.asteroidradar.db
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//
//
///**
// * Created by Ahmad Mansour on 17/10/2021
// * NAMSHI General Trading,
// * Dubai, UAE.
// */
//
//
////@Database(entities = [DatabaseAsteroid::class], version = 1, exportSchema = false)
////abstract class AsteroidsDatabase : RoomDatabase() {
////
////    abstract val asteroidDatabaseDao: AsteroidDatabaseDao
////
////    companion object {
////        @Volatile
////        private var INSTANCE: AsteroidsDatabase? = null
////        fun getInstance(context: Context): AsteroidsDatabase {
////            synchronized(this) {
////                var instance = INSTANCE
////                if (instance == null) {
////                    instance = Room.databaseBuilder(
////                        context.applicationContext,
////                        AsteroidsDatabase::class.java,
////                        "asteroids_history_database"
////                    )
////                        .fallbackToDestructiveMigration()
////                        .build()
////                    INSTANCE = instance
////                }
////                return instance
////            }
////        }
////    }
////}