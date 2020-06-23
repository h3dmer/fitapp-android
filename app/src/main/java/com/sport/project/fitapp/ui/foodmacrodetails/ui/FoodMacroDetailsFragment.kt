package com.sport.project.fitapp.ui.foodmacrodetails.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.sport.project.fitapp.databinding.FragmentFoodMacroDetailsBinding
import com.sport.project.fitapp.di.Injectable
import com.sport.project.fitapp.di.injectViewModel
import com.sport.project.fitapp.ui.foodmacrodetails.data.buildChart
import com.sport.project.fitapp.utils.startEnterTransitionAfterLoadingImage
import javax.inject.Inject

class FoodMacroDetailsFragment : Fragment(), Injectable, OnChartValueSelectedListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val args: FoodMacroDetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentFoodMacroDetailsBinding
    private lateinit var macroDetailsViewModel: FoodMacroDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        macroDetailsViewModel = injectViewModel(viewModelFactory)
        setSharedElementTransitionOnEnter()
        binding = FragmentFoodMacroDetailsBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            macroDetailsViewModel.setFoodDetails(args.foodDetails)
            viewModel = macroDetailsViewModel
            lifecycleOwner = viewLifecycleOwner
            toolbar.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }

            var isToolbarShown = false

            foodDetailScrollview.setOnScrollChangeListener(
                NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
                    val shouldShowToolbar = scrollY > toolbar.height
                    if (isToolbarShown != shouldShowToolbar) {
                        isToolbarShown = shouldShowToolbar
                        appbar.isActivated = shouldShowToolbar
                        toolbarLayout.isTitleEnabled = shouldShowToolbar
                    }
                }
            )
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        binding.detailImage.apply {
            transitionName = args.foodDetails.ndb_no.toString()
            startEnterTransitionAfterLoadingImage(args.foodDetails.photo.highRes, this)
            buildChart(binding.chart, args.foodDetails)
        }
    }

    private fun setSharedElementTransitionOnEnter() {
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        postponeEnterTransition()
    }

    override fun onNothingSelected() {}

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        if (e == null) return
    }
}
