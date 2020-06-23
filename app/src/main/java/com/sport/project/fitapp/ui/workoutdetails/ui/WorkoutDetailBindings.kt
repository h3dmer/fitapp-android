package com.sport.project.fitapp.ui.workoutdetails.ui

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sport.project.fitapp.db.entities.WorkoutExercise
import com.sport.project.fitapp.ui.workouts.ui.workoutsdialog.WorkoutExercisesAdapter

@BindingAdapter("app:listItems")
fun setItems(listView: RecyclerView, items: List<WorkoutExercise>?) {
    items?.let {
        (listView.adapter as WorkoutExercisesAdapter).submitList(items)
    }
}
