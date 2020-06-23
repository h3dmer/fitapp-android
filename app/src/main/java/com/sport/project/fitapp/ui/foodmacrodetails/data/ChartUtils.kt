package com.sport.project.fitapp.ui.foodmacrodetails.data

import android.content.res.Resources
import android.graphics.Color
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.sport.project.fitapp.R
import com.sport.project.fitapp.network.networkDTO.NaturalLanguageFoods
import com.sport.project.fitapp.ui.foodmacrodetails.ui.FoodMacroDetailsFragment

fun FoodMacroDetailsFragment.buildChart(chart: PieChart, foodDetails: NaturalLanguageFoods) {
    chart.setUsePercentValues(true)
    chart.description.isEnabled = false
    chart.setExtraOffsets(5f, 10f, 5f, 5f)
    chart.dragDecelerationFrictionCoef = 0.95f
    chart.centerText = "Calories: \n ${foodDetails.nf_calories}"

    chart.isDrawHoleEnabled = true
    chart.setHoleColor(Color.WHITE)

    chart.setTransparentCircleColor(Color.WHITE)
    chart.setTransparentCircleAlpha(110)

    chart.holeRadius = 51f
    chart.transparentCircleRadius = 60f

    chart.setDrawCenterText(true);

    chart.rotationAngle = 0f
    chart.isRotationEnabled = true
    chart.isHighlightPerTapEnabled = true


    chart.setOnChartValueSelectedListener(this)

    chart.animateY(2000, Easing.EaseInOutQuad)

    val l = chart.legend
    l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
    l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
    l.orientation = Legend.LegendOrientation.VERTICAL
    l.setDrawInside(false)
    l.xEntrySpace = 10f
    l.yEntrySpace = 0f
    l.yOffset = 0f

    setData(chart, foodDetails, resources)

    chart.setEntryLabelColor(Color.WHITE)
    chart.setEntryLabelTextSize(12f)
}

fun setData(chart: PieChart, foodDetails: NaturalLanguageFoods, resources: Resources) {
    val entries: ArrayList<PieEntry> = ArrayList()

    entries.add(PieEntry(foodDetails.nf_total_carbohydrate.toFloat(), "Carbs"))
    entries.add(PieEntry(foodDetails.nf_protein.toFloat(), "Proteint"))
    entries.add(PieEntry(foodDetails.nf_total_fat.toFloat(), "Fat"))

    val dataSet = PieDataSet(entries, "Election Results")
    dataSet.setDrawIcons(false)
    dataSet.sliceSpace = 3f
    dataSet.iconsOffset = MPPointF(0f, 40f)
    dataSet.selectionShift = 5f

    val colors: ArrayList<Int> = ArrayList()
    for (c in ColorTemplate.COLORFUL_COLORS) colors.add(c)
    colors.add(ColorTemplate.getHoloBlue())
    dataSet.colors = colors
    dataSet.selectionShift = 5f
    val data = PieData(dataSet)
    data.setValueFormatter(PercentFormatter(chart))
    data.setValueTextSize(11f)
    data.setValueTextColor(resources.getColor(R.color.white, null))
    chart.data = data

    chart.highlightValues(null)
    chart.invalidate()
}