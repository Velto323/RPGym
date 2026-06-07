package com.example.rpgym

object PlayerData {

    // =====================
    // LEVEL / XP
    // =====================
    var level = 1
    var xp = 0

    fun addXp(amount: Int) {
        xp += amount

        while (xp >= 100) {
            xp -= 100
            level++
        }
    }

    // =====================
    // STRENGTH SYSTEM
    // =====================
    var strengthLevel = 1
    var strengthXp = 0

    fun addStrengthXp(amount: Int) {
        strengthXp += amount

        while (strengthXp >= 100) {
            strengthXp -= 100
            strengthLevel++
        }
    }

    fun getStrength(): Int {
        return strengthLevel * 5
    }

    // =====================
    // HP SYSTEM
    // =====================
    var hp = 100
    var maxHp = 100

    fun resetHp() {
        hp = maxHp
    }

    // =====================
    // WORLD / ZONES
    // =====================
    var currentZone = 0
    var unlockedZone = 0

    // =====================
    // PROGRESS TRACKING
    // =====================
    var defeatedMonsters = 0
    var completedTasks = 0

    // =====================
    // BOSSES / QUEST SYSTEM
    // =====================
    val defeatedBosses = mutableSetOf<Int>()

    var bossQuestProgress = 0
    var bossQuestTarget = 10

    fun addBossKill(zone: Int) {
        bossQuestProgress++
        defeatedBosses.add(zone)

        if (unlockedZone < 9) {
            unlockedZone++
        }
    }

    // =====================
    // OFFLINE / TIME (future medytacja)
    // =====================
    var lastOnlineTime: Long = System.currentTimeMillis()

    // =====================
    // EVENT SYSTEM (UI SYNC FIX)
    // =====================
    private val listeners = mutableListOf<() -> Unit>()

    fun addListener(listener: () -> Unit) {
        listeners.add(listener)
    }

    fun notifyChange() {
        listeners.forEach { it.invoke() }
    }
}