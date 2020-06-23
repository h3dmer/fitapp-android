package com.sport.project.fitapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sport.project.fitapp.db.entities.Exercise
import io.reactivex.Flowable

@Dao
abstract class ExercisesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addAllExercises(list: List<Exercise>)

    @Query("SELECT * FROM all_exercises")
    abstract fun getAllExercises(): Flowable<List<Exercise>>

}