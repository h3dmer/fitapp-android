package com.sport.project.fitapp.ui.nutritions.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sport.project.fitapp.network.networkDTO.CalculateExercise
import com.sport.project.fitapp.network.networkDTO.NaturalExercise
import com.sport.project.fitapp.network.networkDTO.NaturalLanguageFoodQuery
import com.sport.project.fitapp.network.networkDTO.Nutrients
import com.sport.project.fitapp.ui.nutritions.data.NaturalLanguageRepositoryImpl
import com.sport.project.fitapp.ui.nutritions.ui.state.NutritionViewState
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class NutritionViewModel @Inject constructor(
    private val repository: NaturalLanguageRepositoryImpl,
    private val disposable: CompositeDisposable
) : ViewModel() {

    private val _fetchExercises = MutableLiveData<NaturalExercise>()
    val fetchExercises: MutableLiveData<NaturalExercise> get() = _fetchExercises

    private val _exerciseViewState =
        MutableLiveData<NutritionViewState<NaturalExercise>>().apply {
            postValue(NutritionViewState.None)
        }
    val exerciseViewState: LiveData<NutritionViewState<NaturalExercise>> = _exerciseViewState

    private val _foodsViewState =
        MutableLiveData<NutritionViewState<Nutrients>>().apply { value = NutritionViewState.None }
    val foodsViewState: LiveData<NutritionViewState<Nutrients>> get() = _foodsViewState

    fun burnedCaloriesFromExercises(query: CalculateExercise) {
        _exerciseViewState.postValue(NutritionViewState.Loading)
        disposable.add(
            repository.calculateCaloriesExercises(query)
                .subscribe(
                    {
                        _exerciseViewState.postValue(NutritionViewState.NutritionSuccess(it))
                        fetchExercises.value = it
                    },
                    { _exerciseViewState.postValue(NutritionViewState.NutritionFailure(Exception(it.message))) }
                )
        )
    }

    fun burnedCaloriesFromFood(query: NaturalLanguageFoodQuery) {
        _foodsViewState.postValue(NutritionViewState.Loading)
        disposable.add(
            repository.calculateCaloriesNutrition(query)
                .subscribe(
                    { _foodsViewState.value = NutritionViewState.NutritionSuccess(it) },
                    { _foodsViewState.value = NutritionViewState.NutritionFailure(Exception(it.message)) }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}