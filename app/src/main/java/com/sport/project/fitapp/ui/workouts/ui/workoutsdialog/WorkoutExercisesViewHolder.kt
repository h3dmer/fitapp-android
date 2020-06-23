package com.sport.project.fitapp.ui.workouts.ui.workoutsdialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sport.project.fitapp.databinding.ItemWorkoutExerciseBinding
import com.sport.project.fitapp.db.entities.WorkoutExercise

class WorkoutExercisesViewHolder private constructor(val binding: ItemWorkoutExerciseBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        workout: WorkoutExercise,
        updateExercise: (WorkoutExercise) -> Unit,
        deleteExercise: (WorkoutExercise) -> Unit
    ) {
        binding.workout = workout
        binding.setDeleteExercise { deleteExercise.invoke(workout) }
        binding.setUpdateExercise { updateExercise.invoke(workout) }
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): WorkoutExercisesViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemWorkoutExerciseBinding.inflate(
                layoutInflater,
                parent,
                false
            )

            return WorkoutExercisesViewHolder(binding)
        }
    }
}