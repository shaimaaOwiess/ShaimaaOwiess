package com.example.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.datasource.StoneAgeContent
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
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TeacherHeartPurple

@Composable
fun GreatGrammarChallengeScreen(
    onChallengeCompleted: (answers: List<StudentAnswer>, totalDurationSeconds: Long) -> Unit,
    onSpeakTeacher: (String, Boolean, Boolean) -> Unit,
    onPlaySuccessTone: () -> Unit,
    onPlaySupportTone: () -> Unit,
    modifier: Modifier = Modifier
) {
    val questions = remember { StoneAgeContent.challengeQuestions }
    var currentIndex by remember { mutableIntStateOf(0) }
    var questionStartTime by remember { mutableLongStateOf(System.currentTimeMillis()) }
    val assessmentStartTime = remember { System.currentTimeMillis() }

    val collectedAnswers = remember { mutableStateListOf<StudentAnswer>() }

    var showCorrectDialog by remember { mutableStateOf(false) }
    var showSupportDialog by remember { mutableStateOf(false) }
    var lastFeedbackMessage by remember { mutableStateOf("") }
    var lastExplanation by remember { mutableStateOf("") }
    var lastCorrectAnswer by remember { mutableStateOf("") }

    val currentQuestion = questions.getOrNull(currentIndex)
    val progress by animateFloatAsState(
        targetValue = if (questions.isNotEmpty()) (currentIndex.toFloat() / questions.size.toFloat()) else 0f,
        label = "challenge_progress"
    )

    LaunchedEffect(currentIndex) {
        if (currentIndex == 0) {
            onSpeakTeacher(
                "Welcome to the Great Grammar Challenge! 🏆 Show everything you have discovered. Take your time, do your best, and remember I believe in you!",
                true,
                false
            )
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PrehistoricPaper)
            .testTag("grammar_challenge_screen")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Header Banner
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFF8F00)),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(Color(0x33FFFFFF)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "🏆", fontSize = 24.sp)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "THE GREAT GRAMMAR CHALLENGE",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 15.sp
                            )
                            Text(
                                text = "Zone 6: Master Present Simple",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFFFFF8E1)
                            )
                        }
                    }

                    // Score pill
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color.White
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "⭐", fontSize = 14.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            val correctCount = collectedAnswers.count { it.isCorrect }
                            Text(
                                text = "$correctCount / ${questions.size}",
                                fontWeight = FontWeight.Bold,
                                color = CampfireAmber,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Progress Bar & Counter
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Question ${currentIndex + 1} of ${questions.size}",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = LavenderDark
                )

                Text(
                    text = "${(progress * 100).toInt()}% Done",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = CampfireOrange
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(RoundedCornerShape(5.dp)),
                color = LavenderPrimary,
                trackColor = Color(0xFFE0E0E0)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Skill Focus Badge
            if (currentQuestion != null) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .background(LavenderLight)
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "🎯 Skill: ", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = LavenderDark)
                        Text(
                            text = currentQuestion.skill.displayName,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TeacherHeartPurple
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // The Question Card
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
                        collectedAnswers.add(answer)

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
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Dialogs
        if (showCorrectDialog) {
            CorrectFeedbackDialog(
                encouragementMessage = lastFeedbackMessage,
                explanation = lastExplanation,
                onDismiss = {
                    showCorrectDialog = false
                    if (currentIndex + 1 < questions.size) {
                        currentIndex += 1
                        questionStartTime = System.currentTimeMillis()
                    } else {
                        // Assessment complete!
                        val totalDurationSec = (System.currentTimeMillis() - assessmentStartTime) / 1000
                        onChallengeCompleted(collectedAnswers.toList(), totalDurationSec)
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
                    if (currentIndex + 1 < questions.size) {
                        currentIndex += 1
                        questionStartTime = System.currentTimeMillis()
                    } else {
                        val totalDurationSec = (System.currentTimeMillis() - assessmentStartTime) / 1000
                        onChallengeCompleted(collectedAnswers.toList(), totalDurationSec)
                    }
                },
                onSpeak = { text -> onSpeakTeacher(text, false, true) }
            )
        }
    }
}
