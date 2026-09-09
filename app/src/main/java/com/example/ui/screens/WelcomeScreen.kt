package com.example.ui.screens

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CampfireAmber
import com.example.ui.theme.CampfireOrange
import com.example.ui.theme.GoldenStar
import com.example.ui.theme.LavenderDark
import com.example.ui.theme.LavenderLight
import com.example.ui.theme.LavenderPrimary
import com.example.ui.theme.PrehistoricPaper
import com.example.ui.theme.TeacherHeartPurple

@Composable
fun WelcomeScreen(
    initialName: String,
    onStartAdventure: (studentName: String, avatar: String) -> Unit,
    onOpenDashboard: () -> Unit,
    onSpeakTeacher: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var studentName by remember { mutableStateOf(initialName) }
    var selectedAvatar by remember { mutableStateOf("👦") }
    val focusManager = LocalFocusManager.current

    val teacherGreeting = "Hi, my amazing learner! 💜 Welcome to our Stone Age adventure! Today we are going to discover the Present Simple together. Are you ready? Let's go!"

    LaunchedEffect(Unit) {
        onSpeakTeacher(teacherGreeting)
    }

    val infiniteTransition = rememberInfiniteTransition(label = "pulse_star")
    val starScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.16f,
        animationSpec = infiniteRepeatable(
            animation = tween(800),
            repeatMode = RepeatMode.Reverse
        ),
        label = "star_scale"
    )

    Surface(
        modifier = modifier.fillMaxSize(),
        color = PrehistoricPaper
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Teacher Brand Banner
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(LavenderPrimary, TeacherHeartPurple)
                        )
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "⭐", fontSize = 16.sp, modifier = Modifier.scale(starScale))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Teacher Shaimaa Owiess",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "💜", fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "“Learn with love. Dream big. Shine bright! 💜✨📚🌟”",
                style = MaterialTheme.typography.bodyMedium,
                color = LavenderDark,
                textAlign = TextAlign.Center,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Adventure Hero Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("welcome_hero_card"),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Prehistoric Adventure Title
                    Text(
                        text = "🦕 THE STONE AGE ADVENTURE 🪨🔥",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = CampfireOrange,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Discover Present Simple!",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = LavenderDark,
                        fontSize = 26.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Teacher Message speech balloon
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = LavenderLight,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(text = "👩‍🏫", fontSize = 22.sp)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Teacher Shaimaa says:",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = LavenderDark
                                    )
                                }
                                IconButton(
                                    onClick = { onSpeakTeacher(teacherGreeting) },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.VolumeUp,
                                        contentDescription = "Listen to teacher",
                                        tint = LavenderPrimary
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "“Hi, my amazing learner! 💜\n\nWelcome to our Stone Age adventure!\nToday we are going to discover the Present Simple together.\n\nAre you ready? Let's go!”",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color(0xFF311B92),
                                lineHeight = 23.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Student Name Input
                    Text(
                        text = "What is your explorer name?",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = LavenderDark
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = studentName,
                        onValueChange = { studentName = it },
                        placeholder = { Text("Enter your name here...") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("student_name_input"),
                        shape = RoundedCornerShape(18.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = LavenderPrimary,
                            unfocusedBorderColor = Color(0xFFD1C4E9),
                            focusedContainerColor = Color(0xFFFAF7FD),
                            unfocusedContainerColor = Color.White
                        ),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Avatar Picker
                    Text(
                        text = "Choose your Stone Age Character:",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF5D4037)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        val avatars = listOf(
                            "👦" to "Adam",
                            "👧" to "Sara",
                            "🦕" to "Baby Dino",
                            "⭐" to "Explorer"
                        )
                        avatars.forEach { (emoji, label) ->
                            val isSelected = selectedAvatar == emoji
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .clickable { selectedAvatar = emoji }
                                    .background(if (isSelected) LavenderLight else Color.Transparent)
                                    .border(
                                        width = if (isSelected) 2.dp else 1.dp,
                                        color = if (isSelected) LavenderPrimary else Color(0xFFE0E0E0),
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                Text(text = emoji, fontSize = 28.sp)
                                Text(
                                    text = label,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) LavenderDark else Color(0xFF757575)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Start Adventure Button
                    Button(
                        onClick = {
                            val finalName = if (studentName.isBlank()) "Super Explorer" else studentName.trim()
                            onStartAdventure(finalName, selectedAvatar)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(58.dp)
                            .testTag("start_adventure_button"),
                        shape = RoundedCornerShape(22.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CampfireOrange
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                    ) {
                        Text(
                            text = "Begin Stone Age Adventure! 🚀",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 17.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Teacher Dashboard Access Button
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = Color.White,
                shadowElevation = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOpenDashboard() }
                    .testTag("open_teacher_dashboard_btn")
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "📊", fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Teacher Dashboard & Analytics",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = LavenderDark
                            )
                            Text(
                                text = "View 5-Sheet Excel reports, skill analysis & past sessions",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF757575),
                                fontSize = 11.sp
                            )
                        }
                    }
                    Text(text = "→", fontSize = 18.sp, color = LavenderPrimary, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
