package com.sport.project.fitapp.ui.naturallanguagefoods.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sport.project.fitapp.R
import com.sport.project.fitapp.databinding.FragmentNaturalLanguageFoodsBinding
import com.sport.project.fitapp.di.Injectable
import com.sport.project.fitapp.di.injectViewModel
import com.sport.project.fitapp.network.networkDTO.NaturalLanguageFoodQuery
import com.sport.project.fitapp.network.networkDTO.Nutrients
import com.sport.project.fitapp.ui.nutritions.ui.NutritionViewModel
import com.sport.project.fitapp.ui.nutritions.ui.state.NutritionViewState
import com.sport.project.fitapp.utils.DividerItemDecorator
import javax.inject.Inject

class NaturalLanguageFoodsFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var nutritionViewModel: NutritionViewModel
    private lateinit var binding: FragmentNaturalLanguageFoodsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        nutritionViewModel = injectViewModel(viewModelFactory)
        binding = FragmentNaturalLanguageFoodsBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@NaturalLanguageFoodsFragment
            viewModel = nutritionViewModel
            foodList.addItemDecoration(
                DividerItemDecorator(
                    resources.getDrawable(
                        R.drawable.divider,
                        null
                    )
                )
            )
        }

        val adapter = FoodNaturalLanguageAdapter()
        binding.foodList.adapter = adapter

        setUpButtons(binding)
        subscribeUi(adapter)

        return binding.root
    }

    private fun setUpButtons(binding: FragmentNaturalLanguageFoodsBinding) {
        binding.calculateFoods.setOnClickListener {
            calculateBurnedCalories(binding)
        }
    }

    private fun subscribeUi(adapter: FoodNaturalLanguageAdapter) {
        nutritionViewModel.foodsViewState.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is NutritionViewState.NutritionSuccess<*> -> {
                    if (result.isInstanceOf<NutritionViewState.NutritionSuccess<Nutrients>>(result)) {
                        val list = result as NutritionViewState.NutritionSuccess<Nutrients>
                        adapter.submitList(result.data.foods)
                        binding.caloriesResult = list.data.foods.sumByDouble { it.nf_calories }
                    }
                }
                is NutritionViewState.NutritionFailure -> {
                    Toast.makeText(
                        context,
                        "Problem occurred during fetching information: ${result.error.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    private fun calculateBurnedCalories(binding: FragmentNaturalLanguageFoodsBinding) {
        nutritionViewModel.burnedCaloriesFromFood(NaturalLanguageFoodQuery(query = binding.foodEditText.text.toString()))
    }
}
