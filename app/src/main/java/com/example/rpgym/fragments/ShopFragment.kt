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
import com.example.rpgym.data.PlayerData
import com.example.rpgym.R

class ShopFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_shop, container, false)

        val tvGold = view.findViewById<TextView>(R.id.tvShopGold)
        val tvInfo = view.findViewById<TextView>(R.id.tvShopInfo)

        val tvHealthPotions = view.findViewById<TextView>(R.id.tvHealthPotions)
        val tvStrengthPotions = view.findViewById<TextView>(R.id.tvStrengthPotions)

        fun refresh() {

            tvGold.text = "Złoto: ${PlayerData.gold}"

            tvInfo.text =
                "HP: ${PlayerData.hp}/${PlayerData.getCurrentMaxHp()}\n" +
                        "Siła: ${PlayerData.strengthLevel}"

            tvHealthPotions.text =
                "Mikstury HP: ${PlayerData.healthPotions}"

            tvStrengthPotions.text =
                "Mikstury siły: ${PlayerData.strengthPotions}"
        }

        refresh()

        // ================= BUY HP POTION =================
        view.findViewById<Button>(R.id.btnBuyHealthPotion).setOnClickListener {

            if (!PlayerData.buyHealthPotion()) {
                Toast.makeText(requireContext(), "Za mało złota!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(requireContext(), "Kupiono miksturę HP!", Toast.LENGTH_SHORT).show()
            refresh()
        }

        // ================= BUY STRENGTH POTION =================
        view.findViewById<Button>(R.id.btnBuyStrengthPotion).setOnClickListener {

            if (!PlayerData.buyStrengthPotion()) {
                Toast.makeText(requireContext(), "Za mało złota!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(requireContext(), "Kupiono miksturę siły!", Toast.LENGTH_SHORT).show()
            refresh()
        }

        // ================= BACK =================
        view.findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        return view
    }
}