package com.fit2081.a1mateysh33878463.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.fit2081.a1mateysh33878463.R
import com.fit2081.a1mateysh33878463.Screen
import com.fit2081.a1mateysh33878463.data.homescreen.HomeViewModel
import androidx.compose.runtime.getValue

@Composable
fun HomeScreen(
    onEditClick: () -> Unit,
    onSettingsClick: () -> Unit,
    navController: NavHostController,
    context: Context
) {
    val sharedPreferences = context.getSharedPreferences("userPrefs", Context.MODE_PRIVATE) // Calls the userPrefs shared Preferences
    val userId = sharedPreferences.getString("LoggedInUserId", null) // Obtains the userId thats been stored in it
    val viewModel: HomeViewModel = viewModel()
    val foodQualityScore by viewModel.heifaScore.collectAsState()
    val patientName by viewModel.patientName.collectAsState()



    LaunchedEffect(userId) {
        if (userId != null) {
            viewModel.loadPatient(userId)
        }
    }

    var selectedScreen = remember { mutableStateOf(Screen.HOME) } // Set default selected screen to home


    Scaffold(
        bottomBar = { // Creates bottom nav bar
            NavBottomBar(navController, selectedScreen) // Creates the navbar on Home Screen
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            // Greeting section
            Text(text = "Hello,", fontSize = 18.sp, color = Color.Gray)
            Text(text = patientName, fontSize = 26.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "You've already filled in your Food Intake Questionnaire, but you can change details here:",
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = onEditClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF610ce5)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Edit", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Food Plate Image
            Image(
                painter = painterResource(id = R.drawable.home_screen),
                contentDescription = "Food Plate",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Score section
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text(text = "My Score", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                TextButton( // Text that also functions as a button
                    onClick = {
                        navController.navigate(Screen.INSIGHT.name)
                    }

                ) {
                    Text("See all scores")
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "See all scores"
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = null,
                    tint = Color.Gray
                )
                Text(
                    text = "Your Food Quality score",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "$foodQualityScore/100",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50)
                )
            }

            Spacer(modifier = Modifier.padding(vertical = 30.dp))

            // Explanation text
            Text(
                text = "What is the Food Quality Score?",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Your Food Quality Score provides a snapshot of how well your eating patterns align with established food guidelines, helping you identify both strengths and opportunities for improvement in your diet.",
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "This personalized measurement considers various food groups including vegetables, fruits, whole grains, and proteins to give you practical insights for making healthier food choices.",
                fontSize = 14.sp
            )
        }
    }
}