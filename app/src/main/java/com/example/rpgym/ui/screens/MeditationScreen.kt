package com.example.rpgym.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rpgym.data.PlayerData
import kotlinx.coroutines.delay

@Composable
fun MeditationScreen() {
    // Replaces the old Handler/Looper polling loop: tick once per second while shown.
    LaunchedEffect(Unit) {
        PlayerData.startMeditation()
        while (true) {
            PlayerData.tickMeditation()
            delay(1000)
        }
    }

    val timeToFull = PlayerData.getTimeToFullHp()

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("HP: ${PlayerData.hp} / ${PlayerData.getCurrentMaxHp()}", fontSize = 22.sp)
        Text(
            if (timeToFull <= 0) "FULL HP ✔" else "Do pełnego HP: ${timeToFull}s",
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 12.dp),
        )
    }
}
