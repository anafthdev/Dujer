package com.anafthdev.dujer.feature.financial.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.anafthdev.dujer.data.model.Wallet
import com.anafthdev.dujer.feature.theme.shapes
import com.anafthdev.dujer.foundation.common.CurrencyFormatter
import com.anafthdev.dujer.foundation.extension.deviceLocale
import com.anafthdev.dujer.foundation.extension.isLightTheme
import com.anafthdev.dujer.foundation.extension.toColor
import com.anafthdev.dujer.foundation.ui.LocalUiColor
import com.anafthdev.dujer.foundation.uiextension.horizontalScroll
import com.anafthdev.dujer.foundation.uimode.data.LocalUiMode
import com.anafthdev.dujer.foundation.window.dpScaled
import com.anafthdev.dujer.foundation.window.spScaled
import com.anafthdev.dujer.model.LocalCurrency

@Composable
fun WalletList(
	wallets: List<Wallet>,
	onWalletSelected: (Wallet) -> Unit
) {
	
	val uiMode = LocalUiMode.current
	
	Column(
		modifier = Modifier
			.fillMaxWidth()
			.clip(shapes.large)
			.border(
				width = 1.dpScaled,
				color = MaterialTheme.colorScheme.outline,
				shape = shapes.large
			)
	) {
		wallets.forEach { wallet ->
			Row(
				verticalAlignment = Alignment.CenterVertically,
				modifier = Modifier
					.fillMaxWidth()
					.clickable { onWalletSelected(wallet) }
					.padding(8.dpScaled)
			) {
				Box(
					contentAlignment = Alignment.Center,
					modifier = Modifier
						.size(48.dpScaled)
						.clip(MaterialTheme.shapes.medium)
						.border(
							width = 1.dpScaled,
							shape = MaterialTheme.shapes.medium,
							color = if (uiMode.isLightTheme()) MaterialTheme.colorScheme.outline
							else Color.Transparent
						)
						.background(wallet.tint.backgroundTint.toColor())
				) {
					Icon(
						painter = painterResource(id = wallet.iconID),
						tint = wallet.tint.iconTint.toColor(),
						contentDescription = null
					)
				}
				
				Column(
					modifier = Modifier
						.padding(
							horizontal = 8.dpScaled
						)
						.weight(1f)
				) {
					Text(
						maxLines = 1,
						text = wallet.name,
						overflow = TextOverflow.Ellipsis,
						textAlign = TextAlign.Center,
						style = MaterialTheme.typography.titleSmall.copy(
							color = LocalUiColor.current.titleText,
							fontWeight = FontWeight.SemiBold,
							fontSize = MaterialTheme.typography.titleSmall.fontSize.spScaled
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
							color = LocalUiColor.current.bodyText,
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
