package com.sport.project.fitapp.ui.nutritions.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sport.project.fitapp.ui.naturallanguageexercises.ui.NaturalLanguageExercisesFragment
import com.sport.project.fitapp.ui.naturallanguagefoods.ui.NaturalLanguageFoodsFragment

const val FOOD_PAGE_INDEX = 0
const val EXERCISE_PAGE_INDEX = 1

class NutritionPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        FOOD_PAGE_INDEX to { NaturalLanguageFoodsFragment() },
        EXERCISE_PAGE_INDEX to { NaturalLanguageExercisesFragment() }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}