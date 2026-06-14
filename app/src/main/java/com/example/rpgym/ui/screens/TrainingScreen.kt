package com.example.rpgym.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rpgym.data.PlayerData

private data class TrainingAction(val label: String, val onClick: (onMessage: (String, String) -> Unit) -> Unit)

@Composable
fun TrainingScreen() {
    val context = LocalContext.current
    var dialog by remember { mutableStateOf<Pair<String, String>?>(null) }

    val showMessage: (String, String) -> Unit = { title, msg -> dialog = title to msg }

    val actions = remember {
        listOf(
            TrainingAction("Pompki +10") { _ -> PlayerData.addStrengthXp(10) },
            TrainingAction("Podciąganie +15") { _ -> PlayerData.addStrengthXp(15) },
            TrainingAction("Bieg +5") { _ -> PlayerData.addStrengthXp(5) },
            TrainingAction("Przysiady +8") { _ -> PlayerData.addStrengthXp(8) },
            TrainingAction("Eliksir życia") { onMessage ->
                if (PlayerData.activateHealthPotion()) {
                    onMessage("❤️ HP Potion", "+50% regen / bonus HP przez 1h")
                } else {
                    onMessage("Brak", "Nie masz mikstury życia")
                }
            },
            TrainingAction("Eliksir siły") { onMessage ->
                if (PlayerData.activateStrengthPotion()) {
                    onMessage("⚡ Siła", "+damage przez 1h")
                } else {
                    onMessage("Brak", "Nie masz mikstury siły")
                }
            },
        )
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
    ) {
        Text("Siła lvl ${PlayerData.strengthLevel}", fontSize = 22.sp)
        Text("${PlayerData.strengthXp}/100 XP", fontSize = 16.sp, modifier = Modifier.padding(top = 4.dp))
        LinearProgressIndicator(
            progress = { PlayerData.strengthXp / 100f },
            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(actions) { action ->
                Button(
                    onClick = {
                        action.onClick(showMessage)
                        PlayerData.save(context)
                    },
                    modifier = Modifier.fillMaxWidth().height(64.dp),
                ) {
                    Text(action.label)
                }
            }
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
