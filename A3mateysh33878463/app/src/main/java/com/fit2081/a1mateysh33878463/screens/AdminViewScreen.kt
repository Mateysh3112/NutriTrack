package com.fit2081.a1mateysh33878463.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fit2081.a1mateysh33878463.data.admin.AdminViewModel
import com.fit2081.a1mateysh33878463.data.nutricoachai.NutriCoachAiUiState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.fit2081.a1mateysh33878463.Screen


@Composable
fun AdminViewScreen(
    context: Context,
    navController: NavHostController,
    viewModel: AdminViewModel = viewModel(),
    selectedScreen: MutableState<Screen> = remember { mutableStateOf(Screen.ADMIN_VIEW) }
) {
    val maleAverage by viewModel.maleAverage.observeAsState("-")
    val femaleAverage by viewModel.femaleAverage.observeAsState("-")
    val patterns by viewModel.genAiPatterns.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        bottomBar = {
            NavBottomBar(navController = navController, selectedScreen = selectedScreen)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            Text("Clinician Dashboard", fontSize = 24.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = maleAverage,
                onValueChange = {},
                readOnly = true,
                label = { Text("Average HEIFA (Male)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = femaleAverage,
                onValueChange = {},
                readOnly = true,
                label = { Text("Average HEIFA (Female)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = { viewModel.generatePatterns() },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF610ce5)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Search, contentDescription = "AI", tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Find Data Pattern", color = Color.White)
            }

            Spacer(modifier = Modifier.height(24.dp))

            when (val state = patterns) {
                is NutriCoachAiUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is NutriCoachAiUiState.Success -> {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        state.outputText.split("\n\n").forEach { paragraph ->
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Text(
                                    text = paragraph.trim(),
                                    modifier = Modifier.padding(12.dp),
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
                is NutriCoachAiUiState.Error -> {
                    Text("Error: ${state.errorMessage}", color = Color.Red)
                }
                else -> {}
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { navController.popBackStack() },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF610ce5)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Done, contentDescription = "Done", tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Done", color = Color.White)
            }
        }
    }
}
