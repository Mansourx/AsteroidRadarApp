package com.udacity.asteroidradar.domain


/**
 * Created by Ahmad Mansour on 23/10/2021
 * Dubai, UAE.
 */


/**
 * Domain objects are plain Kotlin data classes that represent the things in our app. These are the
 * objects that should be displayed on screen, or manipulated by the app.
 *
 * @see database for objects that are mapped to the database
 * @see network for objects that parse or prepare network calls
 */

/**
 * Videos represent a devbyte that can be played.
 */

data class Asteroid(
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
) {
    /**
     * Short description is used for displaying truncated descriptions in the UI
     */
//    val shortDescription: String
//        get() = description.smartTruncate(200)
}