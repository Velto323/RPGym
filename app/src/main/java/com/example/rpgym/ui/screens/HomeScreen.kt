package com.example.rpgym.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rpgym.R
import com.example.rpgym.data.PlayerData

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Level: ${PlayerData.level}", fontSize = 22.sp)
        Text("XP: ${PlayerData.xp}/100", fontSize = 18.sp, modifier = Modifier.padding(top = 8.dp))

        Button(
            onClick = {
                PlayerData.addXp(20)
                PlayerData.completedTasks++
            },
            modifier = Modifier.padding(top = 24.dp),
        ) {
            Text("Perform task")
        }

        Image(
            painter = painterResource(R.drawable.avatar_player),
            contentDescription = "Player avatar",
            modifier = Modifier.padding(top = 24.dp).size(200.dp),
        )
    }
}
