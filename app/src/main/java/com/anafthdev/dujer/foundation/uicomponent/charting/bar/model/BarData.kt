package com.anafthdev.dujer.foundation.uicomponent.charting.bar.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class BarData(
	@SerializedName("x") var x: Float,
	@SerializedName("y") var y: Float
) {
	
	fun toJsonString(): String {
		return Gson().toJson(this)
	}
	
	companion object {
		val default = BarData(-1f, -1f)
		
		val sample1 = listOf(
			BarData(0f, 1f),
			BarData(1f, 3f),
			BarData(2f, 5f),
			BarData(3f, 2f),
			BarData(4f, 14f),
			BarData(5f, 0f),
			BarData(6f, 10f),
			BarData(7f, 6f),
			BarData(8f, 2f),
			BarData(9f, 9f),
			BarData(10f, 4f),
			BarData(11f, 6f),
		)
		
		val sample2 = listOf(
			BarData(0f, 9f),
			BarData(1f, 7f),
			BarData(2f, 8f),
			BarData(3f, 2f),
			BarData(4f, 12f),
			BarData(5f, 7f),
			BarData(6f, 15f),
			BarData(7f, 2f),
			BarData(8f, 6f),
			BarData(9f, 9f),
			BarData(10f, 7f),
			BarData(11f, 2f),
		)
	}
}
