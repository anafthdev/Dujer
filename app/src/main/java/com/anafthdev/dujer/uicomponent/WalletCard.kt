package com.anafthdev.dujer.uicomponent

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.anafthdev.dujer.data.db.model.Wallet
import com.anafthdev.dujer.foundation.extension.deviceLocale
import com.anafthdev.dujer.foundation.extension.toColor
import com.anafthdev.dujer.foundation.uiextension.horizontalScroll
import com.anafthdev.dujer.foundation.window.dpScaled
import com.anafthdev.dujer.foundation.window.spScaled
import com.anafthdev.dujer.model.LocalCurrency
import com.anafthdev.dujer.ui.theme.black01
import com.anafthdev.dujer.ui.theme.large_shape
import com.anafthdev.dujer.util.CurrencyFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletCard(
	wallet: Wallet,
	modifier: Modifier = Modifier
) {

	Card(
		shape = large_shape,
		elevation = CardDefaults.cardElevation(
			defaultElevation = 1.dp
		),
		colors = CardDefaults.cardColors(
			containerColor = wallet.categoryTint.backgroundTint.toColor()
		),
		onClick = {},
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
				tint = wallet.categoryTint.iconTint.toColor(),
				contentDescription = null,
				modifier = Modifier
					.size(40.dpScaled)
			)
			
			Column(
				verticalArrangement = Arrangement.Center,
				modifier = Modifier
					.padding(start = 8.dpScaled)
					.weight(1f)
			) {
				CompositionLocalProvider(
					LocalContentColor provides black01
				) {
					Text(
						maxLines = 1,
						text = wallet.name,
						overflow = TextOverflow.Ellipsis,
						textAlign = TextAlign.Center,
						style = MaterialTheme.typography.bodyMedium.copy(
							fontWeight = FontWeight.SemiBold,
							fontSize = MaterialTheme.typography.bodyMedium.fontSize.spScaled
						),
						modifier = Modifier
							.padding(2.dpScaled)
					)
					
					Text(
						maxLines = 1,
						textAlign = TextAlign.Center,
						text = CurrencyFormatter.format(
							locale = deviceLocale,
							amount = wallet.balance,
							useSymbol = true,
							currencyCode = LocalCurrency.current.countryCode
						),
						style = MaterialTheme.typography.bodyMedium.copy(
							fontSize = MaterialTheme.typography.bodyMedium.fontSize.spScaled
						),
						modifier = Modifier
							.padding(2.dpScaled)
							.horizontalScroll(
								state = rememberScrollState(),
								autoRestart = true
							)
					)
				}
			}
		}
	}
}