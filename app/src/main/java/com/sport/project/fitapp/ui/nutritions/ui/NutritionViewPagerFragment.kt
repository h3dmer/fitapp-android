package com.sport.project.fitapp.ui.nutritions.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.sport.project.fitapp.databinding.FragmentNutritionsViewPagerBinding
import com.sport.project.fitapp.di.Injectable
import com.sport.project.fitapp.di.injectViewModel
import javax.inject.Inject


class NutritionViewPagerFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: NutritionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = injectViewModel(viewModelFactory)
        val binding = FragmentNutritionsViewPagerBinding.inflate(inflater, container, false)
        val tabLayout = binding.tabs
        val viewPager = binding.viewPager

        viewPager.adapter =
            NutritionPagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()

        return binding.root
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            FOOD_PAGE_INDEX -> "FROM FOOD"
            EXERCISE_PAGE_INDEX -> "FROM EXERCISES"
            else -> null
        }
    }
}
