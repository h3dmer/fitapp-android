package com.sport.project.fitapp.ui.macro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jem.rubberpicker.RubberSeekBar
import com.sport.project.fitapp.R
import com.sport.project.fitapp.databinding.FragmentMacroBinding
import com.sport.project.fitapp.di.Injectable
import com.sport.project.fitapp.di.injectViewModel
import com.sport.project.fitapp.ui.macro.utils.showDialog
import com.sport.project.fitapp.utils.hideActionBar
import kotlinx.android.synthetic.main.fragment_macro.*
import javax.inject.Inject

class MacroFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var macroViewModel: MacroViewModel
    private lateinit var binding: FragmentMacroBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        macroViewModel = injectViewModel(viewModelFactory)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_macro, container, false)
        binding.viewModel = macroViewModel
        binding.fragment = this
        binding.lifecycleOwner = activity
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideActionBar()
        carbsSeekBar()
        proteinsSeekBar()
        fatSeekBar()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.getInt("kcal")?.apply {
            caloriesEditText?.setText(this.toString())
        }
    }

    fun navigateToCaloriesFragment() {
        findNavController().navigate(R.id.calculateCaloriesFragment)
    }

    fun calculateMacro() {
        if (caloriesEditText.text.toString().isNotEmpty()) {
            val values = macroViewModel.calculateMacro(caloriesEditText.text.toString().toInt())
            showDialog(values)
        }
    }

    fun highCarbsChoose() {
        macroViewModel.highCarbsValues()
        highCarbsButton.isHovered = true
        moderateButton.isHovered = false
        lowCarbsButton.isHovered = false
        setSeekBar()
    }

    fun moderateChoose() {
        macroViewModel.moderateValues()
        highCarbsButton.isHovered = false
        moderateButton.isHovered = true
        lowCarbsButton.isHovered = false
        setSeekBar()
    }

    fun lowCarbsChoose() {
        macroViewModel.lowCarbsValues()
        highCarbsButton.isHovered = false
        moderateButton.isHovered = false
        lowCarbsButton.isHovered = true
        setSeekBar()
    }

    private fun setSeekBar() {
        carbohydratesSeekBar.setCurrentValue(macroViewModel.carbsLiveData.value!!)
        proteinsSeekBar.setCurrentValue(macroViewModel.proteinsLiveData.value!!)
        fatSeekBar.setCurrentValue(macroViewModel.fatsLiveData.value!!)
    }

    private fun carbsSeekBar() {
        carbohydratesSeekBar.setOnRubberSeekBarChangeListener(object : RubberSeekBar.OnRubberSeekBarChangeListener {
            override fun onProgressChanged(seekBar: RubberSeekBar, value: Int, fromUser: Boolean) {
                macroViewModel.setCarbs(value)
            }
            override fun onStartTrackingTouch(seekBar: RubberSeekBar) {}
            override fun onStopTrackingTouch(seekBar: RubberSeekBar) {}
        })
    }

    private fun proteinsSeekBar() {
        proteinsSeekBar.setOnRubberSeekBarChangeListener(object : RubberSeekBar.OnRubberSeekBarChangeListener {
            override fun onProgressChanged(seekBar: RubberSeekBar, value: Int, fromUser: Boolean) {
                macroViewModel.setProteins(value)
            }
            override fun onStartTrackingTouch(seekBar: RubberSeekBar) {}
            override fun onStopTrackingTouch(seekBar: RubberSeekBar) {}
        })
    }

    private fun fatSeekBar() {
        fatSeekBar.setOnRubberSeekBarChangeListener(object : RubberSeekBar.OnRubberSeekBarChangeListener {
            override fun onProgressChanged(seekBar: RubberSeekBar, value: Int, fromUser: Boolean) {
                macroViewModel.setFats(value)
            }
            override fun onStartTrackingTouch(seekBar: RubberSeekBar) {}
            override fun onStopTrackingTouch(seekBar: RubberSeekBar) {}
        })
    }

}
