package com.anafthdev.dujer.ui.statistic.component

import android.graphics.Color
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import com.anafthdev.dujer.R
import com.anafthdev.dujer.data.FinancialType
import com.anafthdev.dujer.data.db.model.Category
import com.anafthdev.dujer.foundation.extension.isDarkTheme
import com.anafthdev.dujer.foundation.extension.isDefault
import com.anafthdev.dujer.foundation.uimode.data.LocalUiMode
import com.anafthdev.dujer.foundation.window.dpScaled
import com.anafthdev.dujer.ui.statistic.data.CustomPieChartRenderer
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinancialStatisticChart(
	dataSet: PieDataSet,
	financialType: FinancialType,
	category: Category,
	selectedColor: androidx.compose.ui.graphics.Color,
	modifier: Modifier = Modifier,
	onPieDataSelected: (PieEntry?, Highlight?) -> Unit,
	onNothingSelected: () -> Unit
) {
	
	val uiMode = LocalUiMode.current
	
	val pieLabelColor = if (uiMode.isDarkTheme()) Color.WHITE else Color.BLACK
	val background = MaterialTheme.colorScheme.background
	
	var selectedCategory by remember { mutableStateOf(Category.default) }
	var showSelectedCategory by remember { mutableStateOf(false) }
	
	LaunchedEffect(category) {
		selectedCategory = category
		showSelectedCategory = !category.isDefault()
	}
	
	Card(
		shape = MaterialTheme.shapes.large,
		elevation = CardDefaults.cardElevation(
			defaultElevation = 1.dpScaled
		),
		modifier = Modifier
			.padding(
				16.dpScaled
			)
			.fillMaxWidth()
			.padding(4.dpScaled)
	) {
		AndroidView(
			factory = { context ->
				PieChart(context).apply {
					holeRadius = 60f
					rotationAngle = 0f
					isDrawHoleEnabled = true
					isRotationEnabled = true
					legend.isEnabled = false
					description.isEnabled = false
					isHighlightPerTapEnabled = true
					renderer = CustomPieChartRenderer(this, 10f)
					
					setExtraOffsets(0f, 16f, 0f, 16f)
					setEntryLabelColor(pieLabelColor)
					setDrawEntryLabels(false)
					setUsePercentValues(true)
					setHoleColor(background.toArgb())
					setCenterTextTypeface(
						ResourcesCompat.getFont(
							context,
							R.font.inter_regular
						)
					)
				}
			},
			update = { pieChart ->
				pieChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
					override fun onValueSelected(e: Entry?, h: Highlight?) {
						Timber.i("selekted entri: $e, $h")
						onPieDataSelected((e as PieEntry), h)
					}
					
					override fun onNothingSelected() {
						onNothingSelected()
					}
				})
				
				pieChart.centerText = ""
				
				pieChart.data = PieData(dataSet)
				pieChart.invalidate()
			},
			modifier = modifier
		)
		
		AnimatedVisibility(
			visible = showSelectedCategory,
			enter = fadeIn(
				animationSpec = tween(200)
			) + expandVertically(
				animationSpec = tween(600)
			),
			exit = fadeOut(
				animationSpec = tween(200)
			) + shrinkVertically(
				animationSpec = tween(600)
			),
			modifier = Modifier
				.padding(16.dpScaled)
		) {
			StatisticCategoryCard(
				color = selectedColor,
				category = selectedCategory
			)
		}
	}
}
