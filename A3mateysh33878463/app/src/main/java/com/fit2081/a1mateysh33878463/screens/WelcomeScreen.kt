package com.fit2081.a1mateysh33878463.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fit2081.a1mateysh33878463.R

@Composable
fun WelcomeScreen(onLoginClick: () -> Unit) { // onLoginClick is lambda function hence does not return anything
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp), // padding all sides
        verticalArrangement = Arrangement.SpaceBetween, //Fits item in column with even gaps between item
        horizontalAlignment = Alignment.CenterHorizontally // Centers each item horizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "NutriTrack",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Image(
            painter = painterResource(id = R.drawable.nutri_track_logo),
            contentDescription = "App Logo",
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Disclaimer text
        Text(
            text = "This app provides general health and nutrition information for educational purposes only. It is not intended as medical advice, diagnosis, or treatment. Always consult a qualified healthcare professional before making any changes to your diet, exercise, or health regimen. Use this app at your own risk. If you’d like to an Accredited Practicing Dietitian (APD), please visit the Monash Nutrition/Dietetics Clinic (discounted rates for students): https://www.monash.edu/medicine/scs/nutrition/clinics/nutrition",
            textAlign = TextAlign.Center,
            fontSize = 15.sp,
            modifier = Modifier.padding(horizontal = 16.dp), // add padding to both left and right
            fontWeight = FontWeight.Medium,
            fontStyle = FontStyle.Italic
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Login Button
        Button (
            onClick = onLoginClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF610ce5), // background color
                contentColor = Color.White   // text/icon color
            )
        ) {
            Text("Login")
        }

        // Student Name and ID
        Text(
            text = "Designed By Mateysh Naidu (33878463)",
            fontSize = 14.sp,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}