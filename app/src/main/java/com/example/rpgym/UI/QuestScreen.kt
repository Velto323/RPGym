package com.example.rpgym.UI

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rpgym.quest.QuestManager

@Composable
fun QuestScreen() {
    var questsUpdateTrigger by remember { mutableIntStateOf(0) }
    val quests = QuestManager.quests

    DisposableEffect(Unit) {
        val listener: () -> Unit = {
            questsUpdateTrigger++
        }
        QuestManager.addListener(listener)
        onDispose { QuestManager.removeListener(listener) }
    }

    key(questsUpdateTrigger) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(quests) { quest ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = quest.title, fontSize = 18.sp, style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${quest.progress}/${quest.target} - ${if (quest.completed) "✔ DONE" else "IN PROGRESS"}",
                            fontSize = 14.sp,
                            color = if (quest.completed) Color.Green else Color.Gray
                        )
                    }
                }
            }
        }
    }
}
