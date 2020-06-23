package com.sport.project.fitapp.ui.macro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MacroViewModel @Inject constructor(): ViewModel() {

    private val carbs: MutableLiveData<Int> = MutableLiveData()
    private val proteins: MutableLiveData<Int> = MutableLiveData()
    private val fats: MutableLiveData<Int> = MutableLiveData()

    val carbsLiveData: LiveData<Int> get() = carbs
    val proteinsLiveData: LiveData<Int> get() =  proteins
    val fatsLiveData: LiveData<Int> get() =  fats

    init {
        carbs.value = 0
        proteins.value = 0
        fats.value = 0
    }

    fun setCarbs(value: Int) {
        carbs.value = value
    }

    fun setProteins(value: Int) {
        proteins.value = value
    }

    fun setFats(value: Int) {
        fats.value = value
    }

    fun highCarbsValues() {
        carbs.value = 60
        proteins.value = 15
        fats.value = 25
    }

    fun moderateValues() {
        carbs.value = 50
        proteins.value = 20
        fats.value = 30
    }

    fun lowCarbsValues() {
        carbs.value = 20
        proteins.value = 40
        fats.value = 40
    }

    fun calculateMacro(kcal: Int): List<Double> {
        val carbs = (kcal * (carbsLiveData.value!!.toDouble() / 100)) / 4
        val proteins = (kcal * (proteinsLiveData.value!!.toDouble() / 100)) / 4
        val fats = (kcal * (fatsLiveData.value!!.toDouble() / 100)) / 4
        return listOf(carbs, proteins, fats)
    }
}