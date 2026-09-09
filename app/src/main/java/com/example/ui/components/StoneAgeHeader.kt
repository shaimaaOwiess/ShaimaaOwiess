package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.VolumeMute
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.GrammarZone
import com.example.ui.theme.CampfireAmber
import com.example.ui.theme.GoldenStar
import com.example.ui.theme.LavenderDark
import com.example.ui.theme.LavenderLight
import com.example.ui.theme.LavenderPrimary
import com.example.ui.theme.TeacherHeartPurple

@Composable
fun StoneAgeHeader(
    currentZone: GrammarZone,
    unlockedZoneId: Int,
    studentName: String,
    score: Int,
    isSpeaking: Boolean,
    isMuted: Boolean,
    onToggleMute: () -> Unit,
    onZoneClick: (GrammarZone) -> Unit,
    onOpenDashboard: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(800),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .testTag("stone_age_header"),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            // Top Bar: Student Name, Stars, Voice Toggle & Teacher Brand
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Student Chip
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(LavenderLight)
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(text = "🦕", fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = studentName.ifBlank { "Explorer" },
                        style = MaterialTheme.typography.titleMedium,
                        color = LavenderDark,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFFFF8E1))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "⭐", fontSize = 13.sp)
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = "$score",
                                fontWeight = FontWeight.Bold,
                                color = CampfireAmber,
                                fontSize = 14.sp
                            )
                        }
                    }
                }

                // Teacher Shaimaa Badge & Voice Control
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Teacher Chip
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                Brush.horizontalGradient(
                                    listOf(LavenderPrimary, TeacherHeartPurple)
                                )
                            )
                            .clickable { onOpenDashboard() }
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                            .testTag("teacher_dashboard_chip")
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "⭐", fontSize = 12.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Shaimaa Owiess 💜",
                                color = Color.White,
                                style = MaterialTheme.typography.labelLarge,
                                fontSize = 12.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    // Voice Audio button with speaking animation
                    IconButton(
                        onClick = onToggleMute,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(if (isSpeaking) LavenderPrimary else Color(0xFFF3E5F5))
                            .testTag("voice_toggle_button")
                    ) {
                        Icon(
                            imageVector = if (isMuted) Icons.Default.VolumeMute else Icons.Default.VolumeUp,
                            contentDescription = if (isMuted) "Unmute Teacher Voice" else "Mute Teacher Voice",
                            tint = if (isSpeaking) Color.White else LavenderDark,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Adventure Progress Map: 🏕️ → 🔥 → 🪨 → 🌳 → 🌊 → 🏆
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFF5F0EB))
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                GrammarZone.entries.forEachIndexed { index, zone ->
                    val isUnlocked = zone.id <= unlockedZoneId
                    val isCurrent = zone == currentZone
                    val isCompleted = zone.id < currentZone.id

                    val iconBg by animateColorAsState(
                        targetValue = when {
                            isCurrent -> LavenderPrimary
                            isCompleted -> Color(0xFF4CAF50)
                            isUnlocked -> Color(0xFFD7CCC8)
                            else -> Color(0xFFE0E0E0)
                        },
                        label = "iconBg"
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .clickable(enabled = isUnlocked) { onZoneClick(zone) }
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                            .testTag("zone_step_${zone.id}")
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .scale(if (isCurrent) pulseScale else 1f)
                                .clip(CircleShape)
                                .background(iconBg)
                                .border(
                                    width = if (isCurrent) 2.dp else 1.dp,
                                    color = if (isCurrent) GoldenStar else Color.Transparent,
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isCompleted) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Completed",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            } else {
                                Text(
                                    text = zone.emoji,
                                    fontSize = 17.sp
                                )
                            }
                        }

                        Text(
                            text = "Z${zone.id}",
                            fontSize = 10.sp,
                            fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                            color = if (isCurrent) LavenderDark else Color(0xFF757575)
                        )
                    }

                    if (index < GrammarZone.entries.lastIndex) {
                        Text(
                            text = "→",
                            fontSize = 12.sp,
                            color = if (zone.id < unlockedZoneId) LavenderPrimary else Color(0xFFBDBDBD),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
