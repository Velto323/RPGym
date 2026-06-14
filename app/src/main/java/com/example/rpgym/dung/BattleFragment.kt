package com.example.rpgym.dung

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.rpgym.R
import com.example.rpgym.data.DungeonRepository
import com.example.rpgym.data.Monster
import com.example.rpgym.data.PlayerData
import com.example.rpgym.quest.QuestManager

class BattleFragment : Fragment() {

    private lateinit var monster: Monster

    private lateinit var tvMonsterHp: TextView
    private lateinit var tvZone: TextView
    private lateinit var tvWave: TextView

    private var zoneIndex = 0

    companion object {
        fun newInstance(zone: Int): BattleFragment {
            val f = BattleFragment()
            val b = Bundle()
            b.putInt("zone", zone)
            f.arguments = b
            return f
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_battle, container, false)

        tvMonsterHp = view.findViewById(R.id.tvMonsterHp)
        tvZone = view.findViewById(R.id.tvZone)
        tvWave = view.findViewById(R.id.tvWave)

        zoneIndex = arguments?.getInt("zone") ?: 0

        spawn()
        updateUI()

        view.findViewById<Button>(R.id.btnAttack).setOnClickListener {
            attack()
        }

        return view
    }

    private fun spawn() {

        val zone = DungeonRepository.zones[zoneIndex]
        val isBoss = PlayerData.dungeonWave % 5 == 0

        monster = Monster(
            name = if (isBoss) zone.bossName else zone.mobName,
            hp = if (isBoss) zone.bossHp else zone.mobHp,
            strength = if (isBoss) zone.bossAttack else zone.mobAttack,
            isBoss = isBoss
        )
    }

    private fun attack() {

        // gracz bije
        monster.hp -= PlayerData.strength

        if (monster.hp <= 0) {
            monster.hp = 0
            updateUI() // Pokaż 0 HP przed spawnem następnego
            
            QuestManager.onMonsterKilled()

            if (monster.isBoss) {
                PlayerData.addXp((zoneIndex + 1) * 25)
                val firstTime = PlayerData.addBossKill(zoneIndex)

                AlertDialog.Builder(requireContext())
                    .setTitle("👑 BOSS POKONANY")
                    .setMessage(
                        if (firstTime)
                            "Odblokowano nową lokację!\n+${(zoneIndex + 1) * 25} XP"
                        else
                            "+${(zoneIndex + 1) * 25} XP"
                    )
                    .setPositiveButton("OK") { _, _ ->
                        nextWave()
                    }
                    .setCancelable(false)
                    .show()
            } else {
                nextWave()
            }
        } else {
            // mob bije tylko jeśli żyje
            PlayerData.damage(monster.strength)

            if (PlayerData.hp <= 0) {
                PlayerData.hp = 1
                PlayerData.dungeonWave = 1

                AlertDialog.Builder(requireContext())
                    .setTitle("💀 ŚMIERĆ")
                    .setMessage("Wracasz do początku lochu")
                    .setPositiveButton("OK") { _, _ ->
                        spawn()
                        updateUI()
                    }
                    .setCancelable(false)
                    .show()
            }
            updateUI()
        }
    }

    private fun nextWave() {
        PlayerData.dungeonWave++
        spawn()
        updateUI()
    }

    private fun updateUI() {

        val zone = DungeonRepository.zones[zoneIndex]

        tvMonsterHp.text = "HP: ${monster.hp}"
        tvZone.text = zone.name
        tvWave.text = "Fala: ${PlayerData.dungeonWave}"
    }
}
