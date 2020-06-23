package com.sport.project.fitapp.ui.nutritions.ui.state

sealed class NutritionViewState<out T : Any>{

    object None: NutritionViewState<Nothing>()

    object Loading: NutritionViewState<Nothing>()

    data class NutritionFailure(val error: Exception): NutritionViewState<Nothing>()

    data class NutritionSuccess<out T : Any>(val data: T): NutritionViewState<T>() {
        inline fun <reified T> isInstanceOf(instance: Any?): Boolean {
            return instance is T
        }
    }
}