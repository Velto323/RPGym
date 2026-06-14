package com.example.rpgym.UI

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rpgym.R
import com.example.rpgym.data.PlayerData
import com.example.rpgym.main.LoginActivity
import com.example.rpgym.main.SupabaseClient
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch

@Composable
fun PlayerScreen() {
    var subScreen by remember { mutableStateOf("main") } // main, shop, trophies

    when (subScreen) {
        "main" -> PlayerMainContent(
            onOpenShop = { subScreen = "shop" },
            onOpenTrophies = { subScreen = "trophies" }
        )
        "shop" -> ShopScreen(onBack = { subScreen = "main" })
        "trophies" -> TrophiesScreen(onBack = { subScreen = "main" })
    }
}

@Composable
fun PlayerMainContent(onOpenShop: () -> Unit, onOpenTrophies: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    
    var level by remember { mutableIntStateOf(PlayerData.level) }
    var xp by remember { mutableIntStateOf(PlayerData.xp) }
    var strengthLevel by remember { mutableIntStateOf(PlayerData.strengthLevel) }
    var strength by remember { mutableIntStateOf(PlayerData.strength) }
    var gold by remember { mutableIntStateOf(PlayerData.gold) }
    var hp by remember { mutableIntStateOf(PlayerData.hp) }
    var maxHp by remember { mutableIntStateOf(PlayerData.getCurrentMaxHp()) }
    var hasPowerPotion by remember { mutableStateOf(PlayerData.hasPowerPotion()) }
    var hasLifePotion by remember { mutableStateOf(PlayerData.hasLifePotion()) }

    DisposableEffect(Unit) {
        val listener = {
            level = PlayerData.level
            xp = PlayerData.xp
            strengthLevel = PlayerData.strengthLevel
            strength = PlayerData.strength
            gold = PlayerData.gold
            hp = PlayerData.hp
            maxHp = PlayerData.getCurrentMaxHp()
            hasPowerPotion = PlayerData.hasPowerPotion()
            hasLifePotion = PlayerData.hasLifePotion()
        }
        PlayerData.addListener(listener)
        onDispose { PlayerData.removeListener(listener) }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.player_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Poziom: $level", fontSize = 24.sp, color = Color.White)
            Text(text = "XP: $xp/100", fontSize = 20.sp, color = Color.White)
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(onClick = onOpenShop, modifier = Modifier.size(132.dp, 72.dp)) {
                Text("Sklep")
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(text = "Siła: $strengthLevel ($strength)", fontSize = 20.sp, color = Color.White)
            Text(text = "Złoto: $gold", fontSize = 20.sp, color = Color.White)
            Text(text = "HP: $hp/$maxHp", fontSize = 20.sp, color = Color.Red)
            
            Spacer(modifier = Modifier.height(10.dp))
            
            Text(text = "Eliksir siły: ${if (hasPowerPotion) "AKTYWNY" else "OFF"}", color = Color.White)
            Text(text = "Eliksir życia: ${if (hasLifePotion) "AKTYWNY" else "OFF"}", color = Color.White)
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Button(onClick = onOpenTrophies, modifier = Modifier.size(132.dp, 69.dp)) {
                Text("Trofea")
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Button(onClick = {
                scope.launch {
                    try { SupabaseClient.client.auth.signOut() } catch (_: Exception) {}
                    context.startActivity(Intent(context, LoginActivity::class.java))
                }
            }, modifier = Modifier.size(146.dp, 78.dp)) {
                Text("Wyloguj")
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(text = "Questy ukończone: ${PlayerData.defeatedBosses.size}", fontSize = 20.sp, color = Color.White)
        }
    }
}

@Composable
fun ShopScreen(onBack: () -> Unit) {
    var gold by remember { mutableIntStateOf(PlayerData.gold) }
    var hp by remember { mutableIntStateOf(PlayerData.hp) }
    var maxHp by remember { mutableIntStateOf(PlayerData.getCurrentMaxHp()) }
    var strengthLevel by remember { mutableIntStateOf(PlayerData.strengthLevel) }
    var healthPotions by remember { mutableIntStateOf(PlayerData.healthPotions) }
    var strengthPotions by remember { mutableIntStateOf(PlayerData.strengthPotions) }

    DisposableEffect(Unit) {
        val listener = {
            gold = PlayerData.gold
            hp = PlayerData.hp
            maxHp = PlayerData.getCurrentMaxHp()
            strengthLevel = PlayerData.strengthLevel
            healthPotions = PlayerData.healthPotions
            strengthPotions = PlayerData.strengthPotions
        }
        PlayerData.addListener(listener)
        onDispose { PlayerData.removeListener(listener) }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.shop_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        IconButton(onClick = onBack, modifier = Modifier.padding(16.dp).align(Alignment.TopStart)) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Wstecz", tint = Color.White)
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Sklep", fontSize = 26.sp, color = Color.White, fontWeight = FontWeight.Bold)
            Text(text = "Złoto: $gold", fontSize = 18.sp, color = Color.Yellow)
            
            Spacer(modifier = Modifier.height(10.dp))
            
            Text(text = "HP: $hp/$maxHp\nSiła: $strengthLevel", color = Color.White, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
            
            Spacer(modifier = Modifier.height(10.dp))
            
            Text(text = "Mikstury HP: $healthPotions", color = Color.White)
            Text(text = "Mikstury siły: $strengthPotions", color = Color.White)
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Button(onClick = { PlayerData.buyHealthPotion() }) {
                Text("Kup miksturę HP (20 złota)")
            }
            
            Spacer(modifier = Modifier.height(10.dp))
            
            Button(onClick = { PlayerData.buyStrengthPotion() }) {
                Text("Kup miksturę siły (20 złota)")
            }
        }
    }
}

@Composable
fun TrophiesScreen(onBack: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        IconButton(onClick = onBack, modifier = Modifier.padding(16.dp).align(Alignment.TopStart)) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Wstecz", tint = Color.White)
        }
        Text(text = "Trofea (Wkrótce)", modifier = Modifier.align(Alignment.Center), fontSize = 24.sp, color = Color.White)
    }
}
