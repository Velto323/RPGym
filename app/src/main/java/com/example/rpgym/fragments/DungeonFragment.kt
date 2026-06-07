package com.example.rpgym.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.rpgym.DungeonRepository
import com.example.rpgym.PlayerData
import com.example.rpgym.R

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

        view.setOnTouchListener(object : View.OnTouchListener {

            private var startX = 0f

            override fun onTouch(v: View?, event: MotionEvent): Boolean {

                when (event.action) {

                    MotionEvent.ACTION_DOWN -> {
                        startX = event.x
                    }

                    MotionEvent.ACTION_UP -> {

                        val diff = event.x - startX

                        if (diff > 120) {
                            prevZone()
                        } else if (diff < -120) {
                            nextZone()
                        }
                    }
                }

                return true
            }
        })

        btnEnter.setOnClickListener {

            if (index > PlayerData.unlockedZone) {
                return@setOnClickListener
            }

            PlayerData.currentZone = index

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, BattleFragment())
                .addToBackStack(null)
                .commit()
        }

        return view
    }

    private fun nextZone() {
        index = (index + 1) % DungeonRepository.zones.size
        updateUI()
    }

    private fun prevZone() {
        index = if (index - 1 < 0)
            DungeonRepository.zones.lastIndex
        else
            index - 1

        updateUI()
    }

    private fun updateUI() {

        val zone = DungeonRepository.zones[index]

        tvName.text = zone.name
        tvDesc.text =
            "${zone.description}\n\nOdblokowano: ${index <= PlayerData.unlockedZone}"

        if (index <= PlayerData.unlockedZone) {

            btnEnter.isEnabled = true
            btnEnter.text = "⚔ Wejdź do lochu"

        } else {

            btnEnter.isEnabled = false
            btnEnter.text = "🔒 Zablokowane"
        }
    }
}