package com.example.rpgym.quest

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

enum class QuestType {
    MAIN,
    DAILY
}

class Quest(
    val id: String,
    val title: String,
    val description: String,
    val type: QuestType,
    val target: Int,
    initialProgress: Int = 0,
    initialCompleted: Boolean = false
) {
    var progress by mutableIntStateOf(initialProgress)
    var completed by mutableStateOf(initialCompleted)
}
