package com.example.rpgym.dung

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.rpgym.R
import com.example.rpgym.data.*

class DungeonFragment : Fragment() {

    private var index = 0

    private lateinit var tvName: TextView
    private lateinit var tvDesc: TextView
    private lateinit var btnEnter: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_dungeon, container, false)

        tvName = view.findViewById(R.id.tvZoneName)
        tvDesc = view.findViewById(R.id.tvZoneDesc)
        btnEnter = view.findViewById(R.id.btnEnterDungeon)

        updateUI()

        btnEnter.setOnClickListener {

            PlayerData.currentZone = index
            PlayerData.dungeonWave = 1

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, BattleFragment())
                .addToBackStack(null)
                .commit()
        }

        return view
    }

    private fun updateUI() {

        val zone = DungeonRepository.zones[index]

        tvName.text = zone.name
        tvDesc.text = zone.description
    }
}