package com.anafthdev.dujer.uicomponent.charting.bar.data

import com.anafthdev.dujer.uicomponent.charting.bar.model.BarData
import kotlin.random.Random

class BarDataSet(
	val barData: List<BarData>,
	val id: Int = Random.nextInt(),
) {

}
