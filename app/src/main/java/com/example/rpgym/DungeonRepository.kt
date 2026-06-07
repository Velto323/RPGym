package com.example.rpgym

object DungeonRepository {

    val zones = listOf(

        DungeonZone(
            "Las Goblinów",
            "Początek przygody",
            "Goblin",
            "Król Goblinów",
            10, 5,
            100, 10,
            0
        ),

        DungeonZone(
            "Mroczny Las",
            "Pełen wilków",
            "Wilk",
            "Wilkołak",
            12, 10,
            120, 15,
            1
        ),

        DungeonZone(
            "Kamienny Kanion",
            "Kamienne potwory",
            "Magiczny Kamień",
            "Kamienny Troll",
            14, 15,
            140, 20,
            2
        ),

        DungeonZone(
            "Zrujnowany Zamek",
            "Upadli rycerze",
            "Pijany Giermek",
            "Obłędny Rycerz",
            16, 20,
            160, 25,
            3
        ),

        DungeonZone(
            "Pajęcza Grota",
            "Pełna pająków",
            "Pająk",
            "Królowa Pająków",
            18, 25,
            180, 30,
            4
        ),

        DungeonZone(
            "Cmentarz Umarłych",
            "Nekromancja",
            "Zombie",
            "Nekromanta",
            20, 30,
            200, 35,
            5
        ),

        DungeonZone(
            "Przeklęte Bagna",
            "Mroczna magia",
            "Szkielet",
            "Wiedźma",
            25, 35,
            250, 40,
            6
        ),

        DungeonZone(
            "Górskie Szczyty",
            "Latające bestie",
            "Harpia",
            "Gryf",
            30, 40,
            300, 45,
            7
        ),

        DungeonZone(
            "Krwawa Krypta",
            "Królestwo wampirów",
            "Nietoperz",
            "Wampir",
            40, 45,
            400, 50,
            8
        ),

        DungeonZone(
            "Smocza Wieża",
            "Ostatnie wyzwanie",
            "Wiwerna",
            "Smok",
            50, 50,
            500, 55,
            9
        )
    )
}