package com.example.rpgym.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.rpgym.PlayerData
import com.example.rpgym.R

class MeditationFragment : Fragment() {

    private var running = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_meditation, container, false)

        startRegen()

        return view
    }

    private fun startRegen() {

        Thread {

            while (running) {

                PlayerData.meditateTick()
                PlayerData.notifyChange()

                Thread.sleep(1000)
            }
        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        running = false
    }
}