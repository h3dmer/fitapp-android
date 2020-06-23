package com.sport.project.fitapp.utils

import java.text.SimpleDateFormat
import java.util.*

object DateExt {

    private val standard = SimpleDateFormat("yyyy-MM-dd")
    fun standardFormat(date: Date): String {
        return standard.format(date)
    }

    fun standardParse(string: String): Date {
        return standard.parse(string)!!
    }
}