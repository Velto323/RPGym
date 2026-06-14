package com.example.rpgym.UI

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rpgym.data.PlayerData

@Composable
fun TrainingScreen() {
    var strengthLevel by remember { mutableIntStateOf(PlayerData.strengthLevel) }
    var strengthXp by remember { mutableIntStateOf(PlayerData.strengthXp) }

    DisposableEffect(Unit) {
        val listener = {
            strengthLevel = PlayerData.strengthLevel
            strengthXp = PlayerData.strengthXp
        }
        PlayerData.addListener(listener)
        onDispose {
            PlayerData.removeListener(listener)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Siła lvl $strengthLevel",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Text(
            text = "$strengthXp/100 XP",
            color = Color.White,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        LinearProgressIndicator(
            progress = { strengthXp.toFloat() / 100f },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        )

        val buttons = listOf(
            "💪 Pompki\n+10 XP" to 10,
            "🦍 Podciąganie\n+15 XP" to 15,
            "🏃 Bieg\n+5 XP" to 5,
            "🦵 Przysiady\n+8 XP" to 8
        )

        Column {
            buttons.chunked(2).forEach { row ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    row.forEach { (label, xpAmount) ->
                        Button(
                            onClick = { PlayerData.addStrengthXp(xpAmount) },
                            modifier = Modifier
                                .weight(1f)
                                .height(120.dp)
                                .padding(4.dp)
                        ) {
                            Text(label, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                        }
                    }
                    if (row.size == 1) Spacer(modifier = Modifier.weight(1f))
                }
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { PlayerData.activateHealthPotion() },
                    modifier = Modifier
                        .weight(1f)
                        .height(120.dp)
                        .padding(4.dp)
                ) {
                    Text("❤ Eliksir Życia\nMedytacja wolniejsza", textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                }
                Button(
                    onClick = { PlayerData.activateStrengthPotion() },
                    modifier = Modifier
                        .weight(1f)
                        .height(120.dp)
                        .padding(4.dp)
                ) {
                    Text("⚡ Eliksir Mocy\n+damage przez 1h", textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                }
            }
        }
    }
}
