package com.example.rpgym.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.rpgym.*

class BattleFragment : Fragment() {

    private var round = 1
    private lateinit var monster: Monster

    private lateinit var tvRound: TextView
    private lateinit var tvMonster: TextView
    private lateinit var tvHp: TextView
    private lateinit var btnAttack: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_battle, container, false)

        tvRound = view.findViewById(R.id.tvRound)
        tvMonster = view.findViewById(R.id.tvMonster)
        tvHp = view.findViewById(R.id.tvHp)
        btnAttack = view.findViewById(R.id.btnAttack)

        generateMonster()

        btnAttack.setOnClickListener {
            playerAttack()
        }

        updateUI()

        return view
    }

    private fun generateMonster() {
        monster = MonsterRepository.getMonster(round)
    }

    private fun playerAttack() {

        monster.hp -= PlayerData.getStrength()

        if (monster.hp <= 0) {
            nextRound()
            return
        }

        monsterAttack()
        updateUI()
    }

    private fun monsterAttack() {

        PlayerData.hp -= monster.strength

        if (PlayerData.hp <= 0) {
            PlayerData.hp = PlayerData.maxHp
            round = 1
        }
    }

    private fun nextRound() {

        val wasBoss = monster.isBoss

        round++

        if (wasBoss) {
            MonsterRepository.onBossDefeated()

            PlayerData.level++
            PlayerData.strengthLevel++
        }

        generateMonster()
        updateUI()
    }

    private fun updateUI() {
        tvRound.text = "Runda: $round"
        tvMonster.text = monster.name
        tvHp.text = "HP: ${monster.hp}"
    }
}