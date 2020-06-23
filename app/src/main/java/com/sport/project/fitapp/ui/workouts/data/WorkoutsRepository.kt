package com.sport.project.fitapp.ui.workouts.data

import com.sport.project.fitapp.db.entities.Exercise
import com.sport.project.fitapp.db.entities.Workout
import com.sport.project.fitapp.repository.Repository
import io.reactivex.Completable
import io.reactivex.Flowable

interface WorkoutsRepository: Repository {

    fun getWorkout(id: Long): Flowable<Workout>

    fun workouts(): Flowable<List<Workout>>

    fun insertWorkout(workout: Workout): Completable

    fun updateWorkout(workout: Workout): Completable

    fun getAllExercises(): Flowable<List<Exercise>>

    fun deleteWorkout(workout: Workout): Completable

}