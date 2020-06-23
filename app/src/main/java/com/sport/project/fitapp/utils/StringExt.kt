package com.sport.project.fitapp.utils

import android.text.SpannableStringBuilder
import androidx.core.text.bold

fun stringDecor(firstPart: String, value: Double): String {
    return SpannableStringBuilder().bold { append(firstPart) }
            .append(value.toInt().toString()).toString()
}