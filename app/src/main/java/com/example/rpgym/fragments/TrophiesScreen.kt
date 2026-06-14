package com.example.rpgym.fragments

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
fun TrophiesScreen(onBack: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.trophies_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Text("Trofea", color = Color.White, fontSize = 24.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Ukończone strefy:", color = Color.White, fontSize = 20.sp)
            
            PlayerData.defeatedBosses.value.forEach { zone ->
                Text("Strefa $zone - UKOŃCZONA ✔", color = Color.Green, fontSize = 18.sp)
            }
            
            if (PlayerData.defeatedBosses.value.isEmpty()) {
                Text("Brak trofeów jeszcze...", color = Color.Gray, fontSize = 18.sp)
            }
        }
    }
}
