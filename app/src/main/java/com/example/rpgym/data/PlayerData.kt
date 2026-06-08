package com.example.rpgym.data

object PlayerData {

    // =====================
    // PLAYER STATS
    // =====================
    var level = 1
    var xp = 0

    var strengthLevel = 1
    var strengthXp = 0

    var hp = 100
    var maxHp = 100

    fun getStrength(): Int {

        var damage = strengthLevel * 5

        if (hasPowerPotion()) {
            damage += 5
        }

        return damage
    }

    fun getCurrentMaxHp(): Int {
        return if (hasLifePotion())
            maxHp + 50
        else
            maxHp
    }

    // =====================
    // QUEST / DUNGEON
    // =====================
    var completedTasks = 0

    var currentZone = 0
    var unlockedZone = 0
    var dungeonWave = 1

    var defeatedMonsters = 0
    val defeatedBosses = mutableSetOf<Int>()

    fun addBossKill(zone: Int): Boolean {

        val first = defeatedBosses.add(zone)

        if (first) {
            if (unlockedZone < DungeonRepository.zones.lastIndex) {
                unlockedZone++
            }
        }

        return first
    }

    // =====================
    // XP SYSTEM
    // =====================
    fun addXp(amount: Int) {

        xp += amount

        while (xp >= 100) {

            xp -= 100
            level++

            maxHp += 10
            hp = getCurrentMaxHp()
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
    // MEDITATION SYSTEM
    // =====================

    var meditationStartTime: Long? = null
    var lastMeditationTick: Long = System.currentTimeMillis()

    fun startMeditation() {
        if (meditationStartTime == null) {
            meditationStartTime = System.currentTimeMillis()
            lastMeditationTick = System.currentTimeMillis()
        }
    }

    fun tickMeditation() {

        val now = System.currentTimeMillis()

        val seconds =
            (now - lastMeditationTick) / 1000

        if (seconds <= 0) return

        val healPerSecond =
            if (hasLifePotion()) 0.5
            else 1.0

        val healed =
            (seconds * healPerSecond).toInt()

        hp = (hp + healed)
            .coerceAtMost(getCurrentMaxHp())

        lastMeditationTick = now
    }

    fun getTimeToFullHp(): Long {

        val missing =
            getCurrentMaxHp() - hp

        if (missing <= 0) return 0

        return missing.toLong()
    }

    // =====================
    // ELIXIRS
    // =====================

    var powerPotionEndTime = 0L
    var lifePotionEndTime = 0L

    fun hasPowerPotion(): Boolean {
        return System.currentTimeMillis() < powerPotionEndTime
    }

    fun hasLifePotion(): Boolean {
        return System.currentTimeMillis() < lifePotionEndTime
    }

    fun activatePowerPotion(): Boolean {

        if (strengthXp < 100) return false

        strengthXp -= 100

        powerPotionEndTime =
            System.currentTimeMillis() + 60 * 60 * 1000

        return true
    }

    fun activateLifePotion() {

        lifePotionEndTime =
            System.currentTimeMillis() + 60 * 60 * 1000
    }
}