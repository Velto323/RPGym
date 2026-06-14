package com.example.rpgym.fragments

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rpgym.R
import com.example.rpgym.data.PlayerData

@Composable
fun ShopScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.shop_background),
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
                Text("Sklep", color = Color.White, fontSize = 24.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Złoto: ${PlayerData.gold}", color = Color.Yellow, fontSize = 20.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "HP: ${PlayerData.hp}/${PlayerData.getCurrentMaxHp()}\nSiła: ${PlayerData.strengthLevel}",
                color = Color.White,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text("Mikstury HP: ${PlayerData.healthPotions}", color = Color.White)
            Button(onClick = {
                if (PlayerData.buyHealthPotion()) {
                    Toast.makeText(context, "Kupiono miksturę HP!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Za mało złota!", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("Kup Miksturę HP (20G)")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Mikstury siły: ${PlayerData.strengthPotions}", color = Color.White)
            Button(onClick = {
                if (PlayerData.buyStrengthPotion()) {
                    Toast.makeText(context, "Kupiono miksturę siły!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Za mało złota!", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("Kup Miksturę Siły (20G)")
            }
        }
    }
}
