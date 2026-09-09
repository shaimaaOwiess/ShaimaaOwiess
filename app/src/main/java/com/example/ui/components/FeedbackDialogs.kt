package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.CampfireAmber
import com.example.ui.theme.CampfireOrange
import com.example.ui.theme.EncouragementOrange
import com.example.ui.theme.GoldenStar
import com.example.ui.theme.LavenderDark
import com.example.ui.theme.LavenderLight
import com.example.ui.theme.LavenderPrimary
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TeacherHeartPurple

@Composable
fun CorrectFeedbackDialog(
    encouragementMessage: String,
    explanation: String,
    onDismiss: () -> Unit,
    onSpeak: (String) -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "bouncing_dino")
    val bounceScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.12f,
        animationSpec = infiniteRepeatable(
            animation = tween(450),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bounce"
    )

    LaunchedEffect(Unit) {
        onSpeak("$encouragementMessage. $explanation")
    }

    Dialog(
        onDismissRequest = { /* Force explicit button press */ },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            // Particle burst background
            CelebrationParticles(modifier = Modifier.fillMaxWidth().height(300.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("correct_feedback_card"),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(22.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Joyful Avatar & Stars
                    Box(
                        modifier = Modifier
                            .size(76.dp)
                            .scale(bounceScale)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    listOf(Color(0xFFE8F5E9), Color(0xFFC8E6C9))
                                )
                            )
                            .border(3.dp, SuccessGreen, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🥳🦕", fontSize = 34.sp)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "⭐ ✨ 🎉 FANTASTIC! 🌟 💜",
                        style = MaterialTheme.typography.titleLarge,
                        color = SuccessGreen,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Shaimaa Teacher Message Card
                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = LavenderLight,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(text = "⭐", fontSize = 20.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = encouragementMessage,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = LavenderDark,
                                    fontWeight = FontWeight.SemiBold,
                                    lineHeight = 22.sp
                                )
                            }
                            IconButton(
                                onClick = { onSpeak("$encouragementMessage. $explanation") },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.VolumeUp,
                                    contentDescription = "Read feedback",
                                    tint = LavenderPrimary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Why it is correct
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFFF1F8E9),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "💡", fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Why this is correct:",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = SuccessGreen,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = explanation,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF2E7D32),
                                lineHeight = 21.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Continue button
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .testTag("continue_adventure_button"),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SuccessGreen
                        )
                    ) {
                        Text(
                            text = "Next Adventure Step 🌟",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GentleSupportFeedbackDialog(
    correctAnswer: String,
    explanation: String,
    supportMessage: String,
    onDismiss: () -> Unit,
    onSpeak: (String) -> Unit
) {
    LaunchedEffect(Unit) {
        onSpeak("Oops! That's okay! Mistakes help our brains learn. The correct answer is $correctAnswer. $explanation. $supportMessage")
    }

    Dialog(
        onDismissRequest = { /* Explicit button */ },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("support_feedback_card"),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Gentle transition icon: 🥺💔 → 💡 → 🌟
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFFFFF3E0))
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(text = "🥺💔", fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "→", fontSize = 16.sp, color = EncouragementOrange)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "💡", fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "→", fontSize = 16.sp, color = EncouragementOrange)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "🌟", fontSize = 20.sp)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Oops! That's okay! 💜",
                    style = MaterialTheme.typography.titleLarge,
                    color = TeacherHeartPurple,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Text(
                    text = "Mistakes help our brains learn!\nLet's look at it together 🌸",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF6D4C41),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Correct answer showcase
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFEDE7F6),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "🎯", fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "The correct answer is:",
                                style = MaterialTheme.typography.labelLarge,
                                color = LavenderDark,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = correctAnswer,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = LavenderDark,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = explanation,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF4A148C),
                            lineHeight = 21.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Teacher Shaimaa Warm Encouragement
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFFFF8E1),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "🌷", fontSize = 22.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = supportMessage,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF795548),
                                lineHeight = 20.sp
                            )
                        }
                        IconButton(
                            onClick = {
                                onSpeak("The correct answer is $correctAnswer. $explanation. $supportMessage")
                            },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.VolumeUp,
                                contentDescription = "Listen to Teacher Shaimaa",
                                tint = LavenderPrimary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .testTag("try_next_button"),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LavenderPrimary
                    )
                ) {
                    Text(
                        text = "Let's Keep Going Together! 💜",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}
