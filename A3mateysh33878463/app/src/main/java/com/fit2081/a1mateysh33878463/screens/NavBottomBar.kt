package com.fit2081.a1mateysh33878463.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.fit2081.a1mateysh33878463.Screen

@Composable
fun NavBottomBar (
    navController: NavHostController,
    selectedScreen: MutableState<Screen>
) {

    val items = listOf( // Creates a list linking each String to a Screen
        "Home" to Screen.HOME,
        "Insights" to Screen.INSIGHT,
        "NutriCoach" to Screen.NUTRICOACH,
        "Settings" to Screen.SETTINGS
    )

    // Creates the Bottom Bar
    NavigationBar {
        items.forEachIndexed { index, item -> // For each item in the list, structure them into (label, screen)
            val(label, screen) = item
            NavigationBarItem(
                icon = {
                    when (screen) {
                        // Choose which Icon to display based on the screen
                        Screen.HOME-> Icon(Icons.Filled.Home, contentDescription = "Home")
                        Screen.INSIGHT -> Icon(Icons.Filled.Email, contentDescription = "Insights")
                        Screen.NUTRICOACH -> Icon(Icons.Filled.Call, contentDescription = "Nutri Coach")
                        Screen.SETTINGS-> Icon(Icons.Filled.Settings, contentDescription = "Settings")
                        else -> Icon(Icons.Filled.Info, contentDescription = "Unknown")
                    }
                },
                label = { Text(label) },
                selected = selectedScreen.value == screen, // Highlight if this screen is selected
                onClick = {
                    selectedScreen.value = screen // Update selected screen state
                    if (screen == Screen.HOME || screen == Screen.INSIGHT || screen == Screen.SETTINGS || screen == Screen.NUTRICOACH) {
                        navController.navigate(screen.name)
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.Black,
                    selectedTextColor = Color(0xFF610ce5),
                    unselectedTextColor = Color.Black,
                    indicatorColor = Color(0xFF610ce5) // Optional: lighter purple for selected background
                )
            )
        }
    }
}