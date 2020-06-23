package com.sport.project.fitapp.ui.workouts.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sport.project.fitapp.R
import com.sport.project.fitapp.databinding.FragmentWorkoutsBinding
import com.sport.project.fitapp.di.Injectable
import com.sport.project.fitapp.ui.workouts.ui.workoutsdialog.WorkoutsDialog
import com.sport.project.fitapp.utils.event.EventObserver
import javax.inject.Inject


class WorkoutsFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory

    private val workoutsViewModel by viewModels<WorkoutsViewModel> { viewModelProvider }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentWorkoutsBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = workoutsViewModel
            toolbar.setNavigationOnClickListener { workoutsViewModel.navigateBack() }
        }

        setupAdapter(binding)
        subscribeUi()
        setupNavigation()

        return binding.root
    }

    private fun setupAdapter(binding: FragmentWorkoutsBinding): WorkoutsAdapter {
        val adapter =
            WorkoutsAdapter({ workout -> workoutsViewModel.deleteWorkout(workout) }) { workout ->
                workoutsViewModel.updateWorkout(workout)
            }
        binding.workoutsRecyclerView.adapter = adapter
        return adapter
    }

    private fun subscribeUi() {
        workoutsViewModel.updateWorkoutEvent.observe(viewLifecycleOwner, EventObserver {
            WorkoutsDialog(it).show(requireActivity().supportFragmentManager, "WorkoutsDialog")
        })

        workoutsViewModel.deleteWorkoutEvent.observe(viewLifecycleOwner, EventObserver {
            String.format(resources.getString(R.string.deleted_workout_successful, it.name))
        })
    }

    private fun setupNavigation() {
        workoutsViewModel.navigateBackEvent.observe(
            viewLifecycleOwner,
            EventObserver {
                findNavController().navigateUp()
            })

        workoutsViewModel.addNewWorkoutEvent.observe(viewLifecycleOwner, EventObserver {
            WorkoutsDialog(null).show(requireActivity().supportFragmentManager, "WorkoutsDialog")
        })
    }
}
