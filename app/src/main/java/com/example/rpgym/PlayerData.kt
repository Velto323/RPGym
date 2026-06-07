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