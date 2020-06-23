package com.sport.project.fitapp.ui.nutritions.data

import com.sport.project.fitapp.network.api.INutritionService
import com.sport.project.fitapp.network.networkDTO.CalculateExercise
import com.sport.project.fitapp.network.networkDTO.NaturalExercise
import com.sport.project.fitapp.network.networkDTO.NaturalLanguageFoodQuery
import com.sport.project.fitapp.network.networkDTO.Nutrients
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NaturalLanguageRepositoryImpl @Inject constructor(
    @JvmSuppressWildcards private val service: INutritionService
) : NaturalLanguageRepository {

    override fun calculateCaloriesExercises(queryExercise: CalculateExercise): Single<NaturalExercise> {
        return service.caloriesFromExercises(queryExercise)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun calculateCaloriesNutrition(queryNutrients: NaturalLanguageFoodQuery): Single<Nutrients> {
        return service.caloriesFromFood(queryNutrients)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}