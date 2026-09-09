package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.StudentSessionEntity
import com.example.data.model.AssessmentResult
import com.example.data.model.SkillStatus
import com.example.data.repository.StudentProgressRepository
import com.example.ui.theme.CampfireAmber
import com.example.ui.theme.GoldenStar
import com.example.ui.theme.LavenderDark
import com.example.ui.theme.LavenderLight
import com.example.ui.theme.LavenderPrimary
import com.example.ui.theme.PrehistoricPaper
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TeacherHeartPurple
import kotlinx.coroutines.flow.Flow

@Composable
fun TeacherDashboardScreen(
    currentResult: AssessmentResult?,
    pastSessionsFlow: Flow<List<StudentSessionEntity>>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedTab by remember { mutableIntStateOf(0) }
    val pastSessions by pastSessionsFlow.collectAsState(initial = emptyList())

    val sheetTitles = listOf(
        "Sheet 1: Summary",
        "Sheet 2: Questions",
        "Sheet 3: Skills",
        "Sheet 4: Weak Areas",
        "Sheet 5: Plan",
        "Past Sessions"
    )

    Surface(
        modifier = modifier.fillMaxSize().testTag("teacher_dashboard_screen"),
        color = PrehistoricPaper
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Dashboard App Bar
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 4.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = onBack) {
                                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = LavenderDark)
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            Column {
                                Text(
                                    text = "Teacher Assessment Dashboard",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = LavenderDark,
                                    fontSize = 18.sp
                                )
                                Text(
                                    text = "Shaimaa Owiess • 5-Sheet Excel Reporting",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TeacherHeartPurple
                                )
                            }
                        }

                        // Export Button
                        if (currentResult != null) {
                            Button(
                                onClick = {
                                    val csvData = StudentProgressRepository.generateExcelCsvReport(currentResult)
                                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    val clip = ClipData.newPlainText("Teacher Report CSV", csvData)
                                    clipboard.setPrimaryClip(clip)
                                    Toast.makeText(context, "Copied Excel CSV to Clipboard! 📋", Toast.LENGTH_SHORT).show()

                                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                        putExtra(Intent.EXTRA_TEXT, csvData)
                                        type = "text/plain"
                                    }
                                    context.startActivity(Intent.createChooser(shareIntent, "Share Excel CSV Report"))
                                },
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen),
                                modifier = Modifier.testTag("export_excel_button")
                            ) {
                                Icon(imageVector = Icons.Default.FileDownload, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "Export Excel", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Tab Row
                    ScrollableTabRow(
                        selectedTabIndex = selectedTab,
                        edgePadding = 0.dp,
                        containerColor = Color.Transparent,
                        indicator = { tabPositions ->
                            TabRowDefaults.SecondaryIndicator(
                                Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                                color = LavenderPrimary
                            )
                        }
                    ) {
                        sheetTitles.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTab == index,
                                onClick = { selectedTab = index },
                                text = {
                                    Text(
                                        text = title,
                                        fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                                        color = if (selectedTab == index) LavenderPrimary else Color(0xFF757575),
                                        fontSize = 13.sp
                                    )
                                }
                            )
                        }
                    }
                }
            }

            // Body content per tab
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                if (currentResult == null && selectedTab < 5) {
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(top = 40.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "📊", fontSize = 42.sp)
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "No Active Assessment Session Yet",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = LavenderDark
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Start an adventure session and complete Zone 6 to generate live student analytics, error classifications, and remediation plans.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF757575),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                } else if (currentResult != null) {
                    when (selectedTab) {
                        0 -> Sheet1StudentSummary(currentResult)
                        1 -> Sheet2QuestionAnalysis(currentResult)
                        2 -> Sheet3SkillAnalysis(currentResult)
                        3 -> Sheet4WeakAreas(currentResult)
                        4 -> Sheet5PersonalizedPlan(currentResult)
                        5 -> SheetPastSessions(pastSessions)
                    }
                } else {
                    SheetPastSessions(pastSessions)
                }
            }
        }
    }
}

