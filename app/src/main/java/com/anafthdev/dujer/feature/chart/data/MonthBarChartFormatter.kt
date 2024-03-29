package com.anafthdev.dujer.feature.chart.data

import com.anafthdev.dujer.foundation.common.AppUtil
import com.anafthdev.dujer.foundation.extension.percentOf
import com.anafthdev.dujer.foundation.uicomponent.charting.bar.model.BarData
import com.anafthdev.dujer.foundation.uicomponent.charting.formatter.ChartFormatter
import java.math.RoundingMode
import java.text.DecimalFormat

class MonthBarChartFormatter: ChartFormatter<BarData>() {
	
	override fun formatX(x: Float, data: List<BarData>): String {
		return AppUtil.shortMonths[x.toInt()]
	}
	
	override fun formatY(y: Float, data: List<BarData>): String {
		val percent = data.map { it.y }
			.sum()
			.percentOf(
				value = y,
				ifNaN = { 0f }
			)
		
		return "${
			DecimalFormat("#").apply {
				roundingMode = if ((percent - percent.toInt()) >= 0.5f) RoundingMode.CEILING
				else RoundingMode.FLOOR
			}.format(percent)
		}%"
	}
}