package com.sport.project.fitapp.ui.traininglistdetails.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sport.project.fitapp.databinding.ItemExerciseNameBinding

class WorkoutsListDetailsViewHolder private constructor(val binding: ItemExerciseNameBinding) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.setNavigateToVideos { view ->
            binding.exerciseName?.let { exercise ->
                navigateToExerciseDetails(exercise, view)
            }
        }
    }

    fun bind(exerciseName: String) {
        binding.exerciseName = exerciseName
        binding.executePendingBindings()
    }

    private fun navigateToExerciseDetails(exercise: String, view: View) {
        val direction = WorkoutListDetailsFragmentDirections.actionWorkoutListDetailsFragmentToTrainingVideosFragment(exercise)
        view.findNavController().navigate(direction)
    }

    companion object {
        fun from(parent: ViewGroup): WorkoutsListDetailsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemExerciseNameBinding.inflate(
                layoutInflater,
                parent,
                false
            )

            return WorkoutsListDetailsViewHolder(binding)
        }
    }
}