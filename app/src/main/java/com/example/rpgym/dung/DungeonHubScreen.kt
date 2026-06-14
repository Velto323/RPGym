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
import com.example.rpgym.data.*

@Composable
fun DungeonHubScreen(
    onEnterDungeon: (Int) -> Unit,
    onMeditation: () -> Unit
) {
    var index by remember { mutableStateOf(0) }
    val zones = DungeonRepository.zones
    val zone = zones.getOrNull(index) ?: zones[0]

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
            Text(text = "Lochy", color = Color.White, fontSize = 28.sp)
            
            Spacer(modifier = Modifier.height(32.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = {
                    if (index > 0) index--
                    else index = PlayerData.unlockedZone
                }) {
                    Text("<")
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = zone.name, color = Color.White, fontSize = 24.sp)
                    Text(text = zone.description, color = Color.Gray, fontSize = 16.sp)
                }

                Button(onClick = {
                    if (index < PlayerData.unlockedZone) index++
                    else index = 0
                }) {
                    Text(">")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { onEnterDungeon(index) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Enter Dungeon")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    PlayerData.startMeditation()
                    onMeditation()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Meditation")
            }
        }
    }
}
