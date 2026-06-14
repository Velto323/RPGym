package com.example.rpgym.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.rpgym.UI.MainScreen
import com.example.rpgym.data.PlayerData
import com.example.rpgym.quest.QuestManager
import io.github.jan.supabase.auth.auth

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentUser = SupabaseClient.client.auth.currentUserOrNull()

        if (currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        PlayerData.load(this)
        QuestManager.load(this)

        setContent {
            MainScreen()
        }
    }

    override fun onStop() {
        super.onStop()
        PlayerData.save(this)
        QuestManager.save(this)
    }
}
