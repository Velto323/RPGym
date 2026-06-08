package com.example.rpgym.dung

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.rpgym.R
import com.example.rpgym.data.DungeonRepository
import com.example.rpgym.data.PlayerData

class DungeonHubFragment : Fragment() {

    private lateinit var root: View
    private lateinit var tvZone: TextView
    private lateinit var tvDesc: TextView
    private lateinit var btnEnter: Button
    private lateinit var btnMeditation: Button

    private var startX = 0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        root = inflater.inflate(R.layout.fragment_dungeon_hub, container, false)

        tvZone = root.findViewById(R.id.tvZone)
        tvDesc = root.findViewById(R.id.tvZoneDesc)
        btnEnter = root.findViewById(R.id.btnEnter)
        btnMeditation = root.findViewById(R.id.btnMeditation)

        btnEnter.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, BattleFragment())
                .addToBackStack(null)
                .commit()
        }

        btnMeditation.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, MeditationFragment())
                .addToBackStack(null)
                .commit()
        }

        setupSwipe()
        updateUI()

        return root
    }

    // =========================
    // SWIPE (PEWNY ROOT TOUCH)
    // =========================
    private fun setupSwipe() {

        root.setOnTouchListener { _, event ->

            when (event.action) {

                MotionEvent.ACTION_DOWN -> {
                    startX = event.x
                }

                MotionEvent.ACTION_UP -> {

                    val diff = event.x - startX

                    if (diff > 80) prevZone()
                    if (diff < -80) nextZone()

                    updateUI()
                }
            }

            true
        }
    }

    // =========================
    // ZONE LOGIC
    // =========================
    private fun nextZone() {
        if (PlayerData.currentZone < PlayerData.unlockedZone) {
            PlayerData.currentZone++
        }
    }

    private fun prevZone() {
        if (PlayerData.currentZone > 0) {
            PlayerData.currentZone--
        }
    }

    // =========================
    // UI FROM REPOSITORY
    // =========================
    private fun updateUI() {

        val zone = DungeonRepository.zones[PlayerData.currentZone]

        tvZone.text = zone.name
        tvDesc.text = zone.description
    }
}