package com.example.rpgym.dung

import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.rpgym.R
import com.example.rpgym.data.*

class DungeonHubFragment : Fragment() {

    private lateinit var root: View
    private lateinit var tvName: TextView
    private lateinit var tvDesc: TextView

    private var index = 0f
    private var startX = 0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: android.os.Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_dungeon_hub, container, false)

        root = view
        tvName = view.findViewById(R.id.tvZoneName)
        tvDesc = view.findViewById(R.id.tvZoneDesc)

        view.findViewById<Button>(R.id.btnEnterDungeon).setOnClickListener {

            PlayerData.currentZone = index.toInt()
            PlayerData.dungeonWave = 1

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, BattleFragment.newInstance(index.toInt()))
                .addToBackStack(null)
                .commit()
        }

        view.findViewById<Button>(R.id.btnMeditation).setOnClickListener {

            PlayerData.startMeditation()

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, MeditationFragment())
                .addToBackStack(null)
                .commit()
        }

        setupSwipe(view)
        updateUI()

        return view
    }

    private fun setupSwipe(view: View) {

        view.isClickable = true
        view.isFocusable = true

        view.setOnTouchListener { _, event ->

            when (event.action) {

                MotionEvent.ACTION_DOWN -> {
                    startX = event.x
                    true
                }

                MotionEvent.ACTION_UP -> {

                    val diff = event.x - startX

                    if (diff > 120) prev()
                    else if (diff < -120) next()

                    updateUI()
                    true
                }

                else -> false
            }
        }
    }

    private fun next() {

        if (index < PlayerData.unlockedZone.toFloat()) index++
        else index = 0f
    }

    private fun prev() {

        if (index > 0f) index--
        else index = PlayerData.unlockedZone.toFloat()
    }

    private fun updateUI() {

        val zone = DungeonRepository.zones[index.toInt()]

        tvName.text = zone.name
        tvDesc.text = zone.description
    }
}