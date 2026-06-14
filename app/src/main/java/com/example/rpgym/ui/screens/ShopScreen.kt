package com.example.rpgym.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rpgym.R
import com.example.rpgym.data.PlayerData
import com.example.rpgym.ui.components.ScreenBackground

@Composable
fun ShopScreen(onBack: () -> Unit) {
    val context = LocalContext.current

    ScreenBackground(R.drawable.shop_background) {
        IconButton(onClick = onBack, modifier = Modifier.padding(8.dp)) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("Sklep", fontSize = 28.sp)
            Text("Złoto: ${PlayerData.gold}", fontSize = 18.sp, modifier = Modifier.padding(top = 16.dp))
            Text(
                "HP: ${PlayerData.hp}/${PlayerData.getCurrentMaxHp()}\nSiła: ${PlayerData.strengthLevel}",
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp),
            )

            Text("Mikstury HP: ${PlayerData.healthPotions}", fontSize = 16.sp, modifier = Modifier.padding(top = 16.dp))
            Button(
                onClick = {
                    if (!PlayerData.buyHealthPotion()) {
                        Toast.makeText(context, "Za mało złota!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Kupiono miksturę HP!", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.padding(top = 8.dp),
            ) {
                Text("Kup miksturę HP (20)")
            }

            Text("Mikstury siły: ${PlayerData.strengthPotions}", fontSize = 16.sp, modifier = Modifier.padding(top = 16.dp))
            Button(
                onClick = {
                    if (!PlayerData.buyStrengthPotion()) {
                        Toast.makeText(context, "Za mało złota!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Kupiono miksturę siły!", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.padding(top = 8.dp),
            ) {
                Text("Kup miksturę siły (20)")
            }
        }
    }
}
