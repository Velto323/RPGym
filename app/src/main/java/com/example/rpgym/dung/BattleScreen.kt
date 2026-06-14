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
import com.example.rpgym.quest.QuestManager

@Composable
fun BattleScreen(zoneIndex: Int) {
    val zone = DungeonRepository.zones[zoneIndex]
    
    // Manual monster state because it's local to the screen
    var monsterHp by remember { mutableStateOf(0) }
    var monsterName by remember { mutableStateOf("") }
    var monsterStrength by remember { mutableStateOf(0) }
    var monsterIsBoss by remember { mutableStateOf(false) }

    fun spawn() {
        val isBoss = PlayerData.dungeonWave % 5 == 0
        monsterName = if (isBoss) zone.bossName else zone.mobName
        monsterHp = if (isBoss) zone.bossHp else zone.mobHp
        monsterStrength = if (isBoss) zone.bossAttack else zone.mobAttack
        monsterIsBoss = isBoss
    }

    // Initialize
    LaunchedEffect(zoneIndex) {
        spawn()
    }

    var dialogData by remember { mutableStateOf<Pair<String, String>?>(null) }

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
        Text(text = zone.name, color = Color.White, fontSize = 24.sp)
        Text(text = "Fala: ${PlayerData.dungeonWave}", color = Color.Gray, fontSize = 18.sp)
        
        Spacer(modifier = Modifier.height(32.dp))

        Text(text = monsterName, color = if (monsterIsBoss) Color.Red else Color.White, fontSize = 28.sp)
        Text(text = "HP: $monsterHp", color = Color.White, fontSize = 20.sp)

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                // Player attacks
                monsterHp -= PlayerData.strength

                if (monsterHp <= 0) {
                    QuestManager.onMonsterKilled()
                    if (monsterIsBoss) {
                        PlayerData.addXp((zoneIndex + 1) * 25)
                        val firstTime = PlayerData.addBossKill(zoneIndex)
                        if (firstTime) {
                            QuestManager.onBossKilled(zoneIndex)
                        }
                        dialogData = "👑 BOSS POKONANY" to (if (firstTime) "Odblokowano nową lokację!\n+${(zoneIndex + 1) * 25} XP" else "+${(zoneIndex + 1) * 25} XP")
                    }
                    PlayerData.dungeonWave++
                    spawn()
                }

                // Monster attacks
                PlayerData.damage(monsterStrength)

                if (PlayerData.hp <= 0) {
                    PlayerData.hp = 1
                    PlayerData.dungeonWave = 1
                    dialogData = "💀 ŚMIERĆ" to "Wracasz do początku lochu"
                    spawn()
                }
            },
            modifier = Modifier.fillMaxWidth().height(64.dp)
        ) {
            Text("ATAK")
        }
    }
}

    dialogData?.let { (title, message) ->
        AlertDialog(
            onDismissRequest = { dialogData = null },
            title = { Text(title) },
            text = { Text(message) },
            confirmButton = {
                TextButton(onClick = { dialogData = null }) {
                    Text("OK")
                }
            }
        )
    }
}
