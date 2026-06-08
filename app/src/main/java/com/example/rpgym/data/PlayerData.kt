package com.example.rpgym.data

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
    // QUEST SYSTEM
    // =====================
    var bossQuestProgress = 0
    var bossQuestTarget = 10

    var completedTasks = 0


    // =====================
    // MEDITATION SYSTEM (HP REGEN + TIMER)
    // =====================
    var meditationStartTime: Long? = null

    private const val REGEN_PER_SEC = 1

    fun startMeditation() {
        meditationStartTime = System.currentTimeMillis()
    }

    fun tickMeditation() {
        val start = meditationStartTime ?: return

        val seconds = (System.currentTimeMillis() - start) / 1000
        val healed = (seconds * REGEN_PER_SEC).toInt()

        hp = (hp + healed).coerceAtMost(maxHp)
    }

    fun getTimeToFullHp(): Long {

        val missing = maxHp - hp
        if (missing <= 0) return 0

        val start = meditationStartTime ?: return missing.toLong()

        val elapsed = (System.currentTimeMillis() - start) / 1000
        val remaining = missing - elapsed

        return remaining.coerceAtLeast(0)
    }


    // =====================
    // BOSS LOGIC
    // =====================
    fun addBossKill(zone: Int): Boolean {

        val firstTime = defeatedBosses.add(zone)

        if (firstTime) {
            bossQuestProgress++

            if (unlockedZone < 9) {
                unlockedZone++
            }
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
    // UI EVENT SYSTEM
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