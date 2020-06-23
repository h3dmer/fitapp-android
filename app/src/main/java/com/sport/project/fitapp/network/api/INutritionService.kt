package com.sport.project.fitapp.network.api

import com.sport.project.fitapp.network.networkDTO.CalculateExercise
import com.sport.project.fitapp.network.networkDTO.NaturalExercise
import com.sport.project.fitapp.network.networkDTO.NaturalLanguageFoodQuery
import com.sport.project.fitapp.network.networkDTO.Nutrients
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface INutritionService {

    @POST("natural/nutrients")
    fun caloriesFromFood(@Body query: NaturalLanguageFoodQuery): Single<Nutrients>

    @POST("natural/exercise")
    fun caloriesFromExercises(@Body calculateExercise: CalculateExercise): Single<NaturalExercise>

}