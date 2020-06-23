package com.sport.project.fitapp.ui.workouts.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sport.project.fitapp.db.entities.Workout
import com.sport.project.fitapp.ui.workouts.data.WorkoutsRepository
import com.sport.project.fitapp.utils.event.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WorkoutsViewModel @Inject constructor(
    private val repository: WorkoutsRepository
) : ViewModel() {

    val workouts = LiveDataReactiveStreams.fromPublisher(repository.workouts())

    private val _updateWorkoutEvent = MutableLiveData<Event<Workout>>()
    val updateWorkoutEvent: LiveData<Event<Workout>> get() = _updateWorkoutEvent

    private val _navigateBackEvent = MutableLiveData<Event<Unit>>()
    val navigateBackEvent: LiveData<Event<Unit>> get() = _navigateBackEvent

    private val _addNewWorkoutEvent = MutableLiveData<Event<Unit>>()
    val addNewWorkoutEvent: LiveData<Event<Unit>> get() = _addNewWorkoutEvent

    private val _deleteWorkoutEvent = MutableLiveData<Event<Workout>>()
    val deleteWorkoutEvent: LiveData<Event<Workout>> get() = _deleteWorkoutEvent

    fun addNewWorkout() {
        _addNewWorkoutEvent.value = Event(Unit)
    }

    fun navigateBack() {
        _navigateBackEvent.value = Event(Unit)
    }

    fun deleteWorkout(workout: Workout) {
        repository.deleteWorkout(workout)
            .subscribeOn(Schedulers.io())
            .doOnComplete {
                _deleteWorkoutEvent.postValue(Event(workout))
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun updateWorkout(workout: Workout) {
        _updateWorkoutEvent.value = Event(workout)
    }
}