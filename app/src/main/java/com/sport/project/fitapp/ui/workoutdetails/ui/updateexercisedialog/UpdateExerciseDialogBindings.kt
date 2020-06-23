package com.sport.project.fitapp.ui.workoutdetails.ui.updateexercisedialog

import androidx.databinding.BindingAdapter
import com.google.android.material.textview.MaterialTextView
import com.sport.project.fitapp.R

@BindingAdapter("app:exerciseTitle")
fun setExerciseTitle(view: MaterialTextView, viewModel: UpdateExerciseDialogViewModel) {
    val exercise = viewModel.updatingExercise.value
    view.text = String.format(view.resources.getString(R.string.editing_workout), exercise?.name, exercise?.sets, exercise?.reps)
}