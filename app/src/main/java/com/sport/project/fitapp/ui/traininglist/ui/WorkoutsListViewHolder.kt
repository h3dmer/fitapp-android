package com.sport.project.fitapp.ui.traininglist.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.sport.project.fitapp.databinding.ItemWorkoutsListBinding
import com.sport.project.fitapp.db.entities.Exercise

class WorkoutsListViewHolder private constructor(val binding: ItemWorkoutsListBinding) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.setNavToExerciseDetails { view ->
            binding.exercise?.let { exercise ->
                navigateToExerciseDetails(exercise, view)
            }
        }
    }

    fun bind(exercise: Exercise) {
        binding.workoutListCardView.clipToOutline = true
        binding.exercise = exercise
        binding.executePendingBindings()
    }

    private fun navigateToExerciseDetails(exercise: Exercise, view: View) {
        //view.findNavController().navigate(WorkoutsListFragmentDirections.actionWorkoutsListFragmentToWorkoutListDetailsFragment(exercise))
        val direction = WorkoutsListFragmentDirections.actionWorkoutsListFragmentToWorkoutListDetailsFragment(exercise)
        val extras = FragmentNavigatorExtras(
            binding.workoutListImage to binding.workoutListImage.transitionName
        )
        view.findNavController().navigate(direction, extras)
    }

    companion object {
        fun from(parent: ViewGroup): WorkoutsListViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemWorkoutsListBinding.inflate(
                layoutInflater,
                parent,
                false
            )

            return WorkoutsListViewHolder(binding)
        }
    }
}
