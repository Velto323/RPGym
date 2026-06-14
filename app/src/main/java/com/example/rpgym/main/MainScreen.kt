package com.example.rpgym.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.rpgym.R
import com.example.rpgym.data.PlayerData
import com.example.rpgym.dung.BattleScreen
import com.example.rpgym.dung.DungeonHubScreen
import com.example.rpgym.dung.MeditationScreen
import com.example.rpgym.fragments.PlayerScreen
import com.example.rpgym.fragments.ShopScreen
import com.example.rpgym.fragments.TrophiesScreen
import com.example.rpgym.fragments.TrainingScreen
import com.example.rpgym.home.HomeScreen
import com.example.rpgym.quest.QuestScreen

sealed class Screen(val route: String, val title: String, val icon: @Composable () -> Unit) {
    object Home : Screen("home", "Home", { Icon(Icons.Default.Home, contentDescription = "Home") })
    object Training : Screen("training", "Trening", { Icon(Icons.Default.Build, contentDescription = "Trening") })
    object Dungeon : Screen("dungeon", "Loch", { Icon(Icons.Default.Lock, contentDescription = "Loch") })
    object Quests : Screen("quests", "Questy", { Icon(Icons.Default.List, contentDescription = "Questy") })
    object Player : Screen("player", "Gracz", { Icon(Icons.Default.Person, contentDescription = "Gracz") })
    object Shop : Screen("shop", "Sklep", {})
    object Trophies : Screen("trophies", "Trofea", {})
    object Battle : Screen("battle/{zoneId}", "Walka", {})
    object Meditation : Screen("meditation", "Medytacja", {})
}

@Composable
fun MainScreen(
    onLogout: () -> Unit
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigation(navController)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.main_background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.fillMaxSize()) {
                // Global HP HUD
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "HP: ${PlayerData.hp}/${PlayerData.getCurrentMaxHp()}",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                    LinearProgressIndicator(
                        progress = { PlayerData.hp.toFloat() / PlayerData.getCurrentMaxHp().toFloat() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(14.dp)
                            .padding(top = 6.dp),
                        color = Color.Red,
                        trackColor = Color.Gray
                    )
                }

                NavHost(
                    navController = navController,
                    startDestination = Screen.Home.route,
                    modifier = Modifier.weight(1f)
                ) {
                    composable(Screen.Home.route) { HomeScreen() }
                    composable(Screen.Training.route) { TrainingScreen() }
                    composable(Screen.Dungeon.route) { 
                        DungeonHubScreen(
                            onEnterDungeon = { zoneId ->
                                PlayerData.currentZone = zoneId
                                PlayerData.dungeonWave = 1
                                navController.navigate(Screen.Battle.route.replace("{zoneId}", zoneId.toString()))
                            },
                            onMeditation = { navController.navigate(Screen.Meditation.route) }
                        )
                    }
                    composable(Screen.Quests.route) { QuestScreen() }
                    composable(Screen.Player.route) { 
                        PlayerScreen(
                            onShopClick = { navController.navigate(Screen.Shop.route) },
                            onTrophiesClick = { navController.navigate(Screen.Trophies.route) },
                            onLogoutClick = onLogout
                        )
                    }
                    composable(Screen.Shop.route) {
                        ShopScreen(onBack = { navController.popBackStack() })
                    }
                    composable(Screen.Trophies.route) {
                        TrophiesScreen(onBack = { navController.popBackStack() })
                    }
                    composable(Screen.Battle.route) { backStackEntry ->
                        val zoneId = backStackEntry.arguments?.getString("zoneId")?.toIntOrNull() ?: 0
                        BattleScreen(zoneIndex = zoneId)
                    }
                    composable(Screen.Meditation.route) {
                        MeditationScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigation(navController: NavHostController) {
    val items = listOf(
        Screen.Home,
        Screen.Training,
        Screen.Dungeon,
        Screen.Quests,
        Screen.Player
    )
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { screen ->
            NavigationBarItem(
                icon = screen.icon,
                label = { Text(screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
