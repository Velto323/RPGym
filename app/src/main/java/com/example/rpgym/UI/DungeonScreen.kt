package com.example.rpgym.UI

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rpgym.data.DungeonRepository
import com.example.rpgym.data.PlayerData
import com.example.rpgym.quest.QuestManager
import kotlinx.coroutines.delay

@Composable
fun DungeonScreen() {
    var screenState by remember { mutableStateOf("hub") } // hub, battle, meditation
    var selectedZoneIndex by remember { mutableIntStateOf(PlayerData.currentZone) }

    when (screenState) {
        "hub" -> DungeonHub(
            onEnterDungeon = {
                PlayerData.currentZone = selectedZoneIndex
                PlayerData.dungeonWave = 1
                screenState = "battle"
            },
            onMeditation = {
                PlayerData.startMeditation()
                screenState = "meditation"
            },
            selectedZoneIndex = selectedZoneIndex,
            onZoneChange = { 
                selectedZoneIndex = it 
                PlayerData.currentZone = it
            }
        )
        "battle" -> BattleScreen(
            zoneIndex = selectedZoneIndex,
            onBack = { screenState = "hub" }
        )
        "meditation" -> MeditationScreen(
            onBack = { screenState = "hub" }
        )
    }
}

@Composable
fun DungeonHub(
    onEnterDungeon: () -> Unit,
    onMeditation: () -> Unit,
    selectedZoneIndex: Int,
    onZoneChange: (Int) -> Unit
) {
    val zone = DungeonRepository.zones[selectedZoneIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(selectedZoneIndex) {
                var totalDrag = 0f
                detectHorizontalDragGestures(
                    onDragEnd = {
                        if (totalDrag > 100) { // Swipe Right -> Prev
                            val prev = if (selectedZoneIndex > 0) selectedZoneIndex - 1 else PlayerData.unlockedZone
                            onZoneChange(prev)
                        } else if (totalDrag < -100) { // Swipe Left -> Next
                            val next = if (selectedZoneIndex < PlayerData.unlockedZone) selectedZoneIndex + 1 else 0
                            onZoneChange(next)
                        }
                    },
                    onHorizontalDrag = { change, dragAmount ->
                        change.consume()
                        totalDrag += dragAmount
                    }
                )
            }
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = zone.name, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = zone.description, fontSize = 18.sp, color = Color.LightGray, textAlign = TextAlign.Center)
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(onClick = onEnterDungeon, modifier = Modifier.fillMaxWidth()) {
            Text("Wejdź do lochu")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(onClick = onMeditation, modifier = Modifier.fillMaxWidth()) {
            Text("Medytacja")
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = "← Przesuń, aby zmienić lokację →", color = Color.Gray)
    }
}

@Composable
fun BattleScreen(zoneIndex: Int, onBack: () -> Unit) {
    val zone = DungeonRepository.zones[zoneIndex]
    var dungeonWave by remember { mutableIntStateOf(PlayerData.dungeonWave) }
    var playerHp by remember { mutableIntStateOf(PlayerData.hp) }
    
    val isBoss = dungeonWave % 5 == 0
    val monsterName = if (isBoss) zone.bossName else zone.mobName
    val monsterMaxHp = if (isBoss) zone.bossHp else zone.mobHp
    val monsterStrength = if (isBoss) zone.bossAttack else zone.mobAttack
    
    var monsterHp by remember(dungeonWave) { mutableIntStateOf(monsterMaxHp) }
    var showDeathDialog by remember { mutableStateOf(false) }
    var showBossDialog by remember { mutableStateOf(false) }

    if (showDeathDialog) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("💀 ŚMIERĆ") },
            text = { Text("Wracasz do początku lochu") },
            confirmButton = {
                Button(onClick = {
                    showDeathDialog = false
                    onBack()
                }) { Text("OK") }
            }
        )
    }

    if (showBossDialog) {
        AlertDialog(
            onDismissRequest = { showBossDialog = false },
            title = { Text("👑 BOSS POKONANY") },
            text = { Text("Odblokowano nową lokację!\n+${(zoneIndex + 1) * 25} XP") },
            confirmButton = {
                Button(onClick = { showBossDialog = false }) { Text("OK") }
            }
        )
    }

    DisposableEffect(Unit) {
        val listener = {
            playerHp = PlayerData.hp
            dungeonWave = PlayerData.dungeonWave
        }
        PlayerData.addListener(listener)
        onDispose { PlayerData.removeListener(listener) }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = zone.name, fontSize = 24.sp, color = Color.White)
        Text(text = "Fala: $dungeonWave", fontSize = 18.sp, color = Color.LightGray)
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(text = monsterName, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.Red)
        Text(text = "HP: $monsterHp / $monsterMaxHp", fontSize = 20.sp, color = Color.White)
        
        LinearProgressIndicator(
            progress = { monsterHp.toFloat() / monsterMaxHp.toFloat() },
            modifier = Modifier.fillMaxWidth().height(16.dp).padding(vertical = 8.dp),
            color = Color.Red
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = {
                monsterHp -= PlayerData.strength
                if (monsterHp <= 0) {
                    monsterHp = 0
                    QuestManager.onMonsterKilled()
                    if (isBoss) {
                        QuestManager.onBossKilled(zoneIndex) // Update quest state
                        PlayerData.addXp((zoneIndex + 1) * 25)
                        if (PlayerData.addBossKill(zoneIndex)) {
                            showBossDialog = true
                        }
                    }
                    PlayerData.dungeonWave++
                    dungeonWave = PlayerData.dungeonWave
                } else {
                    PlayerData.damage(monsterStrength)
                    if (PlayerData.hp <= 0) {
                        PlayerData.hp = 1
                        PlayerData.dungeonWave = 1
                        dungeonWave = 1
                        showDeathDialog = true
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().height(64.dp)
        ) {
            Text("ATAKUJ")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        TextButton(onClick = onBack) {
            Text("Uciekaj", color = Color.White)
        }
    }
}

@Composable
fun MeditationScreen(onBack: () -> Unit) {
    var playerHp by remember { mutableIntStateOf(PlayerData.hp) }
    var maxHp by remember { mutableIntStateOf(PlayerData.getCurrentMaxHp()) }
    var timeToFull by remember { mutableLongStateOf(PlayerData.getTimeToFullHp()) }

    DisposableEffect(Unit) {
        val listener = {
            playerHp = PlayerData.hp
            maxHp = PlayerData.getCurrentMaxHp()
            timeToFull = PlayerData.getTimeToFullHp()
        }
        PlayerData.addListener(listener)
        onDispose { PlayerData.removeListener(listener) }
    }

    LaunchedEffect(Unit) {
        while (true) {
            PlayerData.tickMeditation()
            delay(1000)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Medytacja", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.Cyan)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "HP: $playerHp / $maxHp", fontSize = 24.sp, color = Color.White)
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = if (timeToFull <= 0) "FULL HP ✔" else "Do pełnego HP: ${timeToFull}s",
            fontSize = 18.sp,
            color = Color.LightGray
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(onClick = onBack) {
            Text("Przerwij medytację")
        }
    }
}
