package com.example.rpgym.quest

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.rpgym.R
import com.example.rpgym.data.PlayerData

class QuestFragment : Fragment(R.layout.fragment_quest) {

    private var tvBossQuest: TextView? = null
    private var tvMonsterQuest: TextView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvBossQuest = view.findViewById(R.id.tvBossQuest)
        tvMonsterQuest = view.findViewById(R.id.tvMonsterQuest)

        PlayerData.addListener { updateUI() }

        updateUI()
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun updateUI() {

        tvBossQuest?.text =
            "Bossy: ${PlayerData.bossQuestProgress}/${PlayerData.bossQuestTarget}"

        tvMonsterQuest?.text =
            "Potwory: ${PlayerData.defeatedMonsters}"
    }

    override fun onDestroyView() {
        super.onDestroyView()

        tvBossQuest = null
        tvMonsterQuest = null

        PlayerData.removeListener { updateUI() }
    }
}