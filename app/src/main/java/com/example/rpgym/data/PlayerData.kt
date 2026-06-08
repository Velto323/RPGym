package com.example.rpgym.data

object PlayerData {

    // PLAYER
    var level = 1
    var xp = 0

    var strengthLevel = 1
    var strengthXp = 0

    var hp = 100
    var maxHp = 100

    var gold = 0

    var healthPotions = 0
    var strengthPotions = 0

    const val HEALTH_POTION_PRICE = 20
    const val STRENGTH_POTION_PRICE = 20

    fun addGold(amount: Int) {
        gold += amount
    }

    fun spendGold(amount: Int): Boolean {
        if (gold < amount) return false
        gold -= amount
        return true
    }

    fun buyHealthPotion(): Boolean {
        if (!spendGold(HEALTH_POTION_PRICE)) return false
        healthPotions++
        return true
    }

    fun buyStrengthPotion(): Boolean {
        if (!spendGold(STRENGTH_POTION_PRICE)) return false
        strengthPotions++
        return true
    }

    fun getStrength(): Int = strengthLevel * 5

    //QUEST
    var completedTasks = 0

    // DUNGEON
    var currentZone = 0
    var unlockedZone = 0
    var dungeonWave = 1

    var defeatedMonsters = 0
    val defeatedBosses = mutableSetOf<Int>()

    // MEDITATION
    var meditationStartTime: Long? = null
    private const val REGEN_PER_SEC = 1

    fun startMeditation() {
        if (meditationStartTime == null) {
            meditationStartTime = System.currentTimeMillis()
        }
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
        return (missing - elapsed).coerceAtLeast(0)
    }

    // BOSS UNLOCK
    fun addBossKill(zone: Int): Boolean {
        val first = defeatedBosses.add(zone)

        if (first) {
            if (unlockedZone < DungeonRepository.zones.lastIndex) {
                unlockedZone++
            }
        }

        return first
    }

    // XP
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
}