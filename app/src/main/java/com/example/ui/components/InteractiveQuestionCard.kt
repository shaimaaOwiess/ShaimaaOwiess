package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.GrammarQuestion
import com.example.ui.theme.CampfireAmber
import com.example.ui.theme.LavenderDark
import com.example.ui.theme.LavenderLight
import com.example.ui.theme.LavenderPrimary
import com.example.ui.theme.PrehistoricTextPrimary
import com.example.ui.theme.StoneWarm

@Composable
fun InteractiveQuestionCard(
    question: GrammarQuestion,
    onOptionSelected: (selectedIndex: Int, optionText: String) -> Unit,
    onSpeakQuestion: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var lastClickTime by remember { mutableLongStateOf(0L) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("question_card_${question.id}"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Context header: Character avatar & Story setting
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFFFFF9EE))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(LavenderLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = question.characterAvatar.take(2),
                            fontSize = 18.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = question.characterAvatar,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = LavenderDark,
                            fontSize = 14.sp
                        )
                        Text(
                            text = question.storyContext,
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 12.sp,
                            color = Color(0xFF6D4C41)
                        )
                    }
                }

                // Read aloud button
                IconButton(
                    onClick = {
                        val fullSpeech = "${question.characterAvatar} asks: ${question.questionText}"
                        onSpeakQuestion(fullSpeech)
                    },
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(LavenderLight)
                        .testTag("speak_question_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.VolumeUp,
                        contentDescription = "Read question aloud with Teacher Shaimaa's voice",
                        tint = LavenderPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Question prompt
            Text(
                text = question.questionText,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = PrehistoricTextPrimary,
                fontSize = 20.sp,
                lineHeight = 28.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Interactive Options
            val optionLetters = listOf("A", "B", "C", "D")
            question.options.forEachIndexed { index, option ->
                val letter = optionLetters.getOrElse(index) { "${index + 1}" }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .clickable {
                            val now = System.currentTimeMillis()
                            // Debounce to prevent accidental double-clicks!
                            if (now - lastClickTime > 600) {
                                lastClickTime = now
                                onOptionSelected(index, option)
                            }
                        }
                        .testTag("question_option_$index"),
                    shape = RoundedCornerShape(18.dp),
                    border = BorderStroke(1.5.dp, StoneWarm),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFCFAF7)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Letter Pill (A, B, C)
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.linearGradient(
                                        listOf(LavenderPrimary, LavenderDark)
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = letter,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        // Option text
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = PrehistoricTextPrimary,
                            fontSize = 17.sp,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}
