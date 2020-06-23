package com.sport.project.fitapp.ui.workoutdetails.ui.updateexercisedialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sport.project.fitapp.R
import com.sport.project.fitapp.db.entities.Exercise
import com.sport.project.fitapp.db.entities.Workout
import com.sport.project.fitapp.db.entities.WorkoutExercise
import com.sport.project.fitapp.ui.workouts.data.WorkoutsRepository
import com.sport.project.fitapp.utils.event.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UpdateExerciseDialogViewModel @Inject constructor(
    private val workoutsRepository: WorkoutsRepository,
    private val disposables: CompositeDisposable
) : ViewModel() {

    val muscleParts: LiveData<List<Exercise>> =
        LiveDataReactiveStreams.fromPublisher(workoutsRepository.getAllExercises())

    private val _exercise = MutableLiveData<Exercise>()
    val exercise: LiveData<Exercise> get() = _exercise

    private val _updateWorkoutEvent = MutableLiveData<Event<Unit>>()
    val updateWorkoutEvent: LiveData<Event<Unit>> get() = _updateWorkoutEvent

    private val _workoutExercises = MutableLiveData<List<WorkoutExercise>>()

    private val _snackbarMessage = MutableLiveData<Event<Int>>()
    val snackbarMessage: LiveData<Event<Int>> get() = _snackbarMessage

    val muscleName = MutableLiveData<String>()
    val exerciseName = MutableLiveData<String>()
    val sets = MutableLiveData<String>()
    val reps = MutableLiveData<String>()

    private val _updatingExercise = MutableLiveData<WorkoutExercise>()
    val updatingExercise: LiveData<WorkoutExercise> get() =  _updatingExercise


    init {
        _workoutExercises.value = listOf()
    }

    fun getMusclesParts(): List<String> {
        return muscleParts.value.orEmpty().map { it.name }
    }

    fun setExercise(muscle: String) {
        _exercise.value = muscleParts.value?.single { muscle == it.name }
    }

    fun updateExercise(workout: Workout) {
        if (checkInputs()) {
            _workoutExercises.value = _workoutExercises.value?.minus(_updatingExercise.value!!)
            val newExercise =  WorkoutExercise(
                _updatingExercise.value?.id!!, exerciseName.value.orEmpty(),
                muscleName.value.orEmpty(), sets.value?.toInt()!!, reps.value?.toInt()!!
            )
            _workoutExercises.value = _workoutExercises.value?.plus(newExercise)

            _workoutExercises.value?.let {
                workoutsRepository.updateWorkout(Workout(id = workout.id, name = workout.name, date = workout.date, workoutExercises = it))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnComplete { _updateWorkoutEvent.postValue(Event(Unit)) }
                    .subscribe()
                    .addTo(disposables)
            }
        } else { _snackbarMessage.value = Event(R.string.error_add_exercise) }
    }

    fun initExercise(exercise: WorkoutExercise, workout: Workout?) {
        _updatingExercise.value = exercise
        muscleName.value = exercise.muscle
        _workoutExercises.value = _workoutExercises.value?.plus(workout?.workoutExercises.orEmpty())
    }

    private fun checkInputs(): Boolean {
        return !(muscleName.value.isNullOrEmpty() || sets.value.isNullOrEmpty() ||
                reps.value.isNullOrEmpty())
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}