@Composable
private fun Sheet1StudentSummary(result: AssessmentResult) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "SHEET 1 — STUDENT SUMMARY",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = LavenderDark
                )
                Spacer(modifier = Modifier.height(14.dp))

                SummaryRow("Student Name", result.studentName)
                SummaryRow("Date & Time", result.dateString)
                SummaryRow("Total Questions", "${result.totalQuestions}")
                SummaryRow("Correct Answers", "${result.correctAnswers} ✅")
                SummaryRow("Incorrect Answers", "${result.incorrectAnswers} ❌")
                SummaryRow("Score Ratio", "${result.correctAnswers}/${result.totalQuestions}")
                SummaryRow("Percentage", "${result.scorePercentage}%")
                SummaryRow("Final Level", "${result.level.badgeEmoji} ${result.level.title}")
                SummaryRow("Time Spent", "${result.timeSpentSeconds} seconds")
                SummaryRow("Strongest Skill", result.strongestSkill.displayName)
                SummaryRow("Weakest Skill", result.weakestSkill.displayName)
                SummaryRow(
                    "Recommended Action",
                    if (result.scorePercentage >= 80) "Celebrate achievement and reinforce with reading" else "Complete personalized remediation mini-games"
                )
            }
        }
    }
}

@Composable
private fun Sheet2QuestionAnalysis(result: AssessmentResult) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "SHEET 2 — QUESTION ANALYSIS (${result.allAnswers.size} Items)",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = LavenderDark
        )
        Spacer(modifier = Modifier.height(10.dp))

        result.allAnswers.forEachIndexed { idx, ans ->
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Q${idx + 1}: ${ans.skill.displayName}",
                            fontWeight = FontWeight.Bold,
                            color = LavenderDark,
                            fontSize = 13.sp
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (ans.isCorrect) Color(0xFFE8F5E9) else Color(0xFFFFEBEE))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = if (ans.isCorrect) "CORRECT" else "INCORRECT",
                                fontWeight = FontWeight.Bold,
                                color = if (ans.isCorrect) Color(0xFF2E7D32) else Color(0xFFC62828),
                                fontSize = 11.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = ans.questionText,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF212121)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row {
                        Text(text = "Chosen: ", fontSize = 12.sp, color = Color(0xFF757575))
                        Text(text = ans.selectedOption, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = if (ans.isCorrect) SuccessGreen else Color(0xFFD32F2F))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(text = "Correct: ", fontSize = 12.sp, color = Color(0xFF757575))
                        Text(text = ans.correctOption, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SuccessGreen)
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "💡 ${ans.explanation}",
                        fontSize = 12.sp,
                        color = Color(0xFF616161)
                    )
                }
            }
        }
    }
}

@Composable
private fun Sheet3SkillAnalysis(result: AssessmentResult) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "SHEET 3 — SKILL ACCURACY & STATUS",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = LavenderDark
        )
        Spacer(modifier = Modifier.height(10.dp))

        result.skillScores.forEach { s ->
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = s.skill.displayName,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = LavenderDark
                            )
                            Text(
                                text = "${s.correctCount}/${s.totalQuestions} correct (${s.accuracyPercentage}%)",
                                fontSize = 12.sp,
                                color = Color(0xFF757575)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    when (s.status) {
                                        SkillStatus.STRONG -> Color(0xFFE8F5E9)
                                        SkillStatus.DEVELOPING -> Color(0xFFFFF9C4)
                                        SkillStatus.NEEDS_PRACTICE -> Color(0xFFFFEBEE)
                                    }
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "${s.status.icon} ${s.status.label}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = when (s.status) {
                                    SkillStatus.STRONG -> Color(0xFF2E7D32)
                                    SkillStatus.DEVELOPING -> Color(0xFFF57F17)
                                    SkillStatus.NEEDS_PRACTICE -> Color(0xFFC62828)
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    LinearProgressIndicator(
                        progress = { s.accuracyPercentage / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = when (s.status) {
                            SkillStatus.STRONG -> SuccessGreen
                            SkillStatus.DEVELOPING -> CampfireAmber
                            SkillStatus.NEEDS_PRACTICE -> Color(0xFFD32F2F)
                        },
                        trackColor = Color(0xFFEEEEEE)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Tip: ${s.recommendation}",
                        fontSize = 12.sp,
                        color = Color(0xFF616161)
                    )
                }
            }
        }
    }
}

@Composable
private fun Sheet4WeakAreas(result: AssessmentResult) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "SHEET 4 — ERROR CLASSIFICATION & REMEDIATION",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = LavenderDark
        )
        Spacer(modifier = Modifier.height(10.dp))

        if (result.weakAreas.isEmpty()) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "🌟", fontSize = 28.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(text = "Zero Weak Areas Detected!", fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                        Text(text = "The student achieved over 80% across all evaluated Present Simple skills.", fontSize = 12.sp, color = Color(0xFF1B5E20))
                    }
                }
            }
        } else {
            result.weakAreas.forEach { w ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = w.skill.displayName,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFC62828)
                            )
                            Text(
                                text = "${w.mistakeCount} errors",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFD32F2F),
                                fontSize = 12.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Root Cause: ${w.whyStruggled}",
                            fontSize = 13.sp,
                            color = Color(0xFF424242)
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFFFFF8E1),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(text = "Recommended Remediation Game:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = CampfireAmber)
                                Text(text = w.remediationGameTitle, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF5D4037))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Sheet5PersonalizedPlan(result: AssessmentResult) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "SHEET 5 — SHAIMAA'S PERSONALIZED PLAN",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = LavenderDark
                )
                Spacer(modifier = Modifier.height(14.dp))

                SummaryRow("Student", result.studentName)
                SummaryRow("Strengths", "${result.strongestSkill.displayName} (High mastery)")
                SummaryRow("Areas to Target", result.weakAreas.joinToString("; ") { it.skill.displayName }.ifBlank { "All skills strong" })
                SummaryRow("Daily Practice", "10 minutes of Present Simple routine sentences")
                SummaryRow("Recommended Mini-Game", result.weakAreas.firstOrNull()?.remediationGameTitle ?: "Feed the Friendly Dinosaur")
                SummaryRow("Shaimaa's Note", result.teacherSpecialMessage)
                SummaryRow("Next Assessment", "In 3 days with Teacher Shaimaa Owiess")
            }
        }
    }
}

@Composable
private fun SheetPastSessions(sessions: List<StudentSessionEntity>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "STUDENT ATTEMPT HISTORY (${sessions.size} Sessions)",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = LavenderDark
        )
        Spacer(modifier = Modifier.height(10.dp))

        if (sessions.isEmpty()) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "📂", fontSize = 32.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "No saved student sessions yet in local database.", color = Color(0xFF757575), fontSize = 13.sp)
                }
            }
        } else {
            sessions.forEach { sess ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(text = sess.studentName, fontWeight = FontWeight.Bold, color = LavenderDark, fontSize = 15.sp)
                            Text(text = "Score: ${sess.correctAnswers}/${sess.totalQuestions} (${sess.scorePercentage}%) • ${sess.timeSpentSeconds}s", fontSize = 12.sp, color = Color(0xFF616161))
                            Text(text = "Level: ${sess.levelName}", fontSize = 11.sp, color = CampfireAmber, fontWeight = FontWeight.SemiBold)
                        }

                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(LavenderLight),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "${sess.scorePercentage}%", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = LavenderPrimary)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SummaryRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 5.dp)) {
        Text(text = label, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF8D6E63))
        Text(text = value, fontSize = 14.sp, color = Color(0xFF212121), fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(4.dp))
        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color(0xFFEEEEEE)))
    }
}
