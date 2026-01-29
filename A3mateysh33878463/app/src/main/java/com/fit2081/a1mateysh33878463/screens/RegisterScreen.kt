package com.fit2081.a1mateysh33878463.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.fit2081.a1mateysh33878463.Screen
import com.fit2081.a1mateysh33878463.data.registerandlogin.RegisterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    context: Context,
    navController: NavHostController
) {
    val viewModel: RegisterViewModel = viewModel()
    val userIds by viewModel.getAllUserIds().observeAsState(emptyList())

    var selectedUserId by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var phoneInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Register", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(24.dp))

        // User ID dropdown
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = selectedUserId,
                onValueChange = {},
                readOnly = true,
                label = { Text("My ID (Provided by your Clinician)") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                userIds.forEach { userId ->
                    DropdownMenuItem(
                        text = { Text(userId) },
                        onClick = {
                            selectedUserId = userId
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Full Name") },
            placeholder = { Text("Enter your name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = phoneInput,
            onValueChange = {
                if (it.length < 12 && it.all { char -> char.isDigit() }) {
                    phoneInput = it
                    showError = false
                }
            },
            label = { Text("Phone Number") },
            placeholder = { Text("Enter your phone number") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = passwordInput,
            onValueChange = { passwordInput = it },
            label = { Text("Password") },
            placeholder = { Text("Set your password") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            placeholder = { Text("Enter your password again") },
            modifier = Modifier.fillMaxWidth()
        )

        if (showError) {
            Spacer(modifier = Modifier.height(8.dp))
            Text("Invalid information. Please check and try again.", color = Color.Red)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (selectedUserId.isBlank() ||
                    fullName.isBlank() ||
                    phoneInput.isBlank() ||
                    passwordInput.isBlank() ||
                    confirmPassword.isBlank() ||
                    passwordInput.length < 8 ||
                    passwordInput != confirmPassword) {
                    showError = true
                    return@Button
                }

                viewModel.attemptRegister(
                    userId = selectedUserId,
                    phone = phoneInput,
                    name = fullName,
                    password = passwordInput
                ) { success ->
                    if (success) {
                        context.getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
                            .edit().putString("LoggedInUserId", selectedUserId).apply()

                        navController.navigate(Screen.QUESTIONNAIRE.name) {
                            popUpTo(Screen.LOGIN.name) { inclusive = true }
                        }
                    } else {
                        showError = true
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF610ce5), contentColor = Color.White)
        ) {
            Text("Register")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { navController.navigate(Screen.LOGIN.name) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF610ce5), contentColor = Color.White)
        ) {
            Text("Login")
        }
    }
}
