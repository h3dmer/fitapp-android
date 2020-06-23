package com.sport.project.fitapp.ui.workouts.ui

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sport.project.fitapp.db.entities.Workout

@BindingAdapter("app:listItems")
fun setItems(listView: RecyclerView, items: List<Workout>?) {
    items?.let {
        (listView.adapter as WorkoutsAdapter).submitList(items)
    }
}
