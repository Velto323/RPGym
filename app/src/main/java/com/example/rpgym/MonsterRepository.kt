package com.example.rpgym

object MonsterRepository {

    private var globalBossBuff = 0

    fun getMonster(round: Int): Monster {

        val bossIndex = (round - 1) / 5
        val isBoss = round % 5 == 0

        val names = listOf(
            "Król Goblinów" to "Goblin",
            "Wilkołak" to "Wilk",
            "Kamienny Troll" to "Magiczny Kamień",
            "Obłędny Rycerz" to "Pijany Giermek",
            "Królowa Pająków" to "Pająk",
            "Nekromanta" to "Zombie",
            "Wiedźma" to "Szkielet",
            "Gryf" to "Harpia",
            "Wampir" to "Nietoperz",
            "Smok" to "Wiwerna"
        )

        val safeIndex = bossIndex.coerceAtMost(names.lastIndex)

        val (bossName, mobName) = names[safeIndex]

        val mobHp = 10 + bossIndex * 2
        val mobAtk = 5 + bossIndex * 2

        val bossHp = 100 + bossIndex * 20
        val bossAtk = 10 + bossIndex * 5 + globalBossBuff

        return if (isBoss) {
            Monster(
                name = "👑 $bossName",
                hp = bossHp,
                strength = bossAtk,
                isBoss = true
            )
        } else {
            Monster(
                name = mobName,
                hp = mobHp,
                strength = mobAtk,
                isBoss = false
            )
        }
    }

    fun onBossDefeated() {
        globalBossBuff += 5
    }
}