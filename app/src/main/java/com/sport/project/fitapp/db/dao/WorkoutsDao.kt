package com.sport.project.fitapp.db.dao

import androidx.annotation.VisibleForTesting
import androidx.room.*
import com.sport.project.fitapp.db.entities.Workout
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
abstract class WorkoutsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(workout: Workout): Completable

    @Query("SELECT * FROM workouts WHERE id = :key")
    abstract fun getWorkout(key: Long): Flowable<Workout>

    @VisibleForTesting
    @Query("SELECT * FROM workouts")
    abstract fun workouts(): Flowable<List<Workout>>

    @Update
    abstract fun updateWorkout(workout: Workout): Completable

    @Delete
    abstract fun deleteWorkout(workout: Workout): Completable

}