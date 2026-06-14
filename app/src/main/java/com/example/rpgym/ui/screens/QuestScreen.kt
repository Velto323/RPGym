package com.example.rpgym.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rpgym.quest.QuestManager

@Composable
fun QuestScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(QuestManager.quests) { quest ->
            Text(
                text = "${quest.title}\n${quest.progress}/${quest.target} - " +
                    if (quest.completed) "✔ DONE" else "IN PROGRESS",
                fontSize = 16.sp,
                modifier = Modifier.padding(8.dp),
            )
        }
    }
}
