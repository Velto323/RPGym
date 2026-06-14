package com.example.rpgym.ui.screens

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rpgym.data.DungeonRepository
import com.example.rpgym.data.PlayerData

@Composable
fun DungeonHubScreen(
    onEnterBattle: (zoneIndex: Int) -> Unit,
    onMeditate: () -> Unit,
) {
    var index by remember { mutableIntStateOf(0) }

    fun next() {
        index = if (index < PlayerData.unlockedZone) index + 1 else 0
    }

    fun prev() {
        index = if (index > 0) index - 1 else PlayerData.unlockedZone
    }

    val zone = DungeonRepository.zones[index]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                var total = 0f
                detectHorizontalDragGestures(
                    onDragStart = { total = 0f },
                    onDragEnd = {
                        if (total > 120f) prev()
                        else if (total < -120f) next()
                    },
                ) { change, dragAmount ->
                    total += dragAmount
                    change.consume()
                }
            }
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(zone.name, fontSize = 26.sp, textAlign = TextAlign.Center)
        Text(
            zone.description,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp),
        )
        Text(
            "← przesuń, aby zmienić lokację →",
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp),
        )

        Button(
            onClick = {
                PlayerData.currentZone = index
                PlayerData.dungeonWave = 1
                onEnterBattle(index)
            },
            modifier = Modifier.padding(top = 24.dp),
        ) {
            Text("Wejdź do lochu")
        }

        Button(
            onClick = {
                PlayerData.startMeditation()
                onMeditate()
            },
            modifier = Modifier.padding(top = 12.dp),
        ) {
            Text("Medytacja")
        }
    }
}
