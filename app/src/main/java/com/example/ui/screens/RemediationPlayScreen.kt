package com.example.ui.screens

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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.data.model.RemediationGameType
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
fun RemediationPlayScreen(
    gameType: RemediationGameType,
    onBack: () -> Unit,
    onSpeakTeacher: (String, Boolean, Boolean) -> Unit,
    onPlaySuccessTone: () -> Unit,
    onPlaySupportTone: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize().testTag("remediation_play_screen"),
        color = PrehistoricPaper
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 4.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = LavenderDark)
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = gameType.icon, fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = gameType.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = LavenderDark
                        )
                        Text(
                            text = gameType.subtitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF757575)
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                when (gameType) {
                    RemediationGameType.BUILD_SENTENCE -> BuildSentenceGame(
                        onSpeakTeacher = onSpeakTeacher,
                        onPlaySuccessTone = onPlaySuccessTone,
                        onPlaySupportTone = onPlaySupportTone
                    )
                    RemediationGameType.CAMPFIRE_CHALLENGE -> CampfireGame(
                        onSpeakTeacher = onSpeakTeacher,
                        onPlaySuccessTone = onPlaySuccessTone,
                        onPlaySupportTone = onPlaySupportTone
                    )
                    else -> FeedDinoGame(
                        onSpeakTeacher = onSpeakTeacher,
                        onPlaySuccessTone = onPlaySuccessTone,
                        onPlaySupportTone = onPlaySupportTone
                    )
                }
            }
        }
    }
}

@Composable
private fun BuildSentenceGame(
    onSpeakTeacher: (String, Boolean, Boolean) -> Unit,
    onPlaySuccessTone: () -> Unit,
    onPlaySupportTone: () -> Unit
) {
    val tasks = remember { StoneAgeContent.sentenceBuilderTasks }
    var taskIndex by remember { mutableIntStateOf(0) }
    val currentTask = tasks[taskIndex]

    val placedWords = remember(taskIndex) { mutableStateListOf<String>() }
    var isSuccess by remember(taskIndex) { mutableStateOf<Boolean?>(null) }

    val remainingWords = remember(taskIndex, placedWords.toList()) {
        val countInPlaced = placedWords.groupingBy { it }.eachCount()
        currentTask.scrambledWords.filter { word ->
            val placedCount = countInPlaced[word] ?: 0
            val initialCount = currentTask.scrambledWords.count { it == word }
            placedCount < initialCount
        }
    }

    LaunchedEffect(taskIndex) {
        onSpeakTeacher("Tap the stone word blocks in the right order! Hint: ${currentTask.hint}", false, false)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Card(
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "🪨 Stone Word Builder 🪨", fontWeight = FontWeight.Bold, color = CampfireAmber)
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Arrange the stone blocks to make a correct Present Simple sentence:",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF616161)
                )
                Spacer(modifier = Modifier.height(10.dp))

                // Teacher Hint
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = LavenderLight,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "💡 Shaimaa's Hint: ${currentTask.hint}",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        color = LavenderDark,
                        modifier = Modifier.padding(10.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Sentence Construction Area (Placed Blocks)
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = Color(0xFFF5F0EB),
                    border = BorderStroke(2.dp, if (isSuccess == true) SuccessGreen else LavenderPrimary),
                    modifier = Modifier.fillMaxWidth().height(90.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize().padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        if (placedWords.isEmpty()) {
                            Text(text = "Tap stone word blocks below...", color = Color(0xFF9E9E9E), fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
                        } else {
                            placedWords.forEach { word ->
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = LavenderPrimary,
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp)
                                        .clickable { placedWords.remove(word); isSuccess = null }
                                ) {
                                    Text(
                                        text = word,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Available Stone Word Blocks
                Text(text = "Available Stone Blocks:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF757575))
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    remainingWords.forEach { word ->
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = Color(0xFFECEFF1),
                            border = BorderStroke(1.5.dp, Color(0xFFB0BEC5)),
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .clickable {
                                    placedWords.add(word)
                                    isSuccess = null
                                }
                        ) {
                            Text(
                                text = word,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF37474F),
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Check button
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedButton(
                        onClick = { placedWords.clear(); isSuccess = null },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Reset")
                    }

                    Button(
                        onClick = {
                            val built = placedWords.joinToString(" ")
                            if (built.equals(currentTask.targetSentence, ignoreCase = true)) {
                                isSuccess = true
                                onPlaySuccessTone()
                                onSpeakTeacher("Awesome job! You built the stone sentence perfectly!", true, false)
                            } else {
                                isSuccess = false
                                onPlaySupportTone()
                                onSpeakTeacher("Almost! Let's reset and try the blocks again.", false, true)
                            }
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = LavenderPrimary)
                    ) {
                        Text("Check 🎯", fontWeight = FontWeight.Bold)
                    }
                }

                if (isSuccess == true) {
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(text = "🎉 Perfect Sentence Built!", color = SuccessGreen, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    if (taskIndex + 1 < tasks.size) {
                        Button(
                            onClick = { taskIndex += 1; isSuccess = null },
                            colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen)
                        ) {
                            Text("Next Sentence ➡️")
                        }
                    }
                } else if (isSuccess == false) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "Keep trying! Remember: ${currentTask.hint}", color = Color(0xFFD32F2F), fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
private fun CampfireGame(
    onSpeakTeacher: (String, Boolean, Boolean) -> Unit,
    onPlaySuccessTone: () -> Unit,
    onPlaySupportTone: () -> Unit
) {
    val tasks = remember { StoneAgeContent.campfireTasks }
    var taskIndex by remember { mutableIntStateOf(0) }
    val task = tasks[taskIndex]
    var feedback by remember(taskIndex) { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Card(
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "🔥 Campfire Verb Challenge 🔥", fontWeight = FontWeight.Bold, color = CampfireAmber)
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = task.prompt,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF3E2723)
                )

                Spacer(modifier = Modifier.height(20.dp))

                task.options.forEachIndexed { idx, opt ->
                    Button(
                        onClick = {
                            if (idx == task.correctIndex) {
                                feedback = "Correct! 🔥 The campfire burns bright! ${task.explanation}"
                                onPlaySuccessTone()
                                onSpeakTeacher("Wonderful! The campfire is glowing brightly!", true, false)
                            } else {
                                feedback = "Oops! That cools the fire. ${task.explanation}"
                                onPlaySupportTone()
                                onSpeakTeacher("Let's try again! ${task.explanation}", false, true)
                            }
                        },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = CampfireOrange)
                    ) {
                        Text(text = opt, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }

                feedback?.let { msg ->
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = msg,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (msg.startsWith("Correct")) SuccessGreen else Color(0xFFD32F2F),
                        textAlign = TextAlign.Center
                    )
                    if (msg.startsWith("Correct") && taskIndex + 1 < tasks.size) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = { taskIndex += 1; feedback = null },
                            colors = ButtonDefaults.buttonColors(containerColor = LavenderPrimary)
                        ) {
                            Text("Next Campfire Round 🔥")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FeedDinoGame(
    onSpeakTeacher: (String, Boolean, Boolean) -> Unit,
    onPlaySuccessTone: () -> Unit,
    onPlaySupportTone: () -> Unit
) {
    val tasks = remember { StoneAgeContent.dinoFoodTasks }
    var taskIndex by remember { mutableIntStateOf(0) }
    val task = tasks[taskIndex]
    var feedback by remember(taskIndex) { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Card(
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "🦕 Feed Friendly Dinosaur 🦕", fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "🦖 \"Hungry! Give me a correct sentence berry!\"", fontStyle = androidx.compose.ui.text.font.FontStyle.Italic, color = Color(0xFF5D4037))
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = task.prompt,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = LavenderDark
                )

                Spacer(modifier = Modifier.height(20.dp))

                task.options.forEachIndexed { idx, opt ->
                    Button(
                        onClick = {
                            if (idx == task.correctIndex) {
                                feedback = "Yummy! 🦕 Dino loves this correct sentence! ${task.explanation}"
                                onPlaySuccessTone()
                                onSpeakTeacher("Yum! Dino is happy and healthy!", true, false)
                            } else {
                                feedback = "Oh no! Dino got a stomach ache from bad grammar! ${task.explanation}"
                                onPlaySupportTone()
                                onSpeakTeacher("Dino needs the correct sentence! Let's try again.", false, true)
                            }
                        },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF43A047))
                    ) {
                        Text(text = opt, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    }
                }

                feedback?.let { msg ->
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = msg,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (msg.startsWith("Yummy")) SuccessGreen else Color(0xFFD32F2F),
                        textAlign = TextAlign.Center
                    )
                    if (msg.startsWith("Yummy") && taskIndex + 1 < tasks.size) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = { taskIndex += 1; feedback = null },
                            colors = ButtonDefaults.buttonColors(containerColor = LavenderPrimary)
                        ) {
                            Text("Feed Next Berry 🦕")
                        }
                    }
                }
            }
        }
    }
}
