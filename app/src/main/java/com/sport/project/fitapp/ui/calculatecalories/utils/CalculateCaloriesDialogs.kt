package com.sport.project.fitapp.ui.calculatecalories.utils

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.sport.project.fitapp.R
import com.sport.project.fitapp.ui.calculatecalories.CalculateCaloriesFragment
import com.sport.project.fitapp.ui.calculatecalories.CaloriesViewModel

fun Fragment.activityDialog(caloriesViewModel: CaloriesViewModel) {
        MaterialDialog(context!!).show {
            listItemsSingleChoice(items = CalculateCaloriesFragment.activityList) { _, index, text ->
                caloriesViewModel.setActivityText(text.toString())
                caloriesViewModel.setDailyActivity(CalculateCaloriesFragment.activityValues[index])
            }
            positiveButton(text = "Wybierz")
        }
}

fun Fragment.caloriesDialog(kcal: Int) {
    MaterialDialog(context!!)
        .title(text = "Kalorie")
        .message(text = "Twoje dzienne zapotrzebowanie to $kcal")
        .positiveButton {
            findNavController().navigate(R.id.macroFragment, bundleOf("kcal" to kcal))
        }.show()
}