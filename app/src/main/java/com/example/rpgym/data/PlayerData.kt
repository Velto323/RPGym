package com.example.rpgym

object PlayerData {

    // =====================
    // PLAYER
    // =====================
    var level = 1
    var xp = 0

    var strengthLevel = 1
    var strengthXp = 0

    var hp = 100
    var maxHp = 100

    fun getStrength(): Int = strengthLevel * 5

    // =====================
    // DUNGEON
    // =====================
    var currentZone = 0
    var unlockedZone = 0
    var dungeonWave = 1

    var defeatedMonsters = 0
    val defeatedBosses = mutableSetOf<Int>()

    // =====================
    // QUESTS
    // =====================
    var bossQuestProgress = 0
    var bossQuestTarget = 10

    var completedTasks = 0

    // =====================
    // MEDITATION (PASSIVE HEAL)
    // =====================
    var lastMeditationTick: Long = System.currentTimeMillis()

    fun meditateTick() {
        val now = System.currentTimeMillis()
        val seconds = (now - lastMeditationTick) / 1000

        if (seconds > 0) {
            hp += seconds.toInt()
            if (hp > maxHp) hp = maxHp
            lastMeditationTick = now
        }
    }

    // =====================
    // BOSS LOGIC
    // =====================
    fun addBossKill(zone: Int): Boolean {

        val firstTime = defeatedBosses.add(zone)

        if (firstTime) {
            bossQuestProgress++
            if (unlockedZone < 9) unlockedZone++
        }

        return firstTime
    }

    // =====================
    // LEVEL SYSTEM
    // =====================
    fun addXp(amount: Int) {
        xp += amount
        while (xp >= 100) {
            xp -= 100
            level++
        }
    }

    fun addStrengthXp(amount: Int) {
        strengthXp += amount
        while (strengthXp >= 100) {
            strengthXp -= 100
            strengthLevel++
        }
    }

    // =====================
    // UI UPDATE SYSTEM
    // =====================
    private val listeners = mutableSetOf<() -> Unit>()

    fun addListener(l: () -> Unit) {
        listeners.add(l)
    }

    fun removeListener(l: () -> Unit) {
        listeners.remove(l)
    }

    fun notifyChange() {
        listeners.forEach { it.invoke() }
    }
}