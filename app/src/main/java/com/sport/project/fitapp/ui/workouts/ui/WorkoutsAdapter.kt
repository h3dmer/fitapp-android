package com.sport.project.fitapp.ui.workouts.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.sport.project.fitapp.db.entities.Workout

class WorkoutsAdapter(private val deleteWorkout: (Workout) -> Unit, private val updateWorkout: (Workout) -> Unit): ListAdapter<Workout, WorkoutsViewHolder>(WorkoutsDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutsViewHolder =
        WorkoutsViewHolder.from(parent)

    override fun onBindViewHolder(holder: WorkoutsViewHolder, position: Int) =
        holder.bind(getItem(position), deleteWorkout, updateWorkout)

}

class WorkoutsDiffCallback : DiffUtil.ItemCallback<Workout>() {
    override fun areItemsTheSame(oldItem: Workout, newItem: Workout): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Workout, newItem: Workout): Boolean {
        return oldItem == newItem
    }
}