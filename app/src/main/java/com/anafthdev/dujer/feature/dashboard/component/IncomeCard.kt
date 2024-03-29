package com.anafthdev.dujer.feature.dashboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.anafthdev.dujer.R
import com.anafthdev.dujer.feature.theme.black03
import com.anafthdev.dujer.feature.theme.income_card_background
import com.anafthdev.dujer.feature.theme.large_shape
import com.anafthdev.dujer.foundation.common.CurrencyFormatter
import com.anafthdev.dujer.foundation.extension.deviceLocale
import com.anafthdev.dujer.foundation.ui.LocalUiColor
import com.anafthdev.dujer.foundation.uiextension.horizontalScroll
import com.anafthdev.dujer.foundation.uiextension.sizeBasedWidth
import com.anafthdev.dujer.foundation.window.dpScaled
import com.anafthdev.dujer.foundation.window.spScaled
import com.anafthdev.dujer.model.LocalCurrency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomeCard(
	income: Double,
	modifier: Modifier = Modifier,
	onClick: () -> Unit
) {
	
	Card(
		shape = large_shape,
		onClick = onClick,
		elevation = CardDefaults.cardElevation(
			defaultElevation = 0.dp,
			focusedElevation = 0.dp,
			hoveredElevation = 0.dp
		),
		colors = CardDefaults.cardColors(
			containerColor = income_card_background
		),
		modifier = modifier
			.fillMaxWidth()
			.sizeBasedWidth(
				enlargement = 1.2f
			)
	) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(16.dpScaled)
		) {
			
			Box(
				contentAlignment = Alignment.Center,
				modifier = Modifier
					.size(48.dpScaled)
					.clip(RoundedCornerShape(100))
					.background(Color(0xFF48827C))
			) {
				Icon(
					painter = painterResource(id = R.drawable.ic_bank),
					tint = Color.White,
					contentDescription = null
				)
			}
			
			Spacer(
				modifier = Modifier
					.weight(1f)
			)
			
			Text(
				text = CurrencyFormatter.format(
					locale = deviceLocale,
					amount = income,
					useSymbol = true,
					currencyCode = LocalCurrency.current.countryCode
				),
				style = MaterialTheme.typography.titleSmall.copy(
					color = black03,
					fontWeight = FontWeight.Bold,
					fontSize = MaterialTheme.typography.titleSmall.fontSize.spScaled
				),
				modifier = Modifier
					.padding(
						vertical = 4.dpScaled
					)
					.horizontalScroll(
						state = rememberScrollState(),
						autoRestart = true
					)
			)
			
			Text(
				text = stringResource(id = R.string.all_income),
				style = MaterialTheme.typography.bodySmall.copy(
					color = LocalUiColor.current.titleText,
					fontWeight = FontWeight.Normal,
					fontSize = MaterialTheme.typography.bodySmall.fontSize.spScaled
				),
				modifier = Modifier
					.padding(
						vertical = 4.dpScaled
					)
			)
			
		}
	}
}
