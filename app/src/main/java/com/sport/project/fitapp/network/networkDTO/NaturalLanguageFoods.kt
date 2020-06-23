package com.sport.project.fitapp.network.networkDTO

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.util.*

@Parcelize
data class NaturalLanguageFoods(
    val alt_measures: @RawValue List<Alt_measures>,
    val food_name: String,
    val brand_name: String,
    val serving_qty: Double,
    val serving_unit: String,
    val serving_weight_grams: Double,
    val nf_calories: Double,
    val nf_total_fat: Double,
    val nf_saturated_fat: Double,
    val nf_cholesterol: Double,
    val nf_sodium: Double,
    val nf_total_carbohydrate: Double,
    val nf_dietary_fiber: Double,
    val nf_sugars: Double,
    val nf_protein: Double,
    val nf_potassium: Double,
    val nf_p: Double,
    val full_nutrients: @RawValue List<Full_nutrients>,
    val nix_brand_name: String,
    val nix_brand_id: String,
    val nix_item_name: String,
    val nix_item_id: String,
    val upc: String,
    val consumed_at: String,
    val source: Int,
    val ndb_no: Int,
    val tags: @RawValue Tags,
    val lat: String,
    val lng: String,
    val meal_type: Int,
    val photo: @RawValue Photo,
    val sub_recipe: String
): Parcelable

data class Nutrients(

    val foods: List<NaturalLanguageFoods>
)

data class NaturalLanguageFoodQuery(
    @SerializedName("query") val query: String = "",
    @SerializedName("line_delimited") val lineDelimited: Boolean = false,
    @SerializedName("timezone") val timeZone: String = TimeZone.getDefault().toString(),
    @SerializedName("use_branded_foods") val use_branded_foods: Boolean = false,
    @SerializedName("use_raw_foods") val useRawFoods: Boolean = false,
    @SerializedName("claims") val claims: Boolean = true,
    @SerializedName("include_subrecipe") val includeSubrecipe: Boolean = true
)