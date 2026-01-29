package com.fit2081.a1mateysh33878463.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavHostController
import com.fit2081.a1mateysh33878463.Screen
import com.fit2081.a1mateysh33878463.User
import com.fit2081.a1mateysh33878463.data.patient.toUser
import com.fit2081.a1mateysh33878463.data.registerandlogin.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    context: Context,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onLoginSuccess: (User, Boolean) -> Unit
) {
    var selectedUserId by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    val viewModel: LoginViewModel = viewModel()
    val userIds by viewModel.getAllUserIds().observeAsState(emptyList())

    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Log in", fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 32.dp))

        Spacer(modifier = Modifier.height(16.dp))

        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            TextField(
                value = selectedUserId,
                onValueChange = {},
                readOnly = true,
                label = { Text("My ID (Provided by your Clinician)") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                modifier = Modifier.fillMaxWidth().menuAnchor(),
                colors = ExposedDropdownMenuDefaults.textFieldColors(
                    focusedIndicatorColor = Color(0xFF610ce5),
                    unfocusedIndicatorColor = Color(0xFF610ce5),
                    focusedLabelColor = Color(0xFF610ce5),
                    cursorColor = Color(0xFF610ce5)
                )
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                userIds.forEach { userId ->
                    DropdownMenuItem(
                        text = { Text(userId) },
                        onClick = {
                            selectedUserId = userId
                            expanded = false
                    })
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = passwordInput,
            onValueChange = { passwordInput = it },
            label = { Text("Password") },
            placeholder = { Text("Enter your password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            modifier = Modifier.fillMaxWidth()
        )


        if (showError) {
            Text("Invalid credentials", color = Color.Red, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "This app is only for pre-registered users. Please have your ID and phone number handy before continuing.",
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.attemptLogin(
                    selectedUserId,
                    passwordInput
                ) { success, patient, completed ->
                    if (success && patient != null) {
                        val prefs = context.getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
                        prefs.edit().putString("LoggedInUserId", patient.userId).apply()

                        onLoginSuccess(patient.toUser(), completed)
                    } else {
                        showError = true
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF610ce5),
                contentColor = Color.White
            )
        ) {
            Text("Continue")
        }



        Spacer(modifier = Modifier.height(16.dp))

        // Register Button
        Button(
            onClick = {
                navController.navigate(Screen.REGISTER.name)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF610ce5),
                contentColor = Color.White
            )
        ) {
            Text("Register")
        }
    }
}
