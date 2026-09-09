package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.data.datasource.StoneAgeContent
import com.example.data.model.GrammarQuestion
import com.example.data.model.GrammarZone
import com.example.data.model.StudentAnswer
import com.example.ui.components.CorrectFeedbackDialog
import com.example.ui.components.GentleSupportFeedbackDialog
import com.example.ui.components.InteractiveQuestionCard
import com.example.ui.theme.CampfireAmber
import com.example.ui.theme.CampfireOrange
import com.example.ui.theme.GoldenStar
import com.example.ui.theme.LavenderDark
import com.example.ui.theme.LavenderLight
import com.example.ui.theme.LavenderPrimary
import com.example.ui.theme.PrehistoricPaper
import com.example.ui.theme.TeacherHeartPurple

@Composable
fun AdventureZoneScreen(
    zone: GrammarZone,
    onAnswerSubmitted: (StudentAnswer) -> Unit,
    onZoneCompleted: (nextZone: GrammarZone) -> Unit,
    onSpeakTeacher: (String, Boolean, Boolean) -> Unit,
    onPlaySuccessTone: () -> Unit,
    onPlaySupportTone: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lesson = remember(zone) {
        StoneAgeContent.zoneLessons.firstOrNull { it.zone == zone }
            ?: StoneAgeContent.zoneLessons[0]
    }

    var currentQuestionIndex by remember(zone) { mutableIntStateOf(0) }
    var questionStartTime by remember(zone, currentQuestionIndex) { mutableStateOf(System.currentTimeMillis()) }

    var showCorrectDialog by remember { mutableStateOf(false) }
    var showSupportDialog by remember { mutableStateOf(false) }
    var lastFeedbackMessage by remember { mutableStateOf("") }
    var lastExplanation by remember { mutableStateOf("") }
    var lastCorrectAnswer by remember { mutableStateOf("") }

    val infiniteTransition = rememberInfiniteTransition(label = "letter_s_anim")
    val giantSScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.22f,
        animationSpec = infiniteRepeatable(
            animation = tween(650),
            repeatMode = RepeatMode.Reverse
        ),
        label = "giantS"
    )

    LaunchedEffect(zone) {
        onSpeakTeacher(lesson.teacherIntro, false, false)
    }

    val currentQuestion = lesson.checkpointQuestions.getOrNull(currentQuestionIndex)

    Box(modifier = modifier.fillMaxSize().background(PrehistoricPaper)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Zone Header Banner
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("zone_banner_${zone.id}"),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(zone.themeColorHex)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = zone.emoji, fontSize = 28.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "ZONE ${zone.id}: ${zone.title.uppercase()}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = zone.subtitle,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color(0xFFFFF8E1),
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }

                    IconButton(
                        onClick = {
                            val narration = "${zone.title}. ${lesson.teacherIntro}. ${lesson.characterDialogue}"
                            onSpeakTeacher(narration, false, false)
                        },
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Color(0x33FFFFFF))
                    ) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "Read zone story",
                            tint = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Teacher Shaimaa Story Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "⭐", fontSize = 18.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Teacher Shaimaa Owiess says:",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = LavenderDark
                            )
                        }
                        IconButton(
                            onClick = { onSpeakTeacher(lesson.teacherIntro, false, false) },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.VolumeUp,
                                contentDescription = "Listen",
                                tint = LavenderPrimary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = lesson.teacherIntro,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF37474F),
                        lineHeight = 24.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Character Storytelling & Dialogue
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFDF5)),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFFFECB3))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "📜 Stone Age Story",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = CampfireAmber
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = lesson.characterDialogue,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF4E342E),
                        lineHeight = 24.sp,
                        fontWeight = FontWeight.Medium
                    )

                    // Special Cave Animated Giant 'S' visual
                    if (zone == GrammarZone.CAVE) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(18.dp))
                                .background(Color(0xFFEDE7F6))
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "🪨 The Stone Age Secret: HE / SHE / IT 🪨",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = LavenderDark
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "S",
                                    fontSize = 58.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = TeacherHeartPurple,
                                    modifier = Modifier.scale(giantSScale)
                                )
                                Text(
                                    text = "Adam plays • Sara reads • The cat sleeps ✨",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = LavenderDark
                                )
                            }
                        }
                    }

                    // Special Forest Grammar Monster Visual
                    if (zone == GrammarZone.FOREST) {
                        Spacer(modifier = Modifier.height(14.dp))
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = Color(0xFFFFEBEE),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "👾", fontSize = 32.sp)
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "Grammar Monster Trick:",
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFC62828),
                                        fontSize = 13.sp
                                    )
                                    Text(
                                        text = "Monster says: \"He doesn't plays!\"\nYou fix it: \"He doesn't play!\" ✅",
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color(0xFFB71C1C),
                                        fontSize = 13.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Key Rules Checklist
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "💡 Stone Age Grammar Secret:",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = LavenderDark
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    lesson.keyRules.forEach { rule ->
                        Row(
                            modifier = Modifier.padding(vertical = 3.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(text = "✨", fontSize = 13.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = rule,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF424242),
                                lineHeight = 20.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Checkpoint Question Section
            if (currentQuestion != null) {
                Text(
                    text = "🎯 Zone ${zone.id} Checkpoint Challenge (${currentQuestionIndex + 1}/${lesson.checkpointQuestions.size}):",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = LavenderDark
                )

                Spacer(modifier = Modifier.height(10.dp))

                InteractiveQuestionCard(
                    question = currentQuestion,
                    onOptionSelected = { selectedIdx, selectedText ->
                        val isCorrect = selectedIdx == currentQuestion.correctOptionIndex
                        val responseTime = System.currentTimeMillis() - questionStartTime

                        val answer = StudentAnswer(
                            questionId = currentQuestion.id,
                            questionText = currentQuestion.questionText,
                            selectedIndex = selectedIdx,
                            selectedOption = selectedText,
                            correctOption = currentQuestion.correctOption,
                            isCorrect = isCorrect,
                            skill = currentQuestion.skill,
                            explanation = currentQuestion.explanation,
                            responseTimeMs = responseTime
                        )
                        onAnswerSubmitted(answer)

                        if (isCorrect) {
                            onPlaySuccessTone()
                            lastFeedbackMessage = StoneAgeContent.correctEncouragements.random()
                            lastExplanation = currentQuestion.explanation
                            showCorrectDialog = true
                        } else {
                            onPlaySupportTone()
                            lastFeedbackMessage = StoneAgeContent.supportiveMistakeMessages.random()
                            lastCorrectAnswer = currentQuestion.correctOption
                            lastExplanation = currentQuestion.explanation
                            showSupportDialog = true
                        }
                    },
                    onSpeakQuestion = { speech ->
                        onSpeakTeacher(speech, false, false)
                    }
                )
            } else {
                // Zone Completed Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(22.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "🎉 ⭐ 🪨", fontSize = 36.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Zone ${zone.id} Cleared!",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E7D32)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "You mastered ${zone.title}!\nTeacher Shaimaa is cheering for you! 💜",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color(0xFF1B5E20)
                        )
                        Spacer(modifier = Modifier.height(18.dp))

                        val nextZone = GrammarZone.fromId(zone.id + 1)
                        Button(
                            onClick = { onZoneCompleted(nextZone) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp)
                                .testTag("go_to_next_zone_btn"),
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = CampfireOrange
                            )
                        ) {
                            Text(
                                text = if (nextZone == GrammarZone.CHALLENGE) "Enter The Great Grammar Challenge! 🏆" else "Travel to ${nextZone.title} ${nextZone.emoji}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Dialogs for Immediate Feedback
        if (showCorrectDialog) {
            CorrectFeedbackDialog(
                encouragementMessage = lastFeedbackMessage,
                explanation = lastExplanation,
                onDismiss = {
                    showCorrectDialog = false
                    if (currentQuestionIndex + 1 < lesson.checkpointQuestions.size) {
                        currentQuestionIndex += 1
                        questionStartTime = System.currentTimeMillis()
                    } else {
                        currentQuestionIndex += 1 // marks completed
                    }
                },
                onSpeak = { text -> onSpeakTeacher(text, true, false) }
            )
        }

        if (showSupportDialog) {
            GentleSupportFeedbackDialog(
                correctAnswer = lastCorrectAnswer,
                explanation = lastExplanation,
                supportMessage = lastFeedbackMessage,
                onDismiss = {
                    showSupportDialog = false
                    if (currentQuestionIndex + 1 < lesson.checkpointQuestions.size) {
                        currentQuestionIndex += 1
                        questionStartTime = System.currentTimeMillis()
                    } else {
                        currentQuestionIndex += 1 // marks completed
                    }
                },
                onSpeak = { text -> onSpeakTeacher(text, false, true) }
            )
        }
    }
}
