package com.udacity.asteroidradar.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.domain.Asteroid
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "asteroids_table")
data class DatabaseAsteroid(
    @PrimaryKey(autoGenerate = true)
    var id: Long,

    @ColumnInfo(name = "codename")
    var codename: String,

    @ColumnInfo(name = "close_approach_date")
    var closeApproachDate: String,

    @ColumnInfo(name = "absolute_magnitude")
    var absoluteMagnitude: Double,

    @ColumnInfo(name = "estimated_diameter")
    var estimatedDiameter: Double,

    @ColumnInfo(name = "relative_velocity")
    var relativeVelocity: Double,

    @ColumnInfo(name = "distance_from_earth")
    var distanceFromEarth: Double,

    @ColumnInfo(name = "is_potentially_hazardous")
    var isPotentiallyHazardous: Boolean
) : Parcelable

/**
 * Convert Network results to database objects
 */
fun List<DatabaseAsteroid>.asDomainModel(): List<Asteroid> {
    return map {
        Asteroid(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}