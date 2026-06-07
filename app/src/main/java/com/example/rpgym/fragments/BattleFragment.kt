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

    private var localRound = 1
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

        spawnMonster()

        btnAttack.setOnClickListener {
            playerAttack()
        }

        updateUI()

        return view
    }

    private fun spawnMonster() {
        monster = MonsterRepository.getMonster(
            PlayerData.currentZone,
            localRound
        )
    }

    private fun playerAttack() {

        monster.hp -= PlayerData.getStrength()

        if (monster.hp <= 0) {
            onMonsterKilled()
            return
        }

        monsterAttack()
        updateUI()
    }

    private fun monsterAttack() {

        PlayerData.hp -= monster.strength

        if (PlayerData.hp <= 0) {
            PlayerData.hp = PlayerData.maxHp
            localRound = 1
        }
    }

    private fun onMonsterKilled() {

        val killed = monster
        val wasBoss = killed.isBoss
        val zone = PlayerData.currentZone

        PlayerData.defeatedMonsters++

        if (wasBoss) {

            PlayerData.level++
            PlayerData.strengthLevel++

            PlayerData.addBossKill(zone)

            android.app.AlertDialog.Builder(requireContext())
                .setTitle("🎉 GRATULACJE!")
                .setMessage("Pokonano bossa!\nOdblokowano nową lokację!")
                .setPositiveButton("OK", null)
                .show()

            localRound = 1

        } else {
            localRound++
        }

        spawnMonster()
        updateUI()

        PlayerData.notifyChange()
    }

    private fun updateUI() {

        val bossTag = if (monster.isBoss) " 👑 BOSS" else ""

        tvRound.text = "Runda: $localRound$bossTag"
        tvMonster.text = monster.name
        tvHp.text = "HP: ${monster.hp}"
    }
}