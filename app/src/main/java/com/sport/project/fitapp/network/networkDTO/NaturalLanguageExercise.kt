package com.sport.project.fitapp.network.networkDTO

import com.google.gson.annotations.SerializedName


data class NaturalExercise(
    val exercises: List<NaturalLanguageExercise>? = null
)

data class CalculateExercise(
    val query: String? = "",
    val gender: CalculateExerciseGender? = CalculateExerciseGender.MALE,
    val weight_kg: Int? = null,
    val height_cm: Int? = null,
    val age: Int? = null
)

enum class CalculateExerciseGender {
    MALE, FEMALE
}

data class NaturalLanguageExercise(
    val tag_id: Int? = 0,
    val user_input: String? = "",
    val duration_min: Double? = 0.0,
    val met: Double? = 0.0,
    val nf_calories: Double? = 0.0,
    val photo: NutritionixPhoto? = null,
    val compendium_code: Int? = 0,
    val name: String? = "",
    val description: String? = null,
    val benefits: String? = null
)

data class NutritionixPhoto(
    @SerializedName("highres") val highRes: String? = "",
    @SerializedName("thumb")val thumb: String? = "",
    @SerializedName("us_user_uploaded")val usUserUploaded: Boolean? = false
)