package com.example.rpgym.data

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object PlayerData {

    // ================= PLAYER =================
    var level by mutableIntStateOf(1)
    var xp by mutableIntStateOf(0)

    var strengthLevel by mutableIntStateOf(1)
    var strengthXp by mutableIntStateOf(0)

    var hp by mutableIntStateOf(100)
    var maxHp by mutableIntStateOf(100)

    var gold by mutableIntStateOf(0)
    var completedTasks by mutableIntStateOf(0)
    var defeatedMonsters by mutableIntStateOf(0)

    val strength: Int
        get() = strengthLevel * 5 + if (hasPowerPotion()) 5 else 0


    // ================= DUNGEON =================
    var currentZone by mutableIntStateOf(0)
    var unlockedZone by mutableIntStateOf(0)
    var dungeonWave by mutableIntStateOf(1)

    val defeatedBosses = mutableStateOf(setOf<Int>())


    // ================= MEDITATION =================
    var meditationStartTime by mutableStateOf<Long?>(null)
    var lastMeditationTick by mutableLongStateOf(System.currentTimeMillis())


    // ================= HP =================

    fun damage(amount: Int) {
        hp = (hp - amount).coerceAtLeast(0)
    }

    fun heal(amount: Int) {
        hp = (hp + amount).coerceAtMost(getCurrentMaxHp())
    }

    fun getCurrentMaxHp(): Int {
        return if (hasLifePotion()) maxHp + 50 else maxHp
    }


    // ================= XP =================

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


    // ================= GOLD =================

    fun addGold(amount: Int) {
        gold += amount
    }

    fun spendGold(amount: Int): Boolean {
        if (gold < amount) return false
        gold -= amount
        return true
    }


    // ================= DUNGEON =================

    fun addBossKill(zone: Int): Boolean {
        val current = defeatedBosses.value
        if (zone in current) return false
        
        defeatedBosses.value = current + zone

        if (unlockedZone < 9) {
            unlockedZone++
        }

        return true
    }


    // ================= MEDITATION =================

    fun startMeditation() {
        if (meditationStartTime == null) {
            meditationStartTime = System.currentTimeMillis()
            lastMeditationTick = System.currentTimeMillis()
        }
    }

    fun tickMeditation() {

        val now = System.currentTimeMillis()
        val seconds = (now - lastMeditationTick) / 1000

        if (seconds <= 0) return

        val healPerSecond =
            if (hasLifePotion()) 0.5 else 1.0

        val healed = (seconds * healPerSecond).toInt()

        hp = (hp + healed).coerceAtMost(getCurrentMaxHp())

        lastMeditationTick = now
    }

    fun getTimeToFullHp(): Long {
        val missing = getCurrentMaxHp() - hp
        if (missing <= 0) return 0
        return missing.toLong()
    }


    // ================= POTIONS =================
    var healthPotions by mutableIntStateOf(0)
    var strengthPotions by mutableIntStateOf(0)

    var powerPotionEndTime by mutableLongStateOf(0L)
    var lifePotionEndTime by mutableLongStateOf(0L)

    private const val POTION_DURATION = 60 * 60 * 1000L

    fun buyHealthPotion(): Boolean {
        if (gold < 20) return false
        gold -= 20
        healthPotions++
        return true
    }

    fun buyStrengthPotion(): Boolean {
        if (gold < 20) return false
        gold -= 20
        strengthPotions++
        return true
    }

    // aktywacja (1 aktywna na raz)
    fun activateHealthPotion(): Boolean {
        if (healthPotions <= 0) return false

        healthPotions--
        lifePotionEndTime = System.currentTimeMillis() + POTION_DURATION

        return true
    }

    fun activateStrengthPotion(): Boolean {
        if (strengthPotions <= 0) return false

        strengthPotions--
        powerPotionEndTime = System.currentTimeMillis() + POTION_DURATION

        return true
    }

    fun hasPowerPotion(): Boolean =
        System.currentTimeMillis() < powerPotionEndTime

    fun hasLifePotion(): Boolean =
        System.currentTimeMillis() < lifePotionEndTime


    // ================= SAVE / LOAD =================

    fun save(context: Context) {
        val p = context.getSharedPreferences("player", Context.MODE_PRIVATE)

        p.edit()
            .putInt("level", level)
            .putInt("xp", xp)

            .putInt("strengthLevel", strengthLevel)
            .putInt("strengthXp", strengthXp)

            .putInt("hp", hp)
            .putInt("maxHp", maxHp)

            .putInt("gold", gold)
            .putInt("completedTasks", completedTasks)
            .putInt("defeatedMonsters", defeatedMonsters)

            .putInt("currentZone", currentZone)
            .putInt("unlockedZone", unlockedZone)
            .putInt("dungeonWave", dungeonWave)

            .putLong("powerPotionEndTime", powerPotionEndTime)
            .putLong("lifePotionEndTime", lifePotionEndTime)

            .apply()
    }

    fun load(context: Context) {
        val p = context.getSharedPreferences("player", Context.MODE_PRIVATE)

        level = p.getInt("level", 1)
        xp = p.getInt("xp", 0)

        strengthLevel = p.getInt("strengthLevel", 1)
        strengthXp = p.getInt("strengthXp", 0)

        hp = p.getInt("hp", 100)
        maxHp = p.getInt("maxHp", 100)

        gold = p.getInt("gold", 0)
        completedTasks = p.getInt("completedTasks", 0)
        defeatedMonsters = p.getInt("defeatedMonsters", 0)

        currentZone = p.getInt("currentZone", 0)
        unlockedZone = p.getInt("unlockedZone", 0)
        dungeonWave = p.getInt("dungeonWave", 1)

        powerPotionEndTime = p.getLong("powerPotionEndTime", 0L)
        lifePotionEndTime = p.getLong("lifePotionEndTime", 0L)
    }

    fun reset() {
        level = 1
        xp = 0
        strengthLevel = 1
        strengthXp = 0
        hp = 100
        maxHp = 100
        gold = 0
        completedTasks = 0
        defeatedMonsters = 0
        currentZone = 0
        unlockedZone = 0
        dungeonWave = 1
        defeatedBosses.value = emptySet()
        meditationStartTime = null
        lastMeditationTick = System.currentTimeMillis()
        healthPotions = 0
        strengthPotions = 0
        powerPotionEndTime = 0L
        lifePotionEndTime = 0L
    }

    // Compat for old listener system if needed, but better to remove
    fun notifyChange() {}
    fun addListener(l: () -> Unit) {}
    fun removeListener(l: () -> Unit) {}
}
