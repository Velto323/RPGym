package com.example.rpgym.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Castle
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.rpgym.R
import com.example.rpgym.data.PlayerData
import com.example.rpgym.data.PlayerRepository
import com.example.rpgym.ui.screens.BattleScreen
import com.example.rpgym.ui.screens.DungeonHubScreen
import com.example.rpgym.ui.screens.HomeScreen
import com.example.rpgym.ui.screens.MeditationScreen
import com.example.rpgym.ui.screens.PlayerScreen
import com.example.rpgym.ui.screens.QuestScreen
import com.example.rpgym.ui.screens.ShopScreen
import com.example.rpgym.ui.screens.TrainingScreen
import com.example.rpgym.ui.screens.TrophiesScreen
import com.example.rpgym.ui.theme.Black
import com.example.rpgym.ui.theme.BottomNavBackground
import com.example.rpgym.ui.theme.BottomNavItemUnselected
import com.example.rpgym.ui.theme.RpgGold

private enum class Tab(val label: String, val icon: ImageVector) {
    HOME("Home", Icons.Filled.Home),
    TRAINING("Trening", Icons.Filled.FitnessCenter),
    DUNGEON("Loch", Icons.Filled.Castle),
    QUESTS("Questy", Icons.AutoMirrored.Filled.List),
    PLAYER("Gracz", Icons.Filled.Person),
}

private sealed interface Overlay {
    data class Battle(val zone: Int) : Overlay
    data object Meditation : Overlay
    data object Shop : Overlay
    data object Trophies : Overlay
}

@Composable
fun MainScreen(
    repository: PlayerRepository,
    userId: String,
    onLoggedOut: () -> Unit,
) {
    var loading by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        repository.loadPlayer(userId)
        loading = false
    }

    var tab by remember { mutableStateOf(Tab.HOME) }
    var overlay by remember { mutableStateOf<Overlay?>(null) }

    BackHandler(enabled = overlay != null) { overlay = null }

    Box(Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.main_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )

        if (loading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Scaffold(
                containerColor = Color.Transparent,
                contentColor = Color.White,
                bottomBar = {
                    Column {
                        // HP bar sits directly above the navigation bar.
                        HpHud()
                        NavigationBar(containerColor = BottomNavBackground) {
                            Tab.entries.forEach { entry ->
                                NavigationBarItem(
                                    selected = overlay == null && tab == entry,
                                    onClick = {
                                        tab = entry
                                        overlay = null
                                    },
                                    icon = { Icon(entry.icon, contentDescription = entry.label) },
                                    label = { Text(entry.label) },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = Black,
                                        selectedTextColor = RpgGold,
                                        indicatorColor = RpgGold,
                                        unselectedIconColor = BottomNavItemUnselected,
                                        unselectedTextColor = BottomNavItemUnselected,
                                    ),
                                )
                            }
                        }
                    }
                },
            ) { padding ->
                Box(Modifier.padding(padding).fillMaxSize()) {
                    when (val current = overlay) {
                        is Overlay.Battle -> BattleScreen(current.zone)
                        Overlay.Meditation -> MeditationScreen()
                        Overlay.Shop -> ShopScreen(onBack = { overlay = null })
                        Overlay.Trophies -> TrophiesScreen(onBack = { overlay = null })
                        null -> when (tab) {
                            Tab.HOME -> HomeScreen()
                            Tab.TRAINING -> TrainingScreen()
                            Tab.DUNGEON -> DungeonHubScreen(
                                onEnterBattle = { overlay = Overlay.Battle(it) },
                                onMeditate = { overlay = Overlay.Meditation },
                            )
                            Tab.QUESTS -> QuestScreen()
                            Tab.PLAYER -> PlayerScreen(
                                onOpenShop = { overlay = Overlay.Shop },
                                onOpenTrophies = { overlay = Overlay.Trophies },
                                onLoggedOut = onLoggedOut,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HpHud() {
    val maxHp = PlayerData.getCurrentMaxHp()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BottomNavBackground)
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        Text("HP: ${PlayerData.hp} / $maxHp", color = Color.White)
        LinearProgressIndicator(
            progress = { if (maxHp > 0) PlayerData.hp / maxHp.toFloat() else 0f },
            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
        )
    }
}
