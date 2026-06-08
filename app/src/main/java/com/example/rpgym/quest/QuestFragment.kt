package com.example.rpgym.quest

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.rpgym.R

class QuestFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_quest, container, false)

        val layout = view.findViewById<LinearLayout>(R.id.questContainer)

        layout.removeAllViews()

        QuestManager.quests.forEach { quest ->

            val tv = TextView(requireContext())

            tv.text =
                "${quest.title}\n" +
                        "${quest.progress}/${quest.target} - " +
                        if (quest.completed) "✔ DONE" else "IN PROGRESS"

            tv.textSize = 16f
            tv.setPadding(20, 20, 20, 20)

            layout.addView(tv)
        }

        return view
    }
}