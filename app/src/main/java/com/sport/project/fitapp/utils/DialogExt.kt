package com.sport.project.fitapp.utils

import android.content.Context
import android.text.InputType
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.sport.project.fitapp.R


fun addGoalDialog(context: Context?, positive: (goal: Int) -> Unit) {
    MaterialDialog(context!!).show {
        title(res = R.string.step_counter_set_goal)
        input(hintRes = R.string.type_goal, inputType = InputType.TYPE_CLASS_NUMBER) { _, text ->
            positive.invoke(text.toString().toInt())
        }
        positiveButton(R.string.add)
    }.show()
}