package com.example.myapplication.util

import com.example.myapplication.data.remote.model.CurrencyHistoryChild
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

/**
 * ChartHelper -
 * 1. Initialize MPLine chart and lifecycle method of chart.
 * 2. Display chart and invalidate
 */
class ChartHelper(private val chart: LineChart, rates: List<CurrencyHistoryChild>) {

    private val chartData = rates

    init {

        initLineChart()
        setDataToLineChart()
    }

    private fun initLineChart() {

//        hide grid lines
        chart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = chart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)
        //remove right y-axis
        chart.axisRight.isEnabled = false
        //remove legend
        chart.legend.isEnabled = false
        //remove description label
        chart.description.isEnabled = false
        chart.setScaleEnabled(true)
        chart.setPinchZoom(true)
        //add animation
        chart.animateX(1000, Easing.EaseInSine)
        // to draw label on xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.valueFormatter = MyAxisFormatter()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f

    }

    inner class MyAxisFormatter : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            return if (index < chartData.size) {
                chartData[index].symbol
            } else {
                ""
            }
        }
    }

    private fun setDataToLineChart() {
        //now draw bar chart with dynamic data
        val entries: ArrayList<Entry> = ArrayList()
        //you can replace this data object with  your custom object
        for (i in chartData.indices) {
            val data = chartData[i]
            entries.add(Entry(i.toFloat(), String.format("%.2f",data.rate).toFloat()))
        }
        val lineDataSet = LineDataSet(entries, "")
        val data = LineData(lineDataSet)
        chart.data = data
        chart.invalidate()
    }


}