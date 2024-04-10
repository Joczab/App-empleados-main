package com.urb.composeapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit

@Composable
fun TopComponent(
    iconVisible: Boolean = true,
    modifier: Modifier = Modifier.fillMaxWidth(),
    title: String = "",
    text: String = "",
    weightArrow: Float = 0.5f,
    weightText: Float = 0.7f,
    titleSize: TextUnit = MaterialTheme.typography.bodyLarge.fontSize,
    textSize: TextUnit =  MaterialTheme.typography.bodyMedium.fontSize,
    iconPressed: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = if (iconVisible) Arrangement.SpaceBetween else Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (iconVisible) {
            Box(modifier = Modifier.weight(weightArrow)) {
                DefaultBackArrow {
                    iconPressed()
                }
            }
        }

        when {
            text.isNotEmpty() && title.isNotEmpty() -> {
                Box(modifier = Modifier.weight(weightText)) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CustomText(
                            text = title,
                            fontSize = titleSize,
                            fontWeight = FontWeight.Bold
                        )
                        CustomText(text = text, fontSize = textSize)
                    }
                }
            }
            title.isNotEmpty() -> {
                Box(modifier = Modifier.weight(weightText)) {
                    CustomText(
                        text = title,
                        fontSize = titleSize,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            text.isNotEmpty() -> {
                Box(modifier = Modifier.weight(weightText)) {
                    CustomText(
                        text = text,
                        fontSize = textSize,
                    )
                }
            }
        }
    }
}