package com.urb.composeapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

@Composable
fun FABComponent(
    onAdd: () -> Unit
) {
    FloatingActionButton(onClick = { onAdd() }) {
        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Icon")
    }
}
