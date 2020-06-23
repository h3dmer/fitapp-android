package com.sport.project.fitapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sport.project.fitapp.db.entities.DailySteps
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
abstract class DailyStepsDao {

    @Insert
    abstract fun insert(day: DailySteps): Single<Unit>

    @Update
    abstract fun update(day: DailySteps)

    @Query("SELECT * FROM daily_steps_table WHERE id = :key")
    abstract fun getDailySteps(key: Long): LiveData<DailySteps?>

    @Query("UPDATE daily_steps_table SET goal = :newGoal WHERE id IN(SELECT id FROM daily_steps_table ORDER BY date DESC LIMIT 1)")
    abstract fun updateDailyGoal(newGoal: Int): Single<Unit>

    @Query("UPDATE daily_steps_table SET steps = steps + :steps WHERE id IN(SELECT id FROM daily_steps_table ORDER BY date DESC LIMIT 1)")
    abstract fun updateDailySteps(steps: Int): Single<Unit>

    @Query("SELECT * FROM daily_steps_table ORDER BY date DESC")
    abstract fun getAllObservable(): LiveData<List<DailySteps>>

    @Query("SELECT * FROM daily_steps_table ORDER BY date DESC LIMIT 1")
    abstract fun getLatestSteps(): Maybe<DailySteps>

    @Query("SELECT * FROM daily_steps_table ORDER BY date DESC LIMIT 1")
    abstract fun getLatestObservable(): Flowable<DailySteps>

//    @Query("DELETE FROM daily_steps_table WHERE id NOT IN(SELECT id FROM daily_steps_table ORDER BY date DESC LIMIT 1)")
//    fun deleteAllButLatest()

}