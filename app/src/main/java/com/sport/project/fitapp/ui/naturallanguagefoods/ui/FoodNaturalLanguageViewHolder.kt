package com.sport.project.fitapp.ui.naturallanguagefoods.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.sport.project.fitapp.databinding.ItemNaturalLanguageFoodBinding
import com.sport.project.fitapp.network.networkDTO.NaturalLanguageFoods
import com.sport.project.fitapp.ui.nutritions.ui.NutritionViewPagerFragmentDirections


class FoodNaturalLanguageViewHolder(val binding: ItemNaturalLanguageFoodBinding) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.setClickListener { view ->
            binding.food?.let { food ->
                navigateToFoodDetails(food, view)
            }
        }
    }

    fun bind(data: NaturalLanguageFoods) {
        binding.food = data
        binding.executePendingBindings()
    }

    private fun navigateToFoodDetails(food: NaturalLanguageFoods, view: View) {
        val direction = NutritionViewPagerFragmentDirections.actionNutritionViewPagerFragmentToFoodMacroDetailsFragment(food)
        val extras = FragmentNavigatorExtras(
            binding.foodImageView to binding.foodImageView.transitionName,
            binding.foodName to binding.foodName.transitionName
        )
        view.findNavController().navigate(direction, extras)
    }

    companion object {
        fun create(parent: ViewGroup): FoodNaturalLanguageViewHolder {
            return FoodNaturalLanguageViewHolder(inflate(parent))
        }

        fun inflate(parent: ViewGroup): ItemNaturalLanguageFoodBinding {
            return ItemNaturalLanguageFoodBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        }
    }
}