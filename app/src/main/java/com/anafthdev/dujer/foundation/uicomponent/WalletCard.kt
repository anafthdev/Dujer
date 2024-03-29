package com.anafthdev.dujer.foundation.uicomponent

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.anafthdev.dujer.data.model.Wallet
import com.anafthdev.dujer.feature.theme.black03
import com.anafthdev.dujer.feature.theme.black05
import com.anafthdev.dujer.feature.theme.large_shape
import com.anafthdev.dujer.foundation.common.CurrencyFormatter
import com.anafthdev.dujer.foundation.extension.deviceLocale
import com.anafthdev.dujer.foundation.extension.toColor
import com.anafthdev.dujer.foundation.uiextension.horizontalScroll
import com.anafthdev.dujer.foundation.window.dpScaled
import com.anafthdev.dujer.foundation.window.spScaled
import com.anafthdev.dujer.model.LocalCurrency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletCard(
	wallet: Wallet,
	modifier: Modifier = Modifier,
	onClick: () -> Unit
) {

	Card(
		shape = large_shape,
		elevation = CardDefaults.cardElevation(
			defaultElevation = 1.dp
		),
		colors = CardDefaults.cardColors(
			containerColor = wallet.tint.backgroundTint.toColor()
		),
		onClick = onClick,
		modifier = modifier
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier
				.fillMaxSize()
				.padding(
					vertical = 16.dpScaled,
					horizontal = 8.dpScaled
				)
		) {
			Icon(
				painter = painterResource(id = wallet.iconID),
				tint = wallet.tint.iconTint.toColor(),
				contentDescription = null,
				modifier = Modifier
					.size(40.dpScaled)
			)
			
			Column(
				verticalArrangement = Arrangement.Center,
				modifier = Modifier
					.padding(start = 8.dpScaled)
					.fillMaxWidth()
					.weight(1f)
			) {
				Text(
					maxLines = 1,
					text = wallet.name,
					overflow = TextOverflow.Ellipsis,
					textAlign = TextAlign.Start,
					style = MaterialTheme.typography.titleSmall.copy(
						color = black03,
						fontWeight = FontWeight.SemiBold,
						fontSize = MaterialTheme.typography.titleSmall.fontSize.spScaled
					),
					modifier = Modifier
						.padding(2.dpScaled)
						.fillMaxWidth()
				)
				
				Text(
					maxLines = 1,
					textAlign = TextAlign.Start,
					text = CurrencyFormatter.format(
						locale = deviceLocale,
						amount = wallet.balance,
						useSymbol = true,
						currencyCode = LocalCurrency.current.countryCode
					),
					style = MaterialTheme.typography.bodyMedium.copy(
						color = black05,
						fontSize = MaterialTheme.typography.bodyMedium.fontSize.spScaled
					),
					modifier = Modifier
						.padding(2.dpScaled)
						.fillMaxWidth()
						.horizontalScroll(
							state = rememberScrollState(),
							autoRestart = true
						)
				)
			}
		}
	}
}
