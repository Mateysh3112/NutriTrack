package com.fit2081.a1mateysh33878463

import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fit2081.a1mateysh33878463.ui.theme.A1mateysh33878463Theme
import java.io.BufferedReader
import java.io.InputStreamReader
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.LinearProgressIndicator
import androidx.lifecycle.lifecycleScope
import com.fit2081.a1mateysh33878463.data.AppDatabase
import com.fit2081.a1mateysh33878463.functions.migrateCSV
import com.fit2081.a1mateysh33878463.screens.AdminViewScreen
import com.fit2081.a1mateysh33878463.screens.ClinicianLoginScreen
import com.fit2081.a1mateysh33878463.screens.FoodIntakeQuestionnnaire
import kotlinx.coroutines.launch
import com.fit2081.a1mateysh33878463.screens.HomeScreen
import com.fit2081.a1mateysh33878463.screens.InsightsScreen
import com.fit2081.a1mateysh33878463.screens.LoginScreen
import com.fit2081.a1mateysh33878463.screens.NavBottomBar
import com.fit2081.a1mateysh33878463.screens.NutriCoachScreen
import com.fit2081.a1mateysh33878463.screens.RegisterScreen
import com.fit2081.a1mateysh33878463.screens.SettingsScreen
import com.fit2081.a1mateysh33878463.screens.WelcomeScreen


// data class to store information that will be extracted from the CSV
data class User(
    val userId: String,
    val phoneNumber: String,
    val sex: String,
    // HEIFA score fields separated by sex and category
    val heifaScoreMale: String,
    val heifaScoreFemale: String,
    val heifaVegFemale: String,
    val heifaVegMale: String,
    val heifafruitsFemale: String,
    val heifafruitsMale: String,
    val heifaGrainCerealFemale: String,
    val heifaGrainCerealMale: String,
    val heifaWholeGrainFemale: String,
    val heifaWholeGrainMale: String,
    val heifaMeatFemale: String,
    val heifaMeatMale: String,
    val heifaDairyFemale: String,
    val heifaDairyMale: String,
    val heifaWaterFemale: String,
    val heifaWaterMale: String,
    val heifaUnsFatsFemale: String,
    val heifaUnsFatsMale: String,
    val heifaSodiumFemale: String,
    val heifaSodiumMale: String,
    val heifaSugarFemale: String,
    val heifaSugarMale: String,
    val heifaAlcoholFemale: String,
    val heifaAlcoholMale: String,
    val heifaDiscretionaryFemale: String,
    val heifaDiscretionaryMale: String,
    val heifaSatFatsMale: String,
    val heifaSatFatsFemale: String
)

// Main entry point of the app
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Uncomment the following to reset the migration flag (for development use only)
            /*getSharedPreferences("migrationPrefs", Context.MODE_PRIVATE)
            .edit().putBoolean("csvMigrated", false).apply()*/

        // Launch coroutine on lifecycleScope to perform one-time CSV data migration into Room database
        lifecycleScope.launch{
            migrateCSV(applicationContext, AppDatabase.getDatabase(application))
        }

        // Enables modern edge-to-edge UI layout
        enableEdgeToEdge()
        setContent { // UI gets composed here
            A1mateysh33878463Theme {
                val navController: NavHostController = rememberNavController() // Helps control the navigation between screens
                val context = LocalContext.current // Provides android the context regarding the current Activity
                var loggedInUser by remember { mutableStateOf<User?>(null)} // variable to save which user has logged in

                val selectedScreen = remember { mutableStateOf(Screen.WELCOME) }
                // MutableState = Variable that can change over time
                Scaffold(modifier = Modifier.fillMaxSize()) { innerpadding ->
                    Column( modifier = Modifier.padding(innerpadding)) {
                        NavHost( // Displays the navigation actions performed
                            navController = navController,
                            startDestination = Screen.WELCOME.name // Starts the application from the welcome screen
                        ) {
                            composable(Screen.WELCOME.name) { //Screen.WELCOME.name will navigate to welcome screen
                                WelcomeScreen( // Because of the composable, welcome screen being called in this composable
                                    onLoginClick = {
                                        navController.navigate(Screen.LOGIN.name) // When login is clicked, navigate to login screen
                                    }
                                )
                            }

                            composable(Screen.LOGIN.name) {
                                LoginScreen(
                                    context = context,
                                    navController = navController,
                                    onLoginSuccess = { user, savedPreferences ->
                                        if (savedPreferences) {
                                            navController.navigate(Screen.HOME.name) // When logged in successfully, navigate to home screen
                                        } else {
                                            navController.navigate(Screen.QUESTIONNAIRE.name)
                                        } // When logged in successfully, navigate to questionnaire screen
                                        loggedInUser = user // save the user that logged in
                                    }
                                )
                            }

                            composable(Screen.SETTINGS.name) {
                                SettingsScreen(
                                    context = context,
                                    navController = navController,
                                    selectedScreen = remember { mutableStateOf(Screen.SETTINGS) }
                                )
                            }

                            composable(Screen.QUESTIONNAIRE.name) {
                                FoodIntakeQuestionnnaire(
                                    navController = navController,
                                    context = context,
                                )
                            }

                            composable(Screen.HOME.name) {
                                HomeScreen(
                                    onEditClick = {
                                        navController.navigate(Screen.QUESTIONNAIRE.name)
                                    },
                                    onSettingsClick = {
                                        navController.navigate(Screen.SETTINGS.name)
                                    },
                                    navController = navController,
                                    context = context
                                )
                            }

                            composable(Screen.INSIGHT.name) {
                                InsightsScreen(
                                    context = context,
                                    navController = navController
                                )
                            }

                            composable(Screen.REGISTER.name) {
                                RegisterScreen(
                                    context = context,
                                    navController = navController
                                )
                            }

                            composable(Screen.NUTRICOACH.name){
                                NutriCoachScreen(
                                    context = context,
                                    navController = navController,
                                    selectedScreen = remember { mutableStateOf(Screen.NUTRICOACH) }
                                )
                            }

                            composable(Screen.ADMIN_VIEW.name) {
                                AdminViewScreen(
                                    context = context,
                                    navController = navController,
                                    selectedScreen = remember { mutableStateOf(Screen.ADMIN_VIEW) }
                                )
                            }

                            composable(Screen.CLINICIAN_LOGIN.name) {
                                ClinicianLoginScreen(navController, selectedScreen)
                            }
                        }
                    }
                }
            }
        }
    }
}

enum class Screen { // Similar to enum used in Java, except in this case its used to navigate through screens
    WELCOME,
    LOGIN,
    QUESTIONNAIRE,
    HOME,
    INSIGHT,
    NUTRICOACH,
    SETTINGS,
    REGISTER,
    CLINICIAN_LOGIN,
    ADMIN_VIEW
}









