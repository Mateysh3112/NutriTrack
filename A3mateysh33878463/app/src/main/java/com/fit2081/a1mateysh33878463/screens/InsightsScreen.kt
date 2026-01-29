package com.fit2081.a1mateysh33878463.screens

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_SEND
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.fit2081.a1mateysh33878463.Screen
import com.fit2081.a1mateysh33878463.data.insights.InsightsViewModel
import com.fit2081.a1mateysh33878463.functions.loadDataFromCSV
import androidx.compose.runtime.getValue


@Composable
fun InsightsScreen(
    context: Context,
    navController: NavHostController,
    viewModel: InsightsViewModel = viewModel()
) {
    // Access shared Preferences to retrieve userId that is logged in
    val sharedPreferences = context.getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
    val userId = sharedPreferences.getString("LoggedInUserId", null)

    val patient by viewModel.patient.collectAsState()

    val sex = patient?.sex

    LaunchedEffect(userId) {
        if (userId != null) viewModel.loadPatient(userId)
    }

    // function getScore helps identify whether the maleValue or Female value should be returned
    fun getScore(maleValue: String, femaleValue: String): Float {
        if (sex == "Female") {
            return femaleValue.toFloat()
        } else {
            return maleValue.toFloat()
        }
    }

    val scoringCategories = listOf(
        "Vegetables" to getScore(patient?.heifaVegMale ?: "0", patient?.heifaVegFemale ?: "0"),
        "Fruits" to getScore(patient?.heifafruitsMale ?: "0", patient?.heifafruitsFemale ?: "0"),
        "Grains & Cereals" to getScore(patient?.heifaGrainCerealMale ?: "0", patient?.heifaGrainCerealFemale ?: "0"),
        "Whole Grains" to getScore(patient?.heifaWholeGrainMale ?: "0", patient?.heifaWholeGrainFemale ?: "0"),
        "Meat & Alternatives" to getScore(patient?.heifaMeatMale ?: "0", patient?.heifaMeatFemale ?: "0"),
        "Dairy" to getScore(patient?.heifaDairyMale ?: "0", patient?.heifaDairyFemale ?: "0"),
        "Water" to getScore(patient?.heifaWaterMale ?: "0", patient?.heifaWaterFemale ?: "0"),
        "Unsaturated Fats" to getScore(patient?.heifaUnsFatsMale ?: "0", patient?.heifaUnsFatsFemale ?: "0"),
        "Saturated Fats" to getScore(patient?.heifaSatFatsMale ?: "0", patient?.heifaSatFatsFemale ?: "0"),
        "Sodium" to getScore(patient?.heifaSodiumMale ?: "0", patient?.heifaSodiumFemale ?: "0"),
        "Sugar" to getScore(patient?.heifaSugarMale ?: "0", patient?.heifaSugarFemale ?: "0"),
        "Alcohol" to getScore(patient?.heifaAlcoholMale ?: "0", patient?.heifaAlcoholFemale ?: "0"),
        "Discretionary Foods" to getScore(patient?.heifaDiscretionaryMale ?: "0", patient?.heifaDiscretionaryFemale ?: "0")
    )

    val totalScore = getScore(patient?.heifaScoreMale ?: "0", patient?.heifaScoreFemale ?: "0") // Uses getScore function to obtain the total score
    var selectedScreen = remember { mutableStateOf(Screen.INSIGHT) }

    Scaffold(
        bottomBar = {
            NavBottomBar(navController, selectedScreen) // Initialises bottom bar on Insights screen
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Insights: Food Score",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(30.dp))

            // For each scoring categories, Create a Linear Progress Indicator. Adds the text for the score next to progress indicator
            scoringCategories.forEach { (label, score) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = label, fontWeight = FontWeight.Medium)
                        LinearProgressIndicator(
                            progress = (score / 10f),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp),
                            color = Color((0xFF610ce5))

                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("${score.toFloat()}/10")
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Total Food Quality Score",
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LinearProgressIndicator( // Progress indicator for the total score
                    progress = (totalScore / 100f).coerceIn(0f, 1f),
                    modifier = Modifier
                        .weight(1f)
                        .height(8.dp),
                    color = Color(0xFF610ce5)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("${totalScore.toFloat()}/100")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Sharing Data code obtained from course materials
            // https://edstem.org/au/courses/20813/lessons/78691/slides/531688
            Button(
                onClick = {
                    val shareIntent = Intent(ACTION_SEND)
                    shareIntent.type = "text/plain"
                    shareIntent.putExtra(Intent.EXTRA_TEXT, totalScore)
                    context.startActivity(Intent.createChooser(shareIntent, "Share text via"))
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF610ce5))
            ) {
                Icon(Icons.Default.Share, contentDescription = "Share", tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Share with someone", color = Color.White)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    navController.navigate(Screen.NUTRICOACH.name)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF610ce5))
            ) {
                Icon(Icons.Default.Favorite, contentDescription = "Improve", tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Improve my diet!", color = Color.White)
            }
        }
    }
}
