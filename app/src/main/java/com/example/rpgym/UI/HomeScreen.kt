package com.example.rpgym.UI

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rpgym.R
import com.example.rpgym.data.PlayerData

@Composable
fun HomeScreen() {
    var level by remember { mutableIntStateOf(PlayerData.level) }
    var xp by remember { mutableIntStateOf(PlayerData.xp) }

    // Observe changes in PlayerData if possible, but here we just update locally for simplicity
    // or we could use a listener. Since PlayerData has a listener mechanism, we should use it.

    DisposableEffect(Unit) {
        val listener = {
            level = PlayerData.level
            xp = PlayerData.xp
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
            .padding(top = 80.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Level: $level",
            fontSize = 24.sp,
            color = Color.White
        )

        Text(
            text = "XP: $xp/100",
            fontSize = 18.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                PlayerData.addXp(20)
                PlayerData.completedTasks++
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Wykonaj zadanie (+XP)")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Image(
            painter = painterResource(id = R.drawable.avatar_player),
            contentDescription = null,
            modifier = Modifier.height(293.dp)
        )
    }
}
