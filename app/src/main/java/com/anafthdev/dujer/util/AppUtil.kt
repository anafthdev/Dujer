package com.anafthdev.dujer.util

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.anafthdev.dujer.foundation.extension.deviceLocale
import java.text.DateFormatSymbols

object AppUtil {
	
	const val ONE_YEAR_IN_MILLIS = 31557600000
	const val ONE_MONTH_IN_MILLIS = 2629800000
	
	val shortMonths: Array<String> = DateFormatSymbols(deviceLocale).shortMonths
	
	val longMonths: Array<String> = DateFormatSymbols(deviceLocale).months
	
	fun Any?.toast(context: Context, length: Int = Toast.LENGTH_SHORT) {
		Handler(Looper.getMainLooper()).post {
			Toast.makeText(context, this.toString(), length).show()
		}
	}
	
}