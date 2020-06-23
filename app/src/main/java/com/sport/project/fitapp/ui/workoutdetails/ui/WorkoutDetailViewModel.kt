package com.sport.project.fitapp.ui.workoutdetails.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sport.project.fitapp.R
import com.sport.project.fitapp.db.entities.Workout
import com.sport.project.fitapp.db.entities.WorkoutExercise
import com.sport.project.fitapp.ui.workouts.data.WorkoutsRepository
import com.sport.project.fitapp.utils.event.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WorkoutDetailViewModel @Inject constructor(
    private val workoutsRepository: WorkoutsRepository,
    private val disposables: CompositeDisposable
) : ViewModel() {

    private var _workout = MutableLiveData<Workout>()
    val workout: LiveData<Workout> get() = _workout

    private var _navigateBack = MutableLiveData<Event<Unit>>()
    val navigateBack: LiveData<Event<Unit>> get() = _navigateBack

    private var _errorText = MutableLiveData<Event<Int>>()
    val errorText: LiveData<Event<Int>> get() = _errorText

    private val _updateExerciseEvent = MutableLiveData<Event<Unit>>()
    val updateExerciseEvent: LiveData<Event<Unit>> get() = _updateExerciseEvent

    fun getWorkout(id: Long) {
        workoutsRepository.getWorkout(id)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { workout -> _workout.postValue(workout) },
                { _errorText.postValue(Event(R.string.workout_details_error)) }
            ).addTo(disposables)
    }

    fun deleteExerciseFromWorkout(exercise: WorkoutExercise) {
        _workout.value?.let {
            workoutsRepository.updateWorkout(
                it.copy(
                    workoutExercises = it.workoutExercises.minus(
                        exercise
                    )
                )
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
                .addTo(disposables)
        }
    }

    fun navigateBack() {
        _navigateBack.value = Event(Unit)
    }

    fun updateExercise() {
        _updateExerciseEvent.value = Event(Unit)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}