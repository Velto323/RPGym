package com.example.rpgym.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.rpgym.data.PlayerRepository
import com.example.rpgym.data.local.AppDatabase
import com.example.rpgym.ui.MainScreen
import com.example.rpgym.ui.theme.RPGymTheme
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private lateinit var repository: PlayerRepository
    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentUser = SupabaseClient.client.auth.currentUserOrNull()
        if (currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }
        userId = currentUser.id

        repository = PlayerRepository(AppDatabase.getInstance(applicationContext))

        setContent {
            RPGymTheme {
                MainScreen(
                    repository = repository,
                    userId = userId!!,
                    onLoggedOut = {
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    },
                )
            }
        }
    }

    override fun onStop() {
        super.onStop()
        val uid = userId ?: return
        // Local Room save is synchronous on IO thread — guarantees survival across process death
        runBlocking(Dispatchers.IO) { repository.savePlayerLocal(uid) }
        // Best-effort cloud sync — acceptable to occasionally miss, updatedAt reconciles on next load
        lifecycleScope.launch { repository.savePlayer(uid, syncToCloud = true) }
    }
}
