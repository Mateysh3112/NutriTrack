package com.fit2081.a1mateysh33878463.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Chat
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.fit2081.a1mateysh33878463.Screen
import com.fit2081.a1mateysh33878463.data.nutricoachai.NutriCoachAiViewModel
import kotlinx.coroutines.runBlocking
import androidx.compose.material3.Icon
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.font.FontStyle
import coil3.compose.AsyncImage
import com.fit2081.a1mateysh33878463.data.nutricoachai.NutriCoachAiUiState


@Composable
fun NutriCoachScreen(
    context: Context,
    navController: NavHostController,
    selectedScreen: MutableState<Screen>,
    viewModel: NutriCoachAiViewModel = viewModel()
) {
    val prefs = context.getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
    val userId = prefs.getString("LoggedInUserId", null) ?: return

    val patient = runBlocking { viewModel.getPatient(userId) }

    val foodPrefs = context.getSharedPreferences("foodIntakePref", Context.MODE_PRIVATE)
    val fruitScore = if (patient.sex == "Female")
        patient.heifafruitsFemale.toFloatOrNull() ?: 0f
    else
        patient.heifafruitsMale.toFloatOrNull() ?: 0f

    val isOptimal = fruitScore >= 5f
    Log.d("NutriCoach", "SEX=${patient.sex}, SCORE=$fruitScore, Optimal=$isOptimal")


    var fruitName by remember { mutableStateOf("") }
    var fruitDetails by remember { mutableStateOf<Map<String, String>?>(null) }
    var aiTip by remember { mutableStateOf("") }
    var showTipModal by remember { mutableStateOf(false) }
    val previousTips by viewModel.getTips(userId).observeAsState(emptyList())

    val uiState by viewModel.uiState.collectAsState()

    val scrollState = rememberScrollState()

    val randomImageId = remember { (1..1000).random() }

    Scaffold(
        bottomBar = {
            NavBottomBar(navController, selectedScreen)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("NutriCoach", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(16.dp))

            if (isOptimal) {
                // Fruit Search
                OutlinedTextField(
                    value = fruitName,
                    onValueChange = { fruitName = it },
                    label = { Text("Fruit Name") },
                    placeholder = { Text("Type in the fruit's name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        viewModel.getFruitDetails(fruitName) { result ->
                            fruitDetails = result
                        }
                    },
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF610ce5))
                ) {
                    Icon(Icons.Default.Search, contentDescription = "Details", tint = Color.White)
                    Spacer(Modifier.width(8.dp))
                    Text("Details", color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (fruitDetails != null && fruitDetails!!.isNotEmpty()) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        listOf("family", "calories", "fat", "sugar", "carbohydrates", "protein").forEach { key ->
                            val value = fruitDetails!![key] ?: "-"
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White, RoundedCornerShape(8.dp))
                                    .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = key, fontSize = 14.sp, color = Color.Black)
                                Text(text = ": $value", fontSize = 14.sp, color = Color.Black)
                            }
                        }
                    }
                }


                Spacer(modifier = Modifier.height(16.dp))
            } else {
                // Random Image

                AsyncImage(
                    model = "https://picsum.photos/$randomImageId",
                    contentDescription = "Random Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(20.dp))
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            // Motivational Message
            Button(
                onClick = {
                    viewModel.generatePersonalisedTip(userId)
                },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF610ce5))
            ) {
                Icon(Icons.Rounded.Chat, contentDescription = "AI", tint = Color.White)
                Spacer(Modifier.width(8.dp))
                Text("Motivational Message (AI)", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (uiState) {
                is NutriCoachAiUiState.Loading -> Text("Generating message...", fontStyle = FontStyle.Italic)
                is NutriCoachAiUiState.Success -> Text((uiState as NutriCoachAiUiState.Success).outputText)
                is NutriCoachAiUiState.Error -> Text("Error: ${(uiState as NutriCoachAiUiState.Error).errorMessage}", color = Color.Red)
                else -> {}
            }

            if (aiTip.isNotBlank()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(aiTip)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Show all tips
            Button(
                onClick = { showTipModal = true },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF610ce5))
            ) {
                Text("Shows All Tips", color = Color.White)
            }

            if (showTipModal) {
                AlertDialog(
                    onDismissRequest = { showTipModal = false },
                    confirmButton = {
                        TextButton(onClick = { showTipModal = false }) {
                            Text("Close")
                        }
                    },
                    title = { Text("All Tips") },
                    text = {
                        if (previousTips.isEmpty()) {
                            Text("No tips saved yet.")
                        } else {
                            Column {
                                previousTips.forEach {
                                    Text("• ${it.message}", modifier = Modifier.padding(bottom = 8.dp))
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}
