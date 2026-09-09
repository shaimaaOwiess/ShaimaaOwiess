package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random

data class ParticleItem(
    val emoji: String,
    val startX: Float,
    val targetX: Float,
    val targetY: Float,
    val rotation: Float,
    val size: Int
)

@Composable
fun CelebrationParticles(
    modifier: Modifier = Modifier,
    particleCount: Int = 22
) {
    val emojis = listOf("⭐", "✨", "🎉", "🥳", "💜", "🦴", "🌟", "🦕", "🪨")

    val particles = remember {
        List(particleCount) {
            ParticleItem(
                emoji = emojis[Random.nextInt(emojis.size)],
                startX = Random.nextFloat() * 700 - 350,
                targetX = Random.nextFloat() * 800 - 400,
                targetY = Random.nextFloat() * 900 - 450,
                rotation = Random.nextFloat() * 360,
                size = Random.nextInt(18, 34)
            )
        }
    }

    val progress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1400, easing = LinearEasing)
        )
    }

    Box(modifier = modifier.fillMaxSize()) {
        particles.forEach { p ->
            val curX = p.startX + (p.targetX - p.startX) * progress.value
            val curY = p.targetY * progress.value
            val alpha = (1f - progress.value).coerceIn(0f, 1f)
            val scale = 0.5f + (progress.value * 0.8f)

            Text(
                text = p.emoji,
                fontSize = p.size.sp,
                modifier = Modifier
                    .offset { IntOffset(curX.toInt() + 350, curY.toInt() + 200) }
                    .scale(scale)
                    .alpha(alpha)
            )
        }
    }
}
