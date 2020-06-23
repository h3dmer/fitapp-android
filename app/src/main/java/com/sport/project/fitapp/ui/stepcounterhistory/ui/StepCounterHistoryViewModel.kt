package com.sport.project.fitapp.ui.stepcounterhistory.ui

import androidx.lifecycle.ViewModel
import com.sport.project.fitapp.db.dao.DailyStepsDao
import javax.inject.Inject

class StepCounterHistoryViewModel @Inject constructor(dailyStepsDao: DailyStepsDao) :
    ViewModel() {

    var stepsHistory =
        dailyStepsDao.getAllObservable()

    init {
        stepsHistory
    }
}