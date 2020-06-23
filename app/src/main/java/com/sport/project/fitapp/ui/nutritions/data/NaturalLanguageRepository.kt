package com.sport.project.fitapp.ui.nutritions.data

import com.sport.project.fitapp.network.networkDTO.CalculateExercise
import com.sport.project.fitapp.network.networkDTO.NaturalExercise
import com.sport.project.fitapp.network.networkDTO.NaturalLanguageFoodQuery
import com.sport.project.fitapp.network.networkDTO.Nutrients
import com.sport.project.fitapp.repository.Repository
import io.reactivex.Single

interface NaturalLanguageRepository: Repository {

    fun calculateCaloriesExercises(queryExercise: CalculateExercise): Single<NaturalExercise>

    fun calculateCaloriesNutrition(queryNutrients: NaturalLanguageFoodQuery): Single<Nutrients>
}