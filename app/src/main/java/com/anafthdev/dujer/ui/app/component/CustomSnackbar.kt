package com.anafthdev.dujer.ui.app.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.anafthdev.dujer.R
import com.anafthdev.dujer.foundation.window.dpScaled
import com.anafthdev.dujer.foundation.window.spScaled

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSnackbar(
	snackbarData: SnackbarData,
	onCancel: () -> Unit
) {
	ElevatedCard(
		shape = MaterialTheme.shapes.large,
		colors = CardDefaults.elevatedCardColors(
			containerColor = MaterialTheme.colorScheme.inverseSurface
		),
		elevation = CardDefaults.elevatedCardElevation(
			defaultElevation = 8.dpScaled
		)
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier
				.padding(
					vertical = 4.dpScaled,
					horizontal = 8.dpScaled
				)
		) {
			Text(
				maxLines = 2,
				overflow = TextOverflow.Ellipsis,
				text = snackbarData.visuals.message,
				style = MaterialTheme.typography.bodyMedium.copy(
					fontSize = MaterialTheme.typography.bodyMedium.fontSize.spScaled
				),
				modifier = Modifier
					.weight(0.8f)
			)
			
			TextButton(
				contentPadding = PaddingValues(8.dpScaled),
				colors = ButtonDefaults.textButtonColors(
					contentColor = MaterialTheme.colorScheme.primaryContainer
				),
				onClick = {
					onCancel()
					snackbarData.dismiss()
				}
			) {
				Row(
					verticalAlignment = Alignment.CenterVertically
				) {
					Icon(
						painter = painterResource(id = R.drawable.ic_undo),
						contentDescription = null,
						modifier = Modifier
							.padding(8.dpScaled)
					)
					
					Text(
						text = stringResource(id = R.string.cancel),
						style = LocalTextStyle.current.copy(
							fontWeight = FontWeight.Medium,
							fontSize = LocalTextStyle.current.fontSize.spScaled
						)
					)
				}
			}
		}
	}
}
