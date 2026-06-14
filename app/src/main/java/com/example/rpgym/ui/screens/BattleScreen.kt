package com.example.rpgym.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rpgym.data.DungeonRepository
import com.example.rpgym.data.Monster
import com.example.rpgym.data.PlayerData
import com.example.rpgym.quest.QuestManager

private fun spawnMonster(zoneIndex: Int): Monster {
    val zone = DungeonRepository.zones[zoneIndex]
    val isBoss = PlayerData.dungeonWave % 5 == 0
    return Monster(
        name = if (isBoss) zone.bossName else zone.mobName,
        hp = if (isBoss) zone.bossHp else zone.mobHp,
        strength = if (isBoss) zone.bossAttack else zone.mobAttack,
        isBoss = isBoss,
    )
}

@Composable
fun BattleScreen(zoneIndex: Int) {
    var monster by remember { mutableStateOf(spawnMonster(zoneIndex)) }
    var dialog by remember { mutableStateOf<Pair<String, String>?>(null) }

    val zone = DungeonRepository.zones[zoneIndex]

    fun attack() {
        var current = monster.copy(hp = monster.hp - PlayerData.strength)

        if (current.hp <= 0) {
            QuestManager.onMonsterKilled()

            if (current.isBoss) {
                PlayerData.addXp((zoneIndex + 1) * 25)
                val firstTime = PlayerData.addBossKill(zoneIndex)
                dialog = "👑 BOSS POKONANY" to
                    if (firstTime) "Odblokowano nową lokację!\n+${(zoneIndex + 1) * 25} XP"
                    else "+${(zoneIndex + 1) * 25} XP"
            }

            PlayerData.dungeonWave++
            current = spawnMonster(zoneIndex)
        }

        // monster strikes back
        PlayerData.damage(current.strength)

        if (PlayerData.hp <= 0) {
            PlayerData.hp = 1
            PlayerData.dungeonWave = 1
            dialog = "💀 ŚMIERĆ" to "Wracasz do początku lochu"
            current = spawnMonster(zoneIndex)
        }

        monster = current
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(zone.name, fontSize = 24.sp)
        Text("Fala: ${PlayerData.dungeonWave}", fontSize = 18.sp, modifier = Modifier.padding(top = 8.dp))
        Text("${monster.name}", fontSize = 18.sp, modifier = Modifier.padding(top = 16.dp))
        Text("HP: ${monster.hp}", fontSize = 18.sp, modifier = Modifier.padding(top = 4.dp))

        Button(onClick = { attack() }, modifier = Modifier.padding(top = 24.dp)) {
            Text("Atakuj")
        }
    }

    dialog?.let { (title, message) ->
        AlertDialog(
            onDismissRequest = { dialog = null },
            confirmButton = { TextButton(onClick = { dialog = null }) { Text("OK") } },
            title = { Text(title) },
            text = { Text(message) },
        )
    }
}
