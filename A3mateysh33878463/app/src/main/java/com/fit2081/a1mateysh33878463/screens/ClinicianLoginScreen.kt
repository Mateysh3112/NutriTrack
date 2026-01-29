package com.fit2081.a1mateysh33878463.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.Login
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.fit2081.a1mateysh33878463.Screen

@Composable
fun ClinicianLoginScreen(
    navController: NavHostController,
    selectedScreen: MutableState<Screen>
) {
    var inputKey by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            NavBottomBar(navController = navController, selectedScreen = selectedScreen)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Clinician Login", fontSize = 22.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = inputKey,
                onValueChange = {
                    inputKey = it
                    error = false
                },
                label = { Text("Clinician Key") },
                placeholder = { Text("Enter your clinician key") },
                isError = error,
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (inputKey.trim() == "dollar-entry-apples") {
                        selectedScreen.value = Screen.ADMIN_VIEW
                        navController.navigate(Screen.ADMIN_VIEW.name)
                    } else {
                        error = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF610ce5))
            ) {
                Icon(Icons.Default.Login, contentDescription = null, tint = Color.White)
                Spacer(Modifier.width(8.dp))
                Text("Clinician Login", color = Color.White)
            }

            if (error) {
                Spacer(modifier = Modifier.height(12.dp))
                Text("Incorrect key. Please try again.", color = Color.Red)
            }
        }
    }
}
