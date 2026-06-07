package com.example.rpgym

object PlayerData {

    var level = 1
    var xp = 0

    var hp = 100
    var maxHp = 100

    var lastOnlineTime: Long = System.currentTimeMillis()

    var strengthLevel = 1
    var strengthXp = 0

    var completedTasks = 0
    var defeatedMonsters = 0

    var currentZone = 0
    var unlockedZone = 0

    // indeksy pokonanych bossów
    val defeatedBosses = mutableSetOf<Int>()

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

    fun getStrength(): Int {
        return strengthLevel * 5
    }
}