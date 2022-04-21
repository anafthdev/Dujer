package com.anafthdev.dujer.ui.financial.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import com.anafthdev.dujer.data.db.model.Category
import com.anafthdev.dujer.foundation.extension.toColor
import com.anafthdev.dujer.foundation.window.dpScaled
import com.anafthdev.dujer.foundation.window.spScaled
import com.anafthdev.dujer.ui.theme.Typography
import com.anafthdev.dujer.ui.theme.large_shape
import com.anafthdev.dujer.ui.theme.medium_shape
import com.anafthdev.dujer.ui.theme.shapes

@Composable
fun CategoryList(
	categories: List<Category>,
	onItemClick: (Category) -> Unit
) {
	
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
		categories.forEach { category ->
			Row(
				verticalAlignment = Alignment.CenterVertically,
				modifier = Modifier
					.fillMaxWidth()
					.clickable {
						onItemClick(category)
					}
					.padding(8.dpScaled)
			) {
				Box(
					contentAlignment = Alignment.Center,
					modifier = Modifier
						.size(48.dpScaled)
						.clip(medium_shape)
						.background(category.tint.backgroundTint.toColor())
				) {
					Icon(
						painter = painterResource(id = category.iconID),
						tint = category.tint.iconTint.toColor(),
						contentDescription = null,
						modifier = Modifier
							.size(20.dpScaled)
					)
				}
				
				Text(
					text = category.name,
					overflow = TextOverflow.Ellipsis,
					style = Typography.bodyMedium.copy(
						fontSize = Typography.bodyMedium.fontSize.spScaled
					),
					modifier = Modifier
						.padding(8.dpScaled)
						.fillMaxWidth()
				)
			}
		}
	}
}