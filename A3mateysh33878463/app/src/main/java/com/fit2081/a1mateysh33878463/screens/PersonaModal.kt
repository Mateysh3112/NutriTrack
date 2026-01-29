package com.fit2081.a1mateysh33878463.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Alert Dialog Function structure obtained from course materials
// https://edstem.org/au/courses/20813/lessons/77916/slides/527591
@Composable
fun PersonaModal(
    title: String,
    description: String,
    imageResId: Int,
    onDismiss: () -> Unit
) {
    // Displays Alert Dialog
    AlertDialog(
        onDismissRequest = onDismiss, // Triggers when dialog is dismissed
        confirmButton = { // Section for Button
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = onDismiss, // Close dialog when clicked
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF610ce5)) // Set button color to purple
                ) {
                    Text("Dismiss", color = Color.White)
                }
            }
        },
        title = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 18.sp, textAlign = TextAlign.Center, modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = description,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}