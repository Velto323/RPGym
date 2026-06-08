package com.example.rpgym.quest

enum class QuestType {
    MAIN,
    DAILY
}

data class Quest(
    val id: String,
    val title: String,
    val description: String,
    val type: QuestType,
    val target: Int,
    var progress: Int = 0,
    var completed: Boolean = false
)