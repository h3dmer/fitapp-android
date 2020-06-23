package com.sport.project.fitapp.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.sport.project.fitapp.db.dao.WorkoutsDao
import com.sport.project.fitapp.db.entities.Workout
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat
import java.util.*

@RunWith(AndroidJUnit4ClassRunner::class)
class WorkoutsDaoTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var appDatabase: AppDatabase
    private lateinit var workoutsDao: WorkoutsDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        workoutsDao = appDatabase.workoutsDao()
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }

    @Test
    fun insertionWorkout() {
        val date =
            SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Calendar.getInstance().time)
        val workout = Workout(0, "Test value", date, listOf())

        workoutsDao.insert(workout).blockingAwait()

        workoutsDao.workouts().test().assertValue { workouts ->
            workouts.isNotEmpty()
        }
    }

    @Test
    fun deleteWorkout() {
        val date =
            SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Calendar.getInstance().time)
        val workout = Workout(1, "Test value", date, listOf())

        workoutsDao.insert(workout).blockingAwait()

        workoutsDao.workouts().subscribe({
            workoutsDao.deleteWorkout(workout).blockingAwait()
        }, { throwable -> throwable.printStackTrace() })

        workoutsDao.workouts().test().assertValue { workouts ->
            workouts.isEmpty()
        }
    }

    @Test
    fun updateWorkout() {
        val date =
            SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Calendar.getInstance().time)
        val newDate = SimpleDateFormat(
            "dd.MM.yyyy",
            Locale.getDefault()
        ).format(Calendar.getInstance().time)
            .format(Calendar.getInstance().add(Calendar.DAY_OF_YEAR, 1))
        val workout = Workout(0, "Hard workout", date, listOf())

        workoutsDao.insert(workout).blockingAwait()

        workoutsDao.workouts()
            .doOnError { throwable -> throwable.printStackTrace() }
            .doOnNext { workouts ->

                workoutsDao.updateWorkout(
                    Workout(
                        workouts[0].id,
                        "Easy workout",
                        newDate,
                        listOf()
                    )
                ).blockingAwait()

                workoutsDao.workouts().test().assertValue { updatedWorkout ->
                    updatedWorkout[0].date = newDate
                    updatedWorkout[0].name == "Easy workout"
                }
            }
    }
}