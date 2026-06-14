package com.example.rpgym.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rpgym.R
import com.example.rpgym.data.PlayerData
import com.example.rpgym.data.PlayerRepository
import com.example.rpgym.data.local.AppDatabase
import com.example.rpgym.main.SupabaseClient
import com.example.rpgym.ui.components.ScreenBackground
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch

@Composable
fun PlayerScreen(
    onOpenShop: () -> Unit,
    onOpenTrophies: () -> Unit,
    onLoggedOut: () -> Unit,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    ScreenBackground(R.drawable.player_background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("Poziom: ${PlayerData.level}", fontSize = 20.sp)
            Text("XP: ${PlayerData.xp}/100", fontSize = 16.sp, modifier = Modifier.padding(top = 4.dp))
            Text("Siła: ${PlayerData.strengthLevel} (${PlayerData.strength})", fontSize = 16.sp, modifier = Modifier.padding(top = 4.dp))
            Text("Złoto: ${PlayerData.gold}", fontSize = 16.sp, modifier = Modifier.padding(top = 4.dp))
            Text("HP: ${PlayerData.hp}/${PlayerData.getCurrentMaxHp()}", fontSize = 16.sp, modifier = Modifier.padding(top = 4.dp))
            Text("Eliksir siły: ${if (PlayerData.hasPowerPotion()) "AKTYWNY" else "OFF"}", fontSize = 16.sp, modifier = Modifier.padding(top = 4.dp))
            Text("Eliksir życia: ${if (PlayerData.hasLifePotion()) "AKTYWNY" else "OFF"}", fontSize = 16.sp, modifier = Modifier.padding(top = 4.dp))
            Text("Questy ukończone: ${PlayerData.defeatedBosses.size}", fontSize = 16.sp, modifier = Modifier.padding(top = 4.dp))

            Button(onClick = onOpenShop, modifier = Modifier.padding(top = 16.dp)) {
                Text("Sklep")
            }
            Button(onClick = onOpenTrophies, modifier = Modifier.padding(top = 8.dp)) {
                Text("Trofea")
            }
            Button(
                onClick = {
                    scope.launch {
                        val uid = SupabaseClient.client.auth.currentUserOrNull()?.id
                        if (uid != null) {
                            runCatching {
                                PlayerRepository(AppDatabase.getInstance(context.applicationContext))
                                    .savePlayer(uid)
                            }
                        }
                        try {
                            SupabaseClient.client.auth.signOut()
                        } catch (_: Exception) {
                        }
                        onLoggedOut()
                    }
                },
                modifier = Modifier.padding(top = 8.dp),
            ) {
                Text("Wyloguj")
            }
        }
    }
}
