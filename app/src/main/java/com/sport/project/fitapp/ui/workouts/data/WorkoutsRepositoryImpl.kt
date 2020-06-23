package com.sport.project.fitapp.ui.workouts.data

import com.sport.project.fitapp.db.dao.ExercisesDao
import com.sport.project.fitapp.db.dao.WorkoutsDao
import com.sport.project.fitapp.db.entities.Exercise
import com.sport.project.fitapp.db.entities.Workout
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class WorkoutsRepositoryImpl @Inject constructor(
    private val workoutsDao: WorkoutsDao,
    private val exercisesDao: ExercisesDao
) : WorkoutsRepository {

    override fun getWorkout(id: Long): Flowable<Workout> {
        return workoutsDao.getWorkout(id)
    }

    override fun workouts(): Flowable<List<Workout>> {
        return workoutsDao.workouts()
    }

    override fun insertWorkout(workout: Workout): Completable {
        return workoutsDao.insert(workout)
    }

    override fun getAllExercises(): Flowable<List<Exercise>> {
        return exercisesDao.getAllExercises()
    }

    override fun deleteWorkout(workout: Workout): Completable {
        return workoutsDao.deleteWorkout(workout)
    }

    override fun updateWorkout(workout: Workout): Completable {
        return workoutsDao.updateWorkout(workout)
    }
}