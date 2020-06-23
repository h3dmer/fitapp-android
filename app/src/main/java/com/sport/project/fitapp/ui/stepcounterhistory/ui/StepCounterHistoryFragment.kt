package com.sport.project.fitapp.ui.stepcounterhistory.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sport.project.fitapp.databinding.FragmentStepCounterHistoryBinding
import com.sport.project.fitapp.di.Injectable
import com.sport.project.fitapp.di.injectViewModel
import javax.inject.Inject


class StepCounterHistoryFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory

    private lateinit var binding: FragmentStepCounterHistoryBinding
    private lateinit var historyViewModel: StepCounterHistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        historyViewModel = injectViewModel(viewModelProvider)
        binding = FragmentStepCounterHistoryBinding.inflate(inflater, container, false).apply {

            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }

        val adapter = StepCounterHistoryAdapter()
        binding.historyRecyclerView.adapter = adapter
        subscribeUi(adapter, historyViewModel)

        return binding.root
    }

    private fun subscribeUi(
        adapter: StepCounterHistoryAdapter,
        viewModel: StepCounterHistoryViewModel
    ) {
        viewModel.stepsHistory.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

}
