package com.sport.project.fitapp.ui.calculatecalories


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sport.project.fitapp.R
import com.sport.project.fitapp.databinding.FragmentCalculateCaloriesBinding
import com.sport.project.fitapp.di.Injectable
import com.sport.project.fitapp.ui.calculatecalories.utils.activityDialog
import com.sport.project.fitapp.ui.calculatecalories.utils.caloriesDialog
import kotlinx.android.synthetic.main.fragment_calculate_calories.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class CalculateCaloriesFragment : Fragment(), Injectable {

    private var gender = 0
    private var metricHeight = 1.0
    private var metricWeight = 1.0
    private lateinit var caloriesViewModel: CaloriesViewModel
    private lateinit var binding: FragmentCalculateCaloriesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calculate_calories, container, false)
        caloriesViewModel = ViewModelProvider(this).get(CaloriesViewModel::class.java)
        binding.viewModel = caloriesViewModel
        binding.fragment = this
        binding.lifecycleOwner = activity
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        unitsRadioGroup.setOnCheckedChangeListener { _, _ ->
            setUpProperUnits()
        }
        genderRadioGroup.setOnCheckedChangeListener { _, _ ->
            setUpGender()
        }
        activityEditText.onClick {
            activityDialog(caloriesViewModel)
        }
    }

    fun calculateCalories() {
        if (ageEditText.text!!.isNotEmpty() && weightEditText.text!!.isNotEmpty() && heightEditText.text!!.isNotEmpty()
            && activityEditText.text!!.isNotEmpty()) {
            val kcal = caloriesViewModel.calculateDailyCalories((heightEditText.text.toString().toDouble() / metricHeight).toInt() ,
                (weightEditText.text.toString().toInt() / metricWeight).toInt() , ageEditText.text.toString().toInt(), gender,
                caloriesViewModel.dailyActivityLiveData.value!!)
            caloriesDialog(kcal)
        }
    }

    private fun setUpProperUnits() {
        if (metricRadioButton.isChecked) {
            heightTextInputLayout.suffixText = "cm"
            weightTextInputLayout.suffixText = "kg"
            metricWeight = 1.0
            metricHeight = 1.0
        } else {
            heightTextInputLayout.suffixText = "inch"
            weightTextInputLayout.suffixText = "lbs"
            metricWeight = 2.2
            metricHeight = 0.393
        }
    }

    private fun setUpGender() {
        gender = if (maleRadioButton.isChecked) 0 else 1
    }

    companion object {
        val activityList = listOf(
            "0 dni",
            "1-3 dni",
            "3-5 dni",
            "codziennie"
        )

        val activityValues = listOf(
            1.2,
            1.3,
            1.6,
            1.8
        )
    }
}
