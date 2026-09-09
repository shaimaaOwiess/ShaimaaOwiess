package com.example.ui.screens

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AssessmentResult
import com.example.data.model.GrammarSkill
import com.example.data.model.PerformanceLevel
import com.example.data.model.RemediationGameType
import com.example.data.model.SkillStatus
import com.example.data.repository.StudentProgressRepository
import com.example.ui.components.CelebrationParticles
import com.example.ui.theme.CampfireAmber
import com.example.ui.theme.CampfireOrange
import com.example.ui.theme.GoldenStar
import com.example.ui.theme.LavenderDark
import com.example.ui.theme.LavenderLight
import com.example.ui.theme.LavenderPrimary
import com.example.ui.theme.PrehistoricPaper
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TeacherHeartPurple

@Composable
fun AssessmentReportScreen(
    result: AssessmentResult,
    onPlayRemediation: (RemediationGameType) -> Unit,
    onOpenTeacherDashboard: () -> Unit,
    onRestartAdventure: () -> Unit,
    onSpeakTeacher: (String, Boolean, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LaunchedEffect(result) {
        val speech = "${result.level.title}. ${result.teacherSpecialMessage}"
        onSpeakTeacher(speech, result.scorePercentage >= 80, result.scorePercentage < 60)
    }

    Surface(
        modifier = modifier.fillMaxSize().testTag("assessment_report_screen"),
        color = PrehistoricPaper
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (result.scorePercentage >= 70) {
                CelebrationParticles(modifier = Modifier.fillMaxSize(), particleCount = 28)
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                // Student Performance Profile Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("performance_card"),
                    shape = RoundedCornerShape(26.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(22.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Badge Icon
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .clip(CircleShape)
                                .background(LavenderLight),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = result.level.badgeEmoji, fontSize = 38.sp)
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = result.level.title,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = LavenderDark,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = "Student: ${result.studentName} | ${result.dateString}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF6D4C41)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Score & Stats Grid
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            StatBox(label = "Score", value = "${result.scorePercentage}%", emoji = "🎯")
                            StatBox(label = "Correct", value = "${result.correctAnswers}/${result.totalQuestions}", emoji = "✅")
                            StatBox(label = "Time", value = "${result.timeSpentSeconds}s", emoji = "⏱️")
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Strongest & Weakest Skill Highlights
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = Color(0xFFE8F5E9),
                                modifier = Modifier.weight(1f)
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text(text = "🌟 Strongest Skill", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SuccessGreen)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = result.strongestSkill.displayName,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF1B5E20)
                                    )
                                }
                            }

                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = Color(0xFFFFF3E0),
                                modifier = Modifier.weight(1f)
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text(text = "🌱 Practice Area", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = CampfireAmber)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = result.weakestSkill.displayName,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFE65100)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Digital Reward Sticker
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1)),
                    border = BorderStroke(2.dp, GoldenStar)
                ) {
                    Row(
                        modifier = Modifier.padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = result.level.badgeEmoji, fontSize = 42.sp)
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "OFFICIAL DIGITAL STICKER",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = CampfireAmber
                            )
                            Text(
                                text = result.level.stickerTitle,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFFE65100)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = result.level.stickerQuote,
                                style = MaterialTheme.typography.bodyMedium,
                                fontStyle = FontStyle.Italic,
                                color = Color(0xFF5D4037)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Shaimaa's Special Learning Plan Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("special_learning_plan"),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "💜", fontSize = 22.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = "Shaimaa's Special Learning Plan",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = LavenderDark
                                    )
                                    Text(
                                        text = "Personalized with love by Teacher Shaimaa Owiess",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color(0xFF757575)
                                    )
                                }
                            }

                            IconButton(
                                onClick = {
                                    onSpeakTeacher(result.teacherSpecialMessage, false, true)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.VolumeUp,
                                    contentDescription = "Read plan aloud",
                                    tint = LavenderPrimary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Special Teacher Message Box
                        Surface(
                            shape = RoundedCornerShape(18.dp),
                            color = LavenderLight,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "“${result.teacherSpecialMessage}”",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF311B92),
                                lineHeight = 22.sp,
                                modifier = Modifier.padding(14.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Personalized Tips Checklist
                        Text(
                            text = "💡 Your Personal Stone Age Practice Tips:",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = LavenderDark
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        result.personalizedTips.forEach { tip ->
                            Row(
                                modifier = Modifier.padding(vertical = 4.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(text = "🌸", fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = tip,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color(0xFF424242),
                                    lineHeight = 20.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        // Remediation Mini-Games Quick Launch
                        Text(
                            text = "🎮 Play Practice Mini-Games:",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = CampfireAmber
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            MiniGameLauncherButton(
                                title = "Build Sentence",
                                icon = "🪨",
                                modifier = Modifier.weight(1f),
                                onClick = { onPlayRemediation(RemediationGameType.BUILD_SENTENCE) }
                            )
                            MiniGameLauncherButton(
                                title = "Campfire",
                                icon = "🔥",
                                modifier = Modifier.weight(1f),
                                onClick = { onPlayRemediation(RemediationGameType.CAMPFIRE_CHALLENGE) }
                            )
                            MiniGameLauncherButton(
                                title = "Feed Dino",
                                icon = "🦕",
                                modifier = Modifier.weight(1f),
                                onClick = { onPlayRemediation(RemediationGameType.FEED_DINOSAUR) }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Official Printable/Shareable Certificate of Achievement
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("certificate_card"),
                    shape = RoundedCornerShape(26.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFDF7)),
                    border = BorderStroke(3.dp, GoldenStar),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(22.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "📜 🪨 🌟 🪨 📜", fontSize = 20.sp)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "CERTIFICATE OF ACHIEVEMENT",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF5D4037),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Stone Age Grammar Academy",
                            style = MaterialTheme.typography.bodyMedium,
                            color = CampfireAmber,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "This is proudly presented to:",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF8D6E63)
                        )

                        Text(
                            text = result.studentName,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = LavenderDark,
                            fontSize = 26.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "for outstanding exploration, courage, and mastery in\nTHE PRESENT SIMPLE TENSE ADVENTURE!",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            color = Color(0xFF4E342E),
                            lineHeight = 20.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Column(horizontalAlignment = Alignment.Start) {
                                Text(text = "Date: ${result.dateString.take(12)}", fontSize = 12.sp, color = Color(0xFF8D6E63))
                                Text(text = "Level: ${result.level.title}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = LavenderPrimary)
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "Shaimaa Owiess 💜",
                                    fontFamily = FontFamily.Cursive,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TeacherHeartPurple
                                )
                                Text(
                                    text = "Teacher & Guide",
                                    fontSize = 11.sp,
                                    color = Color(0xFF757575)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Share Certificate Button
                        OutlinedButton(
                            onClick = {
                                val certText = """
                                    📜 CERTIFICATE OF ACHIEVEMENT 📜
                                    Presented to: ${result.studentName}
                                    For mastering Present Simple in the Stone Age Adventure!
                                    Score: ${result.scorePercentage}% (${result.level.title})
                                    Teacher: Shaimaa Owiess 💜
                                    "Learn with love. Dream big. Shine bright!"
                                """.trimIndent()
                                val sendIntent = Intent(Intent.ACTION_SEND).apply {
                                    putExtra(Intent.EXTRA_TEXT, certText)
                                    type = "text/plain"
                                }
                                context.startActivity(Intent.createChooser(sendIntent, "Share Certificate"))
                            },
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(1.5.dp, LavenderPrimary)
                        ) {
                            Icon(imageVector = Icons.Default.Share, contentDescription = null, tint = LavenderPrimary)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "Share Certificate 📤", color = LavenderPrimary, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Final Teacher Quote Card
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color(0xFFF3E5F5),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "🌸 💜 🌟", fontSize = 24.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "“My amazing learner, Always remember:\nYou don't have to be perfect to learn.\nYou just have to keep trying.\nEvery mistake helps your brain grow.\nI am so proud of you!\nLove, Shaimaa Owiess 💜”",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            fontStyle = FontStyle.Italic,
                            color = LavenderDark,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))

                // Actions: Teacher Dashboard & Restart
                Button(
                    onClick = onOpenTeacherDashboard,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .testTag("open_teacher_dashboard_from_report"),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = LavenderPrimary)
                ) {
                    Text(
                        text = "View 5-Sheet Teacher Excel Report 📊",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedButton(
                    onClick = onRestartAdventure,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("restart_adventure_button"),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.5.dp, CampfireAmber)
                ) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = null, tint = CampfireAmber)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Start New Adventure 🔄",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = CampfireAmber
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

@Composable
private fun StatBox(label: String, value: String, emoji: String) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFF9F7F5)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = emoji, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = LavenderDark
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF757575)
            )
        }
    }
}

@Composable
private fun MiniGameLauncherButton(
    title: String,
    icon: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFFFF3E0),
        border = BorderStroke(1.dp, Color(0xFFFFCC80)),
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = icon, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE65100),
                textAlign = TextAlign.Center
            )
        }
    }
}
