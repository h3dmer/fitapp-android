package com.sport.project.fitapp.ui.workouts.ui.workoutsdialog

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
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class WorkoutsDialogViewModel @Inject constructor(
    private val workoutsRepository: WorkoutsRepository,
    private val disposables: CompositeDisposable
) : ViewModel() {

    val muscleParts: LiveData<List<Exercise>> =
        LiveDataReactiveStreams.fromPublisher(workoutsRepository.getAllExercises())

    private var isNewWorkout: Boolean = false

    val workoutName = MutableLiveData<String>()
    val muscleName = MutableLiveData<String>()
    val exerciseName = MutableLiveData<String>()
    val sets = MutableLiveData<String>()
    val reps = MutableLiveData<String>()

    private val _exercise = MutableLiveData<Exercise>()
    val exercise: LiveData<Exercise> get() = _exercise

    private val _workout = MutableLiveData<Workout>()
    val workout: LiveData<Workout> get() = _workout

    private var _workoutExercises = MutableLiveData<List<WorkoutExercise>>()
    val workoutExercises: LiveData<List<WorkoutExercise>> get() = _workoutExercises

    private val _addWorkoutEvent = MutableLiveData<Event<Unit>>()
    val addWorkoutEvent: LiveData<Event<Unit>> = _addWorkoutEvent

    private val _snackbarMessage = MutableLiveData<Event<Int>>()
    val snackbarMessage: LiveData<Event<Int>> get() = _snackbarMessage

    init {
        _workoutExercises.value = listOf()
    }

    fun getMusclesParts(): List<String> {
        return muscleParts.value.orEmpty().map { it.name }
    }

    fun setExercise(muscle: String) {
        _exercise.value = muscleParts.value?.single { muscle == it.name }
    }

    fun checkIfNew(workout: Workout?) {
        if (workout?.id == null) {
            isNewWorkout = true
            return
        }
        loadData(workout)
    }

    fun addExercise() {
        if (checkInputs()) {
            val workoutExercise = WorkoutExercise(
                id = workoutExercises.value.orEmpty().size.plus(1),
                name = exerciseName.value.toString(), muscle = muscleName.value.toString(),
                sets = sets.value!!.toInt(), reps = reps.value!!.toInt()
            )
            _workoutExercises.value = _workoutExercises.value?.plus(workoutExercise)
        } else {
            _snackbarMessage.value = Event(R.string.error_add_exercise)
        }
    }

    fun deleteWorkout(workoutExercise: WorkoutExercise) {
        _workoutExercises.value = _workoutExercises.value?.minus(workoutExercise)
    }

    fun saveWorkout() {
        if (workoutName.value.isNullOrEmpty()) {
            _snackbarMessage.value = Event(R.string.workout_empty_name)
            return
        }

        val date = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Calendar.getInstance().time)
        _workout.value?.date = date

        if (isNewWorkout) {
            _workout.value = Workout(name = workoutName.value.orEmpty(), date = date, workoutExercises = workoutExercises.value!!)
            workout.value?.let { workout -> saveNewWorkout(workout)}
            return
        }

        workout.value?.let { workout -> saveExistingWorkout(Workout(workout.id, workoutName.value.toString(), date, workoutExercises.value!!)) }
    }

    private fun saveNewWorkout(workout: Workout) {
        workoutsRepository.insertWorkout(workout)
            .subscribeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .doOnComplete { _addWorkoutEvent.postValue(Event(Unit)) }
            .subscribe().addTo(disposables)
    }

    private fun saveExistingWorkout(workout: Workout) {
        workoutsRepository.updateWorkout(workout)
            .subscribeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .doOnComplete { _addWorkoutEvent.postValue(Event(Unit)) }
            .subscribe().addTo(disposables)
    }

    private fun checkInputs(): Boolean {
        return !(exerciseName.value.isNullOrEmpty() || muscleName.value.isNullOrEmpty() || sets.value.isNullOrEmpty() ||
                reps.value.isNullOrEmpty())
    }

    private fun loadData(workout: Workout) {
        _workout.value = workout
        _workoutExercises.value = _workoutExercises.value?.plus(workout.workoutExercises)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
