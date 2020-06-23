package com.sport.project.fitapp.ui.workouts.ui.workoutsdialog

import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.BindingAdapter
import com.sport.project.fitapp.R

@BindingAdapter("setWorkoutsDialogAdapter")
fun setSetsRepAdapter(view: AutoCompleteTextView, elements: Int) {
    view.setAdapter(
        ArrayAdapter(
            view.context,
            R.layout.list_item,
            view.resources.getStringArray(R.array.workout_set_reps).take(elements)
        )
    )
}