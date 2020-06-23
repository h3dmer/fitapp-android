package com.sport.project.fitapp.ui.macro.utils

import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog

fun Fragment.showDialog(values: List<Double>) {
    MaterialDialog(context!!)
        .message(text = "Zapotrzebowanie na białko: ${values[1].toInt()}g, na węglowodany: ${values[0].toInt()}g, na tłuszcze: ${values[2].toInt()}g")
        .positiveButton(text = "Ok")
        .show()
}