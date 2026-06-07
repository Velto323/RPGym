package com.example.rpgym.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.rpgym.PlayerData
import com.example.rpgym.R

class QuestFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_quest, container, false)

        val tvMainQuest = view.findViewById<TextView>(R.id.tvMainQuest)
        val tvDailyQuest = view.findViewById<TextView>(R.id.tvDailyQuest)
        val tvBestiary = view.findViewById<TextView>(R.id.tvBestiary)

        val bosses = listOf(
            "Król Goblinów",
            "Wilkołak",
            "Kamienny Troll",
            "Obłędny Rycerz",
            "Królowa Pająków",
            "Nekromanta",
            "Wiedźma",
            "Gryf",
            "Wampir",
            "Smok"
        )

        val progress = StringBuilder()

        bosses.forEachIndexed { index, name ->

            if (PlayerData.defeatedBosses.contains(index)) {
                progress.append("✓ $name\n")
            } else {
                progress.append("□ $name\n")
            }
        }

        tvMainQuest.text = progress.toString()

        tvDailyQuest.text =
            "Pokonaj 10 przeciwników\n\n" +
                    "Postęp: ${PlayerData.defeatedMonsters}/10"

        tvBestiary.text =
            "Pokonani bossowie: ${PlayerData.defeatedBosses.size}/10"

        return view
    }
}