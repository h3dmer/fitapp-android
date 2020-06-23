package com.sport.project.fitapp.ui.stepcounter.data

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.sport.project.fitapp.db.AppDatabase
import com.sport.project.fitapp.db.entities.DailySteps
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class DateChangeBroadcastReceiver : BroadcastReceiver() {

    @SuppressLint("CheckResult")
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (Intent.ACTION_DATE_CHANGED == action) {
            val db = AppDatabase.getInstance(context)

            @SuppressLint("SimpleDateFormat")
            val newDate = SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().time)
            db.dailyStepsDao().getLatestSteps()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { ds ->
                    addNewDay(db, newDate, ds.goal)
                }
        }
    }

    private fun addNewDay(db: AppDatabase, newDate: String, lastGoal: Int) {
        db.dailyStepsDao().insert(DailySteps(0, newDate, 0, lastGoal))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }
}