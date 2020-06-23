package com.sport.project.fitapp.ui.naturallanguageexercises.ui


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sport.project.fitapp.databinding.FragmentNaturalLanguageExercisesBinding
import com.sport.project.fitapp.di.Injectable
import com.sport.project.fitapp.di.injectViewModel
import com.sport.project.fitapp.network.networkDTO.CalculateExercise
import com.sport.project.fitapp.network.networkDTO.NaturalExercise
import com.sport.project.fitapp.ui.nutritions.ui.NutritionViewModel
import com.sport.project.fitapp.ui.nutritions.ui.state.NutritionViewState
import com.sport.project.fitapp.utils.isInstanceOf
import javax.inject.Inject


class NaturalLanguageExercisesFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var nutritionViewModel: NutritionViewModel
    private lateinit var binding: FragmentNaturalLanguageExercisesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        nutritionViewModel = injectViewModel(viewModelFactory)
        binding = FragmentNaturalLanguageExercisesBinding.inflate(inflater, container, false)

        with(binding) {
            this.viewModel = nutritionViewModel
            layoutManager = LinearLayoutManager(context)
            binding.dividerItemDecoration =
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            lifecycleOwner = this@NaturalLanguageExercisesFragment
        }

        val adapter =
            NutritionAdapter()
        binding.exercisesList.adapter = adapter

        setUpButtons(binding)
        subscribeUi(adapter)

        return binding.root
    }

    private fun setUpButtons(binding: FragmentNaturalLanguageExercisesBinding) {
        binding.calculate.setOnClickListener {
            calculateBurnedCalories(binding)
        }
    }

    private fun subscribeUi(adapter: NutritionAdapter) {
        nutritionViewModel.exerciseViewState.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is NutritionViewState.NutritionSuccess<*> -> {
                    if (isInstanceOf<NutritionViewState.NutritionSuccess<NaturalExercise>>(result)) {
                        val list = result as NutritionViewState.NutritionSuccess<NaturalExercise>
                        adapter.submitList(list.data.exercises)
                    }
                }
                is NutritionViewState.NutritionFailure -> {
                    Toast.makeText(
                        context,
                        "Problem occured during loading informations...",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.d("error -> ", result.error.message!!)
                }
            }
        })
    }

    private fun calculateBurnedCalories(binding: FragmentNaturalLanguageExercisesBinding) {
        nutritionViewModel.burnedCaloriesFromExercises(CalculateExercise(query = binding.exerciseEditText.text.toString()))
    }
}
