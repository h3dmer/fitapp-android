package com.sport.project.fitapp.ui.traininglist.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.sport.project.fitapp.R

@BindingAdapter("workoutsImage")
fun workoutsImage(view: ImageView, id: Int) {
    when (id) {
        0 -> view.setImageDrawable(view.resources.getDrawable(R.drawable.abs, null))
        1 -> view.setImageDrawable(view.resources.getDrawable(R.drawable.chest, null))
        2 -> view.setImageDrawable(view.resources.getDrawable(R.drawable.shoulder, null))
        3 -> view.setImageDrawable(view.resources.getDrawable(R.drawable.biceps, null))
        4 -> view.setImageDrawable(view.resources.getDrawable(R.drawable.triceps, null))
        5 -> view.setImageDrawable(view.resources.getDrawable(R.drawable.lges, null))
        6 -> view.setImageDrawable(view.resources.getDrawable(R.drawable.calf, null))
        7 -> view.setImageDrawable(view.resources.getDrawable(R.drawable.forearm, null))
        8 -> view.setImageDrawable(view.resources.getDrawable(R.drawable.back, null))
        else -> view.setImageDrawable(view.resources.getDrawable(R.drawable.fit_logo, null))
    }
}