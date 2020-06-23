package com.sport.project.fitapp.ui.traininglist.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sport.project.fitapp.db.entities.Exercise
import com.sport.project.fitapp.ui.workouts.data.WorkoutsRepository
import com.sport.project.fitapp.utils.event.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WorkoutsListViewModel @Inject constructor(
    private val repository: WorkoutsRepository
) : ViewModel() {

    private var disposable = CompositeDisposable()

    private var _workoutList: MutableLiveData<List<Exercise>> = MutableLiveData()

    private val _navToWorkouts = MutableLiveData<Event<Unit>>()
    val navToWorkouts: LiveData<Event<Unit>>
        get() = _navToWorkouts

    val workoutList: LiveData<List<Exercise>>
        get() = _workoutList

    init {
        loadExercises()
    }

    private fun loadExercises() {
        disposable.add(
            repository.getAllExercises()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { data -> _workoutList.value = data },
                    { error -> Log.d("WorkoutsViewModel -> ", error.message.toString()) }
                )
        )
    }

    fun navigateToWorkouts() {
        _navToWorkouts.value = Event(Unit)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}