package com.bugiman.composercalculator.view.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DistanceConverter() {

    ModalBottomSheet(
        onDismissRequest = {},
//        sheetState = sheetState,
        containerColor = Color(color = 0xFF1C1C1E),
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .width(width = 40.dp)
                    .height(height = 4.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )
        },
    ) {

    }

}