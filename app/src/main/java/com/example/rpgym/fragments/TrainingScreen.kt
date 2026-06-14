package com.example.rpgym.fragments

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rpgym.R
import com.example.rpgym.data.PlayerData

@Composable
fun TrainingScreen() {
    var message by remember { mutableStateOf<Pair<String, String>?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.training_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            
            Text(
                text = "Siła lvl ${PlayerData.strengthLevel}",
                color = Color.White,
                fontSize = 24.sp
            )
            Text(
                text = "${PlayerData.strengthXp}/100 XP",
                color = Color.White,
                fontSize = 20.sp
            )
            LinearProgressIndicator(
                progress = { PlayerData.strengthXp / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            TrainingButton("Pushups (+10 XP)") { PlayerData.addStrengthXp(10) }
            TrainingButton("Pullups (+15 XP)") { PlayerData.addStrengthXp(15) }
            TrainingButton("Run (+5 XP)") { PlayerData.addStrengthXp(5) }
            TrainingButton("Squats (+8 XP)") { PlayerData.addStrengthXp(8) }

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = {
                if (PlayerData.activateHealthPotion()) {
                    message = "❤️ HP Potion" to "+50% regen / bonus HP przez 1h"
                } else {
                    message = "Brak" to "Nie masz mikstury życia"
                }
            }) {
                Text("Aktywuj Eliksir Życia")
            }

            Button(onClick = {
                if (PlayerData.activateStrengthPotion()) {
                    message = "⚡ Siła" to "+damage przez 1h"
                } else {
                    message = "Brak" to "Nie masz mikstury siły"
                }
            }) {
                Text("Aktywuj Eliksir Siły")
            }
        }

        message?.let { (title, msg) ->
            AlertDialog(
                onDismissRequest = { message = null },
                title = { Text(title) },
                text = { Text(msg) },
                confirmButton = {
                    TextButton(onClick = { message = null }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}

@Composable
fun TrainingButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(text)
    }
}
