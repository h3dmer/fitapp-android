package com.sport.project.fitapp.ui.workouts.ui.workoutsdialog

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.sport.project.fitapp.db.entities.WorkoutExercise

class WorkoutExercisesAdapter(
    private val updateExercise: (WorkoutExercise) -> Unit,
    private val deleteExercise: (WorkoutExercise) -> Unit
) : ListAdapter<WorkoutExercise, WorkoutExercisesViewHolder>(WorkoutExerciseDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutExercisesViewHolder =
        WorkoutExercisesViewHolder.from(parent)

    override fun onBindViewHolder(holder: WorkoutExercisesViewHolder, position: Int) =
        holder.bind(getItem(position), updateExercise, deleteExercise)

}

class WorkoutExerciseDiffCallback : DiffUtil.ItemCallback<WorkoutExercise>() {
    override fun areItemsTheSame(oldItem: WorkoutExercise, newItem: WorkoutExercise): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: WorkoutExercise, newItem: WorkoutExercise): Boolean {
        return oldItem == newItem
    }
}