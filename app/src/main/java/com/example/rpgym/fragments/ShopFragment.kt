package com.example.rpgym.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.rpgym.PlayerData
import com.example.rpgym.R

class ShopFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_shop, container, false)

        val tvGold = view.findViewById<TextView>(R.id.tvShopGold)
        val tvHealthPotions = view.findViewById<TextView>(R.id.tvHealthPotions)
        val tvStrengthPotions = view.findViewById<TextView>(R.id.tvStrengthPotions)

        fun refreshTexts() {
            tvGold.text = "Złoto: ${PlayerData.gold}"
            tvHealthPotions.text = "Mikstury zdrowia: ${PlayerData.healthPotions}"
            tvStrengthPotions.text = "Mikstury siły: ${PlayerData.strengthPotions}"
        }

        refreshTexts()

        view.findViewById<Button>(R.id.btnBuyHealthPotion).setOnClickListener {
            if (PlayerData.buyHealthPotion()) {
                refreshTexts()
            } else {
                Toast.makeText(requireContext(), "Za mało złota!", Toast.LENGTH_SHORT).show()
            }
        }

        view.findViewById<Button>(R.id.btnBuyStrengthPotion).setOnClickListener {
            if (PlayerData.buyStrengthPotion()) {
                refreshTexts()
            } else {
                Toast.makeText(requireContext(), "Za mało złota!", Toast.LENGTH_SHORT).show()
            }
        }

        view.findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        return view
    }
}
