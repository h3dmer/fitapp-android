package com.sport.project.fitapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sport.project.fitapp.db.converters.StringListConverter
import com.sport.project.fitapp.db.converters.WorkoutConverter
import com.sport.project.fitapp.db.dao.DailyStepsDao
import com.sport.project.fitapp.db.dao.ExercisesDao
import com.sport.project.fitapp.db.dao.WorkoutsDao
import com.sport.project.fitapp.db.entities.BasicData.abs
import com.sport.project.fitapp.db.entities.BasicData.back
import com.sport.project.fitapp.db.entities.BasicData.biceps
import com.sport.project.fitapp.db.entities.BasicData.calf
import com.sport.project.fitapp.db.entities.BasicData.chest
import com.sport.project.fitapp.db.entities.BasicData.forearm
import com.sport.project.fitapp.db.entities.BasicData.legs
import com.sport.project.fitapp.db.entities.BasicData.shoulder
import com.sport.project.fitapp.db.entities.BasicData.triceps
import com.sport.project.fitapp.db.entities.DailySteps
import com.sport.project.fitapp.db.entities.Exercise
import com.sport.project.fitapp.db.entities.Workout
import com.sport.project.fitapp.utils.ioThread

@Database(entities = [Exercise::class, DailySteps::class, Workout::class],
    version = 1, exportSchema = false)
@TypeConverters(StringListConverter::class, WorkoutConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun exercisesDao(): ExercisesDao

    abstract fun dailyStepsDao(): DailyStepsDao

    abstract fun workoutsDao(): WorkoutsDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "fitapp-db")
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        ioThread {
                            getInstance(context).exercisesDao().addAllExercises(DATA)
                            db.execSQL("INSERT INTO daily_steps_table VALUES (NULL, date(), 0, 0)")
                        }
                    }
                })
                .build()
        }

        private val DATA: List<Exercise> = listOf(abs, chest, shoulder, biceps, triceps, legs, calf, forearm, back)
    }
}