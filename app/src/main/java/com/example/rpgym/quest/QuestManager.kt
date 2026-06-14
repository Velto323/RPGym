package com.example.rpgym.quest

import android.content.Context
import com.example.rpgym.data.DungeonRepository
import com.example.rpgym.data.PlayerData

object QuestManager {

    val quests = mutableListOf<Quest>()

    private val listeners = mutableSetOf<() -> Unit>()

    fun addListener(l: () -> Unit) {
        listeners.add(l)
    }

    fun removeListener(l: () -> Unit) {
        listeners.remove(l)
    }

    private fun notifyChange() {
        listeners.forEach { it.invoke() }
    }

    init {
        generateQuests()
    }

    private fun generateQuests() {

        // MAIN QUESTS (bossy)
        DungeonRepository.zones.forEachIndexed { i, zone ->
            quests.add(
                Quest(
                    id = "boss_$i",
                    title = "Pokonaj bossa ${zone.bossName}",
                    description = "Zabij bossa w lochu ${zone.name}",
                    type = QuestType.MAIN,
                    target = 1
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

    fun onBossKilled(zone: Int) {

        quests.find { it.id == "boss_$zone" }?.let {
            it.progress = 1
            it.completed = true
        }

        PlayerData.completedTasks++
        notifyChange()
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
        notifyChange()
    }

    fun save(context: Context) {
        val p = context.getSharedPreferences("quests", Context.MODE_PRIVATE)
        val editor = p.edit()
        
        quests.forEach { quest ->
            editor.putInt("${quest.id}_progress", quest.progress)
            editor.putBoolean("${quest.id}_completed", quest.completed)
        }
        
        editor.apply()
    }

    fun load(context: Context) {
        val p = context.getSharedPreferences("quests", Context.MODE_PRIVATE)
        
        quests.forEach { quest ->
            quest.progress = p.getInt("${quest.id}_progress", 0)
            quest.completed = p.getBoolean("${quest.id}_completed", false)
        }
        
        notifyChange()
    }
}