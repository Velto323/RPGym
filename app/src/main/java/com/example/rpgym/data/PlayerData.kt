package com.example.rpgym.data

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object PlayerData {

    // ================= PLAYER =================
    // Fields are Compose-observable: reading them inside a @Composable subscribes
    // that composable to recomposition whenever the value changes.
    var level by mutableStateOf(1)
    var xp by mutableStateOf(0)

    var strengthLevel by mutableStateOf(1)
    var strengthXp by mutableStateOf(0)

    var hp by mutableStateOf(100)
    var maxHp by mutableStateOf(100)

    var gold by mutableStateOf(0)
    var completedTasks by mutableStateOf(0)
    var defeatedMonsters by mutableStateOf(0)

    // FINAL DAMAGE (ZAMIAST getStrength())
    val strength: Int
        get() = strengthLevel * 5 + if (hasPowerPotion()) 5 else 0


    // ================= DUNGEON =================
    var currentZone by mutableStateOf(0)
    var unlockedZone by mutableStateOf(0)
    var dungeonWave by mutableStateOf(1)

    val defeatedBosses = mutableSetOf<Int>()


    // ================= MEDITATION =================
    var meditationStartTime: Long? = null
    var lastMeditationTick: Long = System.currentTimeMillis()


    // ================= LISTENERS =================
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


    // ================= HP =================

    fun damage(amount: Int) {
        hp = (hp - amount).coerceAtLeast(0)
        notifyChange()
    }

    fun heal(amount: Int) {
        hp = (hp + amount).coerceAtMost(getCurrentMaxHp())
        notifyChange()
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

        notifyChange()
    }

    fun addStrengthXp(amount: Int) {
        strengthXp += amount

        while (strengthXp >= 100) {
            strengthXp -= 100
            strengthLevel++
        }

        notifyChange()
    }


    // ================= GOLD =================

    fun addGold(amount: Int) {
        gold += amount
        notifyChange()
    }

    fun spendGold(amount: Int): Boolean {
        if (gold < amount) return false
        gold -= amount
        notifyChange()
        return true
    }


    // ================= DUNGEON =================

    fun addBossKill(zone: Int): Boolean {
        val first = defeatedBosses.add(zone)

        if (first && unlockedZone < 9) {
            unlockedZone++
        }

        notifyChange()
        return first
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

        notifyChange()
    }

    fun getTimeToFullHp(): Long {
        val missing = getCurrentMaxHp() - hp
        if (missing <= 0) return 0
        return missing.toLong()
    }


    // ================= POTIONS =================
    var healthPotions by mutableStateOf(0)
    var strengthPotions by mutableStateOf(0)

    var powerPotionEndTime by mutableStateOf(0L)
    var lifePotionEndTime by mutableStateOf(0L)

    private const val POTION_DURATION = 60 * 60 * 1000L

    fun buyHealthPotion(): Boolean {
        if (gold < 20) return false
        gold -= 20
        healthPotions++
        notifyChange()
        return true
    }

    fun buyStrengthPotion(): Boolean {
        if (gold < 20) return false
        gold -= 20
        strengthPotions++
        notifyChange()
        return true
    }

    // aktywacja (1 aktywna na raz)
    fun activateHealthPotion(): Boolean {
        if (healthPotions <= 0) return false

        healthPotions--
        lifePotionEndTime = System.currentTimeMillis() + POTION_DURATION

        notifyChange()
        return true
    }

    fun activateStrengthPotion(): Boolean {
        if (strengthPotions <= 0) return false

        strengthPotions--
        powerPotionEndTime = System.currentTimeMillis() + POTION_DURATION

        notifyChange()
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
}