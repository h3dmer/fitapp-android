package com.sport.project.fitapp.ui.naturallanguageexercises.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sport.project.fitapp.R
import com.sport.project.fitapp.databinding.ItemNaturalLanguageExerciseBinding
import com.sport.project.fitapp.network.networkDTO.NaturalLanguageExercise

class NutritionViewHolder(private val binding: ItemNaturalLanguageExerciseBinding): RecyclerView.ViewHolder(binding.root) {

    fun onBind(exercise: NaturalLanguageExercise) {
        binding.exercise = exercise
        binding.executePendingBindings()
    }

    companion object {
        fun create(parent: ViewGroup): NutritionViewHolder {
            return NutritionViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_natural_language_exercise, parent, false
                )
            )
        }
    }
}