package com.anafthdev.dujer.uicomponent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.anafthdev.dujer.R
import com.anafthdev.dujer.data.FinancialType
import com.anafthdev.dujer.foundation.common.DelayManager
import com.anafthdev.dujer.foundation.common.EventCountdownTimer
import com.anafthdev.dujer.foundation.common.LocalEventCountdownTimer
import com.anafthdev.dujer.foundation.window.dpScaled
import com.anafthdev.dujer.foundation.window.spScaled
import com.anafthdev.dujer.ui.theme.expense_card_background
import com.anafthdev.dujer.ui.theme.income_card_background
import com.anafthdev.dujer.util.AppUtil.toast
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinancialTypeSelector(
	selectedFinancialType: FinancialType,
	modifier: Modifier = Modifier,
	onDoubleClick: () -> Unit = {},
	onFinancialTypeChanged: (FinancialType) -> Unit
) {
	
	val context = LocalContext.current
	
	val eventCountdownTimer by remember {
		mutableStateOf(
			DelayManager(),
			referentialEqualityPolicy()
		)
	}
	
	val isRunning by eventCountdownTimer.isRunning.collectAsState()
	
	var clickCount by remember { mutableStateOf(0) }
	var selectAll by remember{ mutableStateOf(false) }
	
	LaunchedEffect(isRunning) {
		if (!isRunning) {
			clickCount = 0
		}
	}
	
	LaunchedEffect(clickCount) {
		Timber.i("millis: $clickCount")
		if (clickCount >= 1) {
			selectAll = true
		}
	}
	
	LaunchedEffect(selectAll) {
		if (selectAll) onDoubleClick()
	}
	
	Row(
		modifier = modifier
	) {
		FilterChip(
			selected = (selectedFinancialType == FinancialType.INCOME) or selectAll,
			colors = FilterChipDefaults.filterChipColors(
				selectedContainerColor = income_card_background
			),
			label = {
				Row(
					verticalAlignment = Alignment.CenterVertically,
					horizontalArrangement = Arrangement.Center,
					modifier = Modifier
						.fillMaxSize()
				) {
					Text(
						text = stringResource(id = R.string.income),
						style = MaterialTheme.typography.bodyMedium.copy(
							textAlign = TextAlign.Center,
							fontSize = MaterialTheme.typography.bodyMedium.fontSize.spScaled
						)
					)
				}
			},
			onClick = {
				if (!isRunning) {
					if (!selectAll) {
						eventCountdownTimer.delay(
							timeInMillis = 800,
							onFinish = {
								clickCount = 0
							}
						)
					}
					
					selectAll = false
				} else clickCount++
				
				onFinancialTypeChanged(FinancialType.INCOME)
			},
			modifier = Modifier
				.padding(
					start = 4.dpScaled,
					end = 2.dpScaled
				)
				.height(36.dpScaled)
				.weight(1f)
		)
		
		FilterChip(
			selected = (selectedFinancialType == FinancialType.EXPENSE) or selectAll,
			colors = FilterChipDefaults.filterChipColors(
				selectedContainerColor = expense_card_background
			),
			label = {
				Row(
					verticalAlignment = Alignment.CenterVertically,
					horizontalArrangement = Arrangement.Center,
					modifier = Modifier
						.fillMaxSize()
				) {
					Text(
						text = stringResource(id = R.string.expenses),
						style = MaterialTheme.typography.bodyMedium.copy(
							textAlign = TextAlign.Center,
							fontSize = MaterialTheme.typography.bodyMedium.fontSize.spScaled
						)
					)
				}
			},
			onClick = {
				if (!isRunning) {
					if (!selectAll) {
						eventCountdownTimer.delay(
							timeInMillis = 800,
							onFinish = {
								clickCount = 0
							}
						)
					}
					
					selectAll = false
				} else clickCount++
				
				onFinancialTypeChanged(FinancialType.EXPENSE)
			},
			modifier = Modifier
				.padding(
					start = 2.dpScaled,
					end = 4.dpScaled
				)
				.height(36.dpScaled)
				.weight(1f)
		)
	}
}
