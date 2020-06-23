package com.sport.project.fitapp.ui.naturallanguagefoods.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.sport.project.fitapp.network.networkDTO.NaturalLanguageFoods

class FoodNaturalLanguageAdapter :
    ListAdapter<NaturalLanguageFoods, FoodNaturalLanguageViewHolder>(
        FOOD_COMPARATOR
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FoodNaturalLanguageViewHolder =
        FoodNaturalLanguageViewHolder.create(
            parent
        )

    override fun onBindViewHolder(holder: FoodNaturalLanguageViewHolder, position: Int) =
        holder.bind(getItem(position))

    companion object {
        private val FOOD_COMPARATOR =
            object : DiffUtil.ItemCallback<NaturalLanguageFoods>() {
                override fun areItemsTheSame(
                    oldItem: NaturalLanguageFoods,
                    newItem: NaturalLanguageFoods
                ): Boolean =
                    oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: NaturalLanguageFoods,
                    newItem: NaturalLanguageFoods
                ): Boolean =
                    oldItem == newItem
            }
    }
}