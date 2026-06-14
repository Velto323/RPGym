package com.example.rpgym.dung

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
import kotlinx.coroutines.delay

@Composable
fun MeditationScreen() {
    LaunchedEffect(Unit) {
        PlayerData.startMeditation()
        while (true) {
            PlayerData.tickMeditation()
            delay(1000)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.quest_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
        Text(text = "Medytacja", color = Color.Cyan, fontSize = 28.sp)
        
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "HP: ${PlayerData.hp} / ${PlayerData.getCurrentMaxHp()}",
            color = Color.White,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        val t = PlayerData.getTimeToFullHp()
        Text(
            text = if (t <= 0) "FULL HP ✔" else "Do pełnego HP: ${t}s",
            color = Color.Gray,
            fontSize = 18.sp
        )
    }
}
}
