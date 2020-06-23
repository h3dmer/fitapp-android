package com.sport.project.fitapp.ui.naturallanguageexercises.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.sport.project.fitapp.network.networkDTO.NaturalLanguageExercise


class NutritionAdapter :
    ListAdapter<NaturalLanguageExercise, NutritionViewHolder>(
        NUTRITION_COMPARATOR
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NutritionViewHolder =
        NutritionViewHolder.create(
            parent
        )

    override fun onBindViewHolder(holder: NutritionViewHolder, position: Int) =
        holder.onBind(getItem(position))

    companion object {
        private val NUTRITION_COMPARATOR =
            object : DiffUtil.ItemCallback<NaturalLanguageExercise>() {
                override fun areItemsTheSame(
                    oldItem: NaturalLanguageExercise,
                    newItem: NaturalLanguageExercise
                ): Boolean =
                    oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: NaturalLanguageExercise,
                    newItem: NaturalLanguageExercise
                ): Boolean =
                    oldItem == newItem
            }
    }
}