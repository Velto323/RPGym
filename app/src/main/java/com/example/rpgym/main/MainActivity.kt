package com.example.rpgym.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.rpgym.data.PlayerRepository
import com.example.rpgym.data.local.AppDatabase
import com.example.rpgym.ui.theme.RPGymTheme
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {

    private lateinit var repository: PlayerRepository
    private var userId: String? = null
    private var isLoggingOut = false

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
                var isLoading by remember { mutableStateOf(true) }

                LaunchedEffect(Unit) {
                    try {
                        com.example.rpgym.data.PlayerData.reset()
                        repository.loadPlayer(userId!!)
                        com.example.rpgym.quest.QuestManager.refreshQuests()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    } finally {
                        isLoading = false
                    }
                }

                if (isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else {
                    MainScreen(
                        onLogout = {
                            logout()
                        }
                    )
                }
            }
        }
    }

    private fun logout() {
        if (isLoggingOut) return
        isLoggingOut = true
        lifecycleScope.launch {
            val uid = userId
            if (uid != null) {
                runCatching {
                    repository.savePlayer(uid)
                }
            }
            try {
                SupabaseClient.client.auth.signOut()
                com.example.rpgym.data.PlayerData.reset()
            } catch (_: Exception) {}
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }
    }

    override fun onStop() {
        super.onStop()
        if (isLoggingOut) return
        val uid = userId ?: return
        runBlocking(Dispatchers.IO) { repository.savePlayerLocal(uid) }
        lifecycleScope.launch { repository.savePlayer(uid, syncToCloud = true) }
    }
}
