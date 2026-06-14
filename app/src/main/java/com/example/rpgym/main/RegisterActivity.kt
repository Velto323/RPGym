package com.example.rpgym.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.rpgym.ui.screens.RegisterScreen
import com.example.rpgym.ui.theme.RPGymTheme

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RPGymTheme {
                RegisterScreen(
                    onRegistered = { finish() },
                    onNavigateToLogin = { finish() },
                )
            }
        }
    }
}
