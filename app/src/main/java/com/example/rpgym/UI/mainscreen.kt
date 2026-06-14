package com.example.rpgym.UI

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.rpgym.R
import com.example.rpgym.data.PlayerData

@Composable
fun MainScreen() {

    val hp = PlayerData.hp
    val maxHp = PlayerData.getCurrentMaxHp()

    var selectedTab by remember {
        mutableIntStateOf(0)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        // TŁO
        Image(
            painter = painterResource(R.drawable.main_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Spacer(modifier = Modifier.height(50.dp))

            // HP HUD
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
            ) {

                Column(
                    modifier = Modifier.padding(10.dp)
                ) {

                    Text(
                        text = "❤️ HP: $hp / $maxHp",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    LinearProgressIndicator(
                        progress = {
                            hp.toFloat() / maxHp.toFloat()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(14.dp)
                            .padding(top = 6.dp)
                    )
                }
            }

            // EKRAN
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {

                when (selectedTab) {
                    0 -> HomeScreen()
                    1 -> TrainingScreen()
                    2 -> DungeonScreen()
                    3 -> QuestScreen()
                    4 -> PlayerScreen()
                }
            }

            // DOLNA NAWIGACJA
            NavigationBar {

                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.Home, null) },
                    label = { Text("Home") }
                )

                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.FitnessCenter, null) },
                    label = { Text("Trening") }
                )

                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(Icons.Default.SportsMartialArts, null) },
                    label = { Text("Lochy") }
                )

                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 },
                    icon = { Icon(Icons.Default.Assignment, null) },
                    label = { Text("Questy") }
                )

                NavigationBarItem(
                    selected = selectedTab == 4,
                    onClick = { selectedTab = 4 },
                    icon = { Icon(Icons.Default.Person, null) },
                    label = { Text("Gracz") }
                )
            }
        }
    }
}
