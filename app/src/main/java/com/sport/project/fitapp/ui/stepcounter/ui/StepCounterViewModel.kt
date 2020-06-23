package com.sport.project.fitapp.ui.stepcounter.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sport.project.fitapp.db.dao.DailyStepsDao
import com.sport.project.fitapp.db.entities.DailySteps
import com.sport.project.fitapp.ui.stepcounter.utils.ObservePreferences
import com.sport.project.fitapp.utils.DateExt
import com.sport.project.fitapp.utils.event.Event
import com.sport.project.fitapp.utils.observeOnce
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class StepCounterViewModel @Inject constructor(
    private val dailyStepsDao: DailyStepsDao,
    private val preferences: ObservePreferences
) : ViewModel() {

    private val _navigationToHistory = MutableLiveData<Event<Boolean>>()
    val navigationToHistory: LiveData<Event<Boolean>>
        get() = _navigationToHistory

    private val days = dailyStepsDao.getAllObservable()

    val serviceStatus = preferences.stepCounting

    val today = Transformations.map(days) {
        it.first()
    }

    init {
        today.observeOnce(Observer { addMissingDay(it) })
    }

    private fun addMissingDay(dailySteps: DailySteps) {
        val currentDate = Calendar.getInstance()
        currentDate.set(Calendar.HOUR_OF_DAY, 0)
        currentDate.set(Calendar.MINUTE, 0)
        currentDate.set(Calendar.SECOND, 0)
        currentDate.set(Calendar.MILLISECOND, 0)

        val lastDate = Calendar.getInstance()
        lastDate.time = DateExt.standardParse(dailySteps.date)

        while (lastDate.time < currentDate.time) {

            lastDate.add(Calendar.DATE, 1)
            val newDay = DailySteps(
                0,
                DateExt.standardFormat(lastDate.time),
                0
            )
            dailyStepsDao
                .insert(newDay)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        }
    }

    fun updateDailyGoal(goal: Int) {
        dailyStepsDao.updateDailyGoal(goal)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun getServiceStatus() = preferences.stepCounting.value!!

    fun setServiceStatus(status: Boolean) {
        preferences.setAutoStepCounting(status)
    }

    fun setNavigationToHistory(state: Boolean) {
        _navigationToHistory.value = Event(state)
    }

    override fun onCleared() {
        super.onCleared()
        preferences.destroy()
    }
}