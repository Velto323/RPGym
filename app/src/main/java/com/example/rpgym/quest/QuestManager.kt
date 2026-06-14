package com.example.rpgym.quest

import androidx.compose.runtime.mutableStateListOf
import com.example.rpgym.data.PlayerData

object QuestManager {

    val quests = mutableStateListOf<Quest>()

    init {
        generateQuests()
    }

    private fun generateQuests() {
        quests.clear()
        // MAIN QUESTS (bossy)
        for (i in 0..9) {
            val isDefeated = PlayerData.defeatedBosses.value.contains(i)
            quests.add(
                Quest(
                    id = "boss_$i",
                    title = "Pokonaj bossa ${i + 1}",
                    description = "Zabij bossa w lochu ${i + 1}",
                    type = QuestType.MAIN,
                    target = 1,
                    initialProgress = if (isDefeated) 1 else 0,
                    initialCompleted = isDefeated
                )
            )
        }

        // DAILY QUEST
        quests.add(
            Quest(
                id = "daily_kill",
                title = "Łowca potworów",
                description = "Zabij 10 potworów",
                type = QuestType.DAILY,
                target = 10
            )
        )
    }

    fun refreshQuests() {
        generateQuests()
    }

    fun onBossKilled(zone: Int) {

        quests.find { it.id == "boss_$zone" }?.let {
            it.progress = 1
            it.completed = true
        }

        PlayerData.completedTasks++
    }

    fun onMonsterKilled() {

        quests.find { it.id == "daily_kill" }?.let {

            if (!it.completed) {
                it.progress++

                if (it.progress >= it.target) {
                    it.completed = true
                }
            }
        }

        PlayerData.defeatedMonsters++
    }
}