package com.fit2081.a1mateysh33878463.screens

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.fit2081.a1mateysh33878463.Screen
import com.fit2081.a1mateysh33878463.data.patient.Patient
import com.fit2081.a1mateysh33878463.data.patient.PatientViewModel

@Composable
fun SettingsScreen(
    context: Context,
    navController: NavHostController,
    selectedScreen: MutableState<Screen>
) {

    val viewModel: PatientViewModel = viewModel()
    val sharedPrefs = context.getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
    val loggedInUserId = sharedPrefs.getString("LoggedInUserId", null)

    var patient by remember { mutableStateOf<Patient?>(null) }


    // Load patient details only once when screen is composed
    LaunchedEffect(loggedInUserId) {
        loggedInUserId?.let {
            patient = viewModel.getPatientById(it)
        }
    }

    Scaffold(
        bottomBar = {
            NavBottomBar(navController = navController, selectedScreen = selectedScreen)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Settings", fontSize = 24.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(24.dp))

            Text("ACCOUNT", fontSize = 14.sp, color = Color.Gray, fontWeight = FontWeight.SemiBold, modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.height(12.dp))

            // Account Details
            patient?.let {
                SettingsRow(icon = Icons.Default.Person, label = it.name)
                SettingsRow(icon = Icons.Default.Phone, label = it.phoneNumber)
                SettingsRow(icon = Icons.Default.Badge, label = it.userId)
            }

            Divider(modifier = Modifier.padding(vertical = 24.dp))

            Text("OTHER SETTINGS", fontSize = 14.sp, color = Color.Gray, fontWeight = FontWeight.SemiBold, modifier = Modifier.align(Alignment.Start))

            Spacer(modifier = Modifier.height(12.dp))

            SettingsRow(
                icon = Icons.Default.ExitToApp,
                label = "Logout",
                trailing = Icons.Default.KeyboardArrowRight
            ) {
                sharedPrefs.edit().clear().apply()
                navController.navigate(Screen.WELCOME.name) {
                    popUpTo(Screen.WELCOME.name) { inclusive = true }
                }
            }

            // Clinician Login
            SettingsRow(
                icon = Icons.Default.Person,
                label = "Clinician Login",
                trailing = Icons.Default.KeyboardArrowRight
            ) {
                navController.navigate(Screen.CLINICIAN_LOGIN.name)
            }
        }
    }
}

@Composable
fun SettingsRow(
    icon: ImageVector,
    label: String,
    trailing: ImageVector? = null,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = label, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(label, fontSize = 16.sp, modifier = Modifier.weight(1f))
        trailing?.let {
            Icon(it, contentDescription = "Arrow", modifier = Modifier.size(20.dp))
        }
    }
}