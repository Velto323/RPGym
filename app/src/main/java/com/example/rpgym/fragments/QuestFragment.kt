package com.example.rpgym.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.rpgym.PlayerData
import com.example.rpgym.R

class QuestFragment : Fragment(R.layout.fragment_quest) {

    private lateinit var tvBossQuest: TextView
    private lateinit var tvMonsterQuest: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvBossQuest = view.findViewById(R.id.tvBossQuest)
        tvMonsterQuest = view.findViewById(R.id.tvMonsterQuest)

        PlayerData.addListener {
            updateUI()
        }

        updateUI()
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun updateUI() {

        tvMonsterQuest.text =
            "Potwory: ${PlayerData.defeatedMonsters}"

        tvBossQuest.text =
            "Bossy: ${PlayerData.bossQuestProgress}/${PlayerData.bossQuestTarget}"
    }
}