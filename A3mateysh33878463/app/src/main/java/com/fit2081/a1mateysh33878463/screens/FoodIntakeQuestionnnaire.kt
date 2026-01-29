package com.fit2081.a1mateysh33878463.screens

import android.content.Context
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.fit2081.a1mateysh33878463.R
import com.fit2081.a1mateysh33878463.Screen
import com.fit2081.a1mateysh33878463.data.foodIntake.FoodIntake
import com.fit2081.a1mateysh33878463.data.foodIntake.FoodIntakeViewModel
import com.fit2081.a1mateysh33878463.functions.TimePickerFun

@OptIn(ExperimentalMaterial3Api::class) // Generated using AI for the purpose of dropdown menu
@Composable
fun FoodIntakeQuestionnnaire(navController: NavController, context: Context) {
    val userPrefs = context.getSharedPreferences("userPrefs", Context.MODE_PRIVATE) // Retrieve shared preference file named userPrefs
    val loggedInUserId= userPrefs.getString("LoggedInUserId", "") ?: "" // Reads the value associated with key loggedInUserId
    // ?: "" ensures non-null String always returned

    val viewModel: FoodIntakeViewModel = viewModel()
    val storedFoodIntake by viewModel.getFoodIntake(loggedInUserId).observeAsState()


    // These are the actual state variables tied to the form
    var fruits by remember { mutableStateOf(false) }
    var vegetables by remember { mutableStateOf(false) }
    var grains by remember { mutableStateOf(false) }
    var redMeat by remember { mutableStateOf(false) }
    var seafood by remember { mutableStateOf(false) }
    var poultry by remember { mutableStateOf(false) }
    var fish by remember { mutableStateOf(false) }
    var eggs by remember { mutableStateOf(false) }
    var nutsSeeds by remember { mutableStateOf(false) }

    var selectedPersona by remember { mutableStateOf("") }
    val biggestMealTime = remember { mutableStateOf("00:00") }
    val sleepTime = remember { mutableStateOf("00:00") }
    val wakeupTime = remember { mutableStateOf("00:00") }

    LaunchedEffect(storedFoodIntake) {
        storedFoodIntake?.let {
            fruits = it.fruits
            vegetables = it.vegetables
            grains = it.grains
            redMeat = it.redMeat
            seafood = it.seafood
            poultry = it.poultry
            fish = it.fish
            eggs = it.eggs
            nutsSeeds = it.nutsSeeds
            selectedPersona = it.persona
            biggestMealTime.value = it.biggestMeal
            sleepTime.value = it.sleepTime
            wakeupTime.value = it.wakeTime
        }
    }


    Scaffold( // Full-screen UI structure: provides layout slots for top bar
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Food Intake Questionnaire",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Screen.HOME.name)
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()) // To make it scrollable if content overflows
                .fillMaxSize()
                .padding(16.dp)
        ) {
            var showDialog by remember { mutableStateOf(false) }
            var dialogTitle by remember { mutableStateOf("")}
            var dialogDescription by remember { mutableStateOf("")}
            var dialogImageId by remember { mutableStateOf(R.drawable.persona_1)}

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.Start // Aligns everything starting from the left then to right
            ) {
                Text(
                    text = "Tick all the food categories you can eat",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .padding(horizontal = 8.dp)
                )

                Row(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween // Space between variables only
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) { // modifier.weight(1f) provides this content 1 equal share of the row
                        Checkbox(checked = fruits, onCheckedChange = { fruits = it }) // Notifies the checkbox whether it should appear checked, changes the value of fruit appropriately
                        Text("Fruits", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        Checkbox(checked = vegetables, onCheckedChange = { vegetables = it})
                        Text("Vegetables", fontSize = 13.sp, fontWeight = FontWeight.Medium)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        Checkbox(checked = grains, onCheckedChange = { grains = it})
                        Text("Grains", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        Checkbox(checked = redMeat, onCheckedChange = { redMeat = it })
                        Text("Red Meat", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        Checkbox(checked = seafood, onCheckedChange = { seafood = it})
                        Text("Seafood", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        Checkbox(checked = poultry, onCheckedChange = { poultry = it})
                        Text("Poultry", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        Checkbox(checked = fish, onCheckedChange = { fish = it })
                        Text("Fish", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        Checkbox(checked = eggs, onCheckedChange = { eggs = it})
                        Text("Eggs", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        Checkbox(checked = nutsSeeds, onCheckedChange = { nutsSeeds = it})
                        Text("Nuts/Seeds", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    }
                }
            }

            Spacer(modifier = Modifier.padding(16.dp))

            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Your Persona",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = "People can be broadly classified into 6 different types based on their eating preferences. Click on each button below to find out the different types, and select the type that best fits you!",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 12.dp),
                    fontWeight = FontWeight.Medium
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally) // Center everything horizontally, add 8.dp space between each content
                    ) {
                        Button(
                            onClick = {
                                dialogTitle = "Health Devotee"
                                dialogDescription = "I’m passionate about healthy eating & health plays a big part in my life. " +
                                        "I use social media to follow active lifestyle personalities or get new recipes/exercise ideas. " +
                                        "I may even buy superfoods or follow a particular type of diet. I like to think I am super healthy.\n"
                                dialogImageId = R.drawable.persona_1
                                showDialog = true
                            },
                            modifier = Modifier
                                .weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF610ce5)
                            )
                        ) {
                            Text(
                                text = "Health " +
                                        "Devotee",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                            )
                        }
                        Button(
                            onClick = {
                                dialogTitle = "Mindful Eater"
                                dialogDescription = "I’m health-conscious and being healthy and eating healthy is important to me. " +
                                        "Although health means different things to different people, I make conscious lifestyle decisions about eating based on what I believe healthy means. " +
                                        "I look for new recipes and healthy eating information on social media.\n"
                                dialogImageId = R.drawable.persona_2
                                showDialog = true
                            },
                            modifier = Modifier
                                .weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF610ce5)
                            )
                        ) {
                            Text(
                                text = "Mindful " +
                                        "Eater",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
                    ) {
                        Button(
                            onClick = {
                                dialogTitle = "Balance Seeker"
                                dialogDescription = "I try and live a balanced lifestyle, and I think that all foods are okay in moderation. " +
                                        "I shouldn’t have to feel guilty about eating a piece of cake now and again. " +
                                        "I get all sorts of inspiration from social media like finding out about new restaurants, fun recipes and sometimes healthy eating tips.\n"
                                dialogImageId = R.drawable.persona_4
                                showDialog = true
                            },
                            modifier = Modifier
                                .weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF610ce5)
                            )
                        ) {
                            Text(
                                text = "Balance Seeker",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                            )
                        }
                        Button(
                            onClick = {
                                dialogTitle = "Health Procrastinator"
                                dialogDescription = "I’m contemplating healthy eating but it’s not a priority for me right now. " +
                                        "I know the basics about what it means to be healthy, but it doesn’t seem relevant to me right now. " +
                                        "I have taken a few steps to be healthier but I am not motivated to make it a high priority because I have too many other things going on in my life.\n"
                                dialogImageId = R.drawable.persona_5
                                showDialog = true
                            },
                            modifier = Modifier
                                .weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF610ce5)
                            )
                        ) {
                            Text(
                                text = "Health Procrastinator",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
                    ) {
                        Button(
                            onClick = {
                                dialogTitle = "Wellness Striver"
                                dialogDescription = "I aspire to be healthy (but struggle sometimes). H" +
                                        "ealthy eating is hard work! I’ve tried to improve my diet, but always find things that make it difficult to stick with the changes. " +
                                        "Sometimes I notice recipe ideas or healthy eating hacks, and if it seems easy enough, I’ll give it a go.\n"
                                dialogImageId = R.drawable.persona_3
                                showDialog = true
                            },
                            modifier = Modifier
                                .weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF610ce5)
                            )
                        ) {
                            Text(
                                text = "Wellness " +
                                        "Striver",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                            )
                        }
                        Button(
                            onClick = {
                                dialogTitle = "Food Carefree"
                                dialogDescription = "I’m not bothered about healthy eating. " +
                                        "I don’t really see the point and I don’t think about it. " +
                                        "I don’t really notice healthy eating tips or recipes and I don’t care what I eat."
                                dialogImageId = R.drawable.persona_6
                                showDialog = true
                            },
                            modifier = Modifier
                                .weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF610ce5)
                            )
                        ) {
                            Text(
                                text = "Food Carefree",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                            )
                        }

                    }

                    if (showDialog){ // if show dialog is true, display the information
                        PersonaModal(
                            title = dialogTitle,
                            description = dialogDescription,
                            imageResId = dialogImageId,
                            onDismiss = { showDialog = false } // close dialog
                        )
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                val personaOptions = listOf("Health Devotee", "Mindful Eater", "Wellness Striver",
                    "Balance Seeker", "Health Procrastinator", "Food Carefree")

                var expanded by remember { mutableStateOf(false)}

                Text(
                    text = "Which persona best fits you?",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom=12.dp)
                )

                // Dropdown for User ID
                /*
                ChatGPT Prompt: How to create a dropdown menu in kotlin
                 */
                ExposedDropdownMenuBox(
                    expanded = expanded, // Determines if dropdown menu is shown
                    onExpandedChange = { expanded = !expanded}, // Toggles the dropdown menu when clicked

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    TextField(
                        value = selectedPersona, // The currently selected persona
                        onValueChange = {}, // Disabled because its read only
                        readOnly = true, // Prevent manual typing
                        label = { Text("Select option") }, // label inside the TextField
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) }, // Adds the dropdown arrow
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(), // Anchors the dropdown to the TextField
                        colors = ExposedDropdownMenuDefaults.textFieldColors(
                            focusedIndicatorColor = Color(0xFF610ce5),
                            unfocusedIndicatorColor = Color(0xFF610ce5),
                            focusedLabelColor = Color(0xFF610ce5),
                            cursorColor = Color(0xFF610ce5)
                        )
                    )

                    // The actual dropdown menu that appears when expanded = true
                    // ChatGPT guided code: How to create a dropdown menu in kotlin
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false} // Closes the menu when clicking outside
                    ) {
                        // Iterate through the list of personas to create each dropdown item
                        personaOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option)}, // Display the user's ID
                                onClick = {
                                    selectedPersona = option // Update the selected persona
                                    expanded = false // Close the dropdown
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "Timings",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                )

                var biggestMealPicker = TimePickerFun(biggestMealTime) // Calls the function TimePickerFun and stores the output in the variable
                var sleepTimePicker = TimePickerFun(sleepTime)
                var wakeupTimePicker = TimePickerFun(wakeupTime)

                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically // Centered vertically within the row
                ) {
                    Text(
                        text = "What time of day approx. " +
                                "do you normally eat your biggest meal?",
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = 5.dp)
                    )
                    Button (
                        onClick = {biggestMealPicker.show()},
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF610ce5)
                        )
                    ) {
                        Text(text = biggestMealTime.value)
                    }
                }
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "What time of day approx. " +
                                "do you go to sleep at night?",
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = 5.dp)
                    )
                    Button (
                        onClick = {sleepTimePicker.show()},
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF610ce5)
                        )
                    ) {
                        Text(text = sleepTime.value)
                    }
                }
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "What time of day approx. " +
                                "do you wake up in the morning?",
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = 5.dp)
                    )
                    Button (
                        onClick = {wakeupTimePicker.show()}, // Displays the time picker
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF610ce5)
                        )
                    ) {
                        Text(text = wakeupTime.value)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    val formCompleted = selectedPersona.isNotBlank() // Does not allow form to be submitted unlesss these fields contain a value
                            && biggestMealTime.value != "00.00"
                            && sleepTime.value != "00:00"
                            && wakeupTime.value != "00:00"
                            && biggestMealTime.value != sleepTime.value
                            && biggestMealTime.value != wakeupTime.value
                            && sleepTime.value != wakeupTime.value
                    Button(
                        onClick = {
                            val foodIntake = FoodIntake(
                                userId = loggedInUserId,
                                fruits = fruits,
                                vegetables = vegetables,
                                grains = grains,
                                redMeat = redMeat,
                                seafood = seafood,
                                poultry = poultry,
                                fish = fish,
                                eggs = eggs,
                                nutsSeeds = nutsSeeds,
                                persona = selectedPersona,
                                biggestMeal = biggestMealTime.value,
                                sleepTime = sleepTime.value,
                                wakeTime = wakeupTime.value
                            )
                            viewModel.saveFoodIntake(foodIntake)
                            navController.navigate(Screen.HOME.name)
                        }
                        ,
                        enabled = formCompleted, // Button only enabled when formCompleted returns true
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF610ce5),
                            disabledContainerColor = Color.Gray
                        )
                    ) {
                        Text(text = "Save", fontWeight = FontWeight.Medium, fontSize = 14.sp,
                            color = if(formCompleted) {
                                Color.White
                            } else {
                                Color.LightGray
                            })
                    }
                }
            }
        }
    }

}