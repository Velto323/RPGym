package com.example.rpgym.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

/**
 * Draws an optional full-screen background image (with a darkening scrim so foreground
 * text stays legible), mirroring the FrameLayout + background-image pattern used by the
 * old XML fragments. Pass [background] = null to render only the content.
 */
@Composable
fun ScreenBackground(
    @DrawableRes background: Int? = null,
    scrim: Color = Color.Black.copy(alpha = 0.45f),
    content: @Composable BoxScope.() -> Unit,
) {
    Box(Modifier.fillMaxSize()) {
        if (background != null) {
            Image(
                painter = painterResource(background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
            Box(Modifier.fillMaxSize().background(scrim))
        }
        content()
    }
}
