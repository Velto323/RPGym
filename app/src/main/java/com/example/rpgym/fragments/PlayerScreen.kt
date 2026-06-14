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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rpgym.R
import com.example.rpgym.data.PlayerData

@Composable
fun PlayerScreen(
    onShopClick: () -> Unit,
    onTrophiesClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.player_background),
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
            Text(
                text = "Poziom: ${PlayerData.level}",
                color = Color.White,
                fontSize = 24.sp
            )

            Text(
                text = "XP: ${PlayerData.xp}/100",
                color = Color.White,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onShopClick,
                modifier = Modifier.size(width = 132.dp, height = 72.dp)
            ) {
                Text("Sklep")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Siła: ${PlayerData.strengthLevel} (${PlayerData.strength})",
                color = Color.White,
                fontSize = 20.sp
            )

            Text(
                text = "Złoto: ${PlayerData.gold}",
                color = Color.White,
                fontSize = 20.sp
            )

            Text(
                text = "HP: ${PlayerData.hp}/${PlayerData.getCurrentMaxHp()}",
                color = Color(0xFFFF4444),
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onTrophiesClick,
                modifier = Modifier.size(width = 132.dp, height = 69.dp)
            ) {
                Text("Trofea")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Eliksir siły: ${if (PlayerData.hasPowerPotion()) "AKTYWNY" else "OFF"}",
                color = Color.White,
                fontSize = 18.sp
            )

            Text(
                text = "Eliksir życia: ${if (PlayerData.hasLifePotion()) "AKTYWNY" else "OFF"}",
                color = Color.White,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onLogoutClick,
                modifier = Modifier.size(width = 146.dp, height = 78.dp)
            ) {
                Text("Wyloguj")
            }

            Text(
                text = "Questy ukończone: ${PlayerData.defeatedBosses.value.size}",
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}
