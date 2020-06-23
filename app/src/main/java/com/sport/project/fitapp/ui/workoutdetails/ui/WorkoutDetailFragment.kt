package com.sport.project.fitapp.ui.workoutdetails.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.afollestad.materialdialogs.MaterialDialog
import com.sport.project.fitapp.R
import com.sport.project.fitapp.databinding.FragmentWorkoutDetailBinding
import com.sport.project.fitapp.db.entities.WorkoutExercise
import com.sport.project.fitapp.di.Injectable
import com.sport.project.fitapp.ui.workoutdetails.ui.updateexercisedialog.UpdateExerciseDialog
import com.sport.project.fitapp.ui.workouts.ui.workoutsdialog.WorkoutExercisesAdapter
import com.sport.project.fitapp.utils.event.EventObserver
import org.jetbrains.anko.design.snackbar
import javax.inject.Inject

class WorkoutDetailFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val argument: WorkoutDetailFragmentArgs by navArgs()
    private val workoutDetailViewModel by viewModels<WorkoutDetailViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentWorkoutDetailBinding.inflate(inflater, container, false).apply {
            viewModel = workoutDetailViewModel
            lifecycleOwner = viewLifecycleOwner
            workoutDetailsToolbar.setNavigationOnClickListener { workoutDetailViewModel.navigateBack() }
        }

        workoutDetailViewModel.getWorkout(argument.workoutId)

        setupNavigation()
        setupSnackbar()
        setupAdapter(binding)

        return binding.root
    }

    private fun setupAdapter(binding: FragmentWorkoutDetailBinding) {
        val adapter = WorkoutExercisesAdapter({
            UpdateExerciseDialog(workoutDetailViewModel.workout.value, it).show(requireActivity().supportFragmentManager, "UpdateExerciseDialog")
        }) { deleteDialog(it) }
        binding.workoutsDetailsRecyclerView.adapter = adapter
    }

    private fun setupNavigation() {
        workoutDetailViewModel.navigateBack.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigateUp()
        })

        workoutDetailViewModel.updateExerciseEvent.observe(viewLifecycleOwner, EventObserver {
        })
    }

    private fun setupSnackbar() {
        workoutDetailViewModel.errorText.observe(viewLifecycleOwner, EventObserver {
            view?.snackbar(getString(it))
        })
    }

    private fun deleteDialog(exercise: WorkoutExercise) {
        context?.let { context ->
            MaterialDialog(context)
                .title(R.string.delete_workout_dialog_title)
                .message(R.string.delete_workout_dialog_description)
                .positiveButton { workoutDetailViewModel.deleteExerciseFromWorkout(exercise) }
                .negativeButton { }
                .show()
        }
    }
}
