package com.sport.project.fitapp.ui.foodmacrodetails.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sport.project.fitapp.network.networkDTO.NaturalLanguageFoods
import javax.inject.Inject

class FoodMacroDetailsViewModel @Inject constructor(): ViewModel() {

    private val _foods = MutableLiveData<NaturalLanguageFoods>()
    val food: LiveData<NaturalLanguageFoods> get() = _foods

    fun setFoodDetails(list: NaturalLanguageFoods) {
        _foods.value = list
    }

    fun getValueFromList(number: Int): String {
        val macroValue = _foods.value?.full_nutrients?.last {
            it.attr_id == number
        }
        return StringBuilder("${macroValue?.value.toString()} ${macroValue?.unit}").toString()
    }
}