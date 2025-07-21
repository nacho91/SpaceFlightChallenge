package com.nacho.spaceflight.challenge.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun Error(
    modifier: Modifier = Modifier,
    message: String,
    onRetryClick: () -> Unit
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(message, style = MaterialTheme.typography.headlineLarge, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    onRetryClick()
                }
            ) {
                Text("Retry")
            }
        }
    }
}