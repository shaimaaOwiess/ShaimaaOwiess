package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.ui.AppScreen
import com.example.ui.StoneAgeViewModel
import com.example.ui.components.StoneAgeHeader
import com.example.ui.screens.AdventureZoneScreen
import com.example.ui.screens.AssessmentReportScreen
import com.example.ui.screens.GreatGrammarChallengeScreen
import com.example.ui.screens.RemediationPlayScreen
import com.example.ui.screens.TeacherDashboardScreen
import com.example.ui.screens.WelcomeScreen
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
  private val viewModel: StoneAgeViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        StoneAgeApp(viewModel = viewModel)
      }
    }
  }
}

@Composable
fun StoneAgeApp(viewModel: StoneAgeViewModel) {
  val uiState by viewModel.uiState.collectAsState()

  Scaffold(
    modifier = Modifier.fillMaxSize()
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      // Top header shown during adventure and challenge zones
      if (uiState.currentScreen == AppScreen.ADVENTURE_ZONE || uiState.currentScreen == AppScreen.CHALLENGE_ZONE) {
        StoneAgeHeader(
          currentZone = uiState.currentZone,
          unlockedZoneId = uiState.unlockedZoneId,
          studentName = uiState.studentName,
          score = uiState.scoreStars,
          isSpeaking = uiState.isSpeaking,
          isMuted = uiState.isMuted,
          onToggleMute = { viewModel.toggleMute() },
          onZoneClick = { zone -> viewModel.navigateToZone(zone) },
          onOpenDashboard = { viewModel.openTeacherDashboard() }
        )
      }

      Box(modifier = Modifier.weight(1f)) {
        when (uiState.currentScreen) {
          AppScreen.WELCOME -> WelcomeScreen(
            initialName = uiState.studentName,
            onStartAdventure = { name, avatar -> viewModel.startAdventure(name, avatar) },
            onOpenDashboard = { viewModel.openTeacherDashboard() },
            onSpeakTeacher = { text -> viewModel.speakTeacher(text, false, false) }
          )

          AppScreen.ADVENTURE_ZONE -> AdventureZoneScreen(
            zone = uiState.currentZone,
            onAnswerSubmitted = { answer -> viewModel.onAnswerSubmitted(answer) },
            onZoneCompleted = { nextZone -> viewModel.advanceToNextZone(nextZone) },
            onSpeakTeacher = { text, excited, gentle -> viewModel.speakTeacher(text, excited, gentle) },
            onPlaySuccessTone = { viewModel.playSuccessChime() },
            onPlaySupportTone = { viewModel.playSupportChime() }
          )

          AppScreen.CHALLENGE_ZONE -> GreatGrammarChallengeScreen(
            onChallengeCompleted = { answers, duration ->
              viewModel.completeChallenge(answers, duration)
            },
            onSpeakTeacher = { text, excited, gentle -> viewModel.speakTeacher(text, excited, gentle) },
            onPlaySuccessTone = { viewModel.playSuccessChime() },
            onPlaySupportTone = { viewModel.playSupportChime() }
          )

          AppScreen.ASSESSMENT_REPORT -> uiState.latestAssessmentResult?.let { result ->
            AssessmentReportScreen(
              result = result,
              onPlayRemediation = { gameType -> viewModel.playRemediationGame(gameType) },
              onOpenTeacherDashboard = { viewModel.openTeacherDashboard() },
              onRestartAdventure = { viewModel.restartAdventure() },
              onSpeakTeacher = { text, excited, gentle -> viewModel.speakTeacher(text, excited, gentle) }
            )
          } ?: WelcomeScreen(
            initialName = uiState.studentName,
            onStartAdventure = { name, avatar -> viewModel.startAdventure(name, avatar) },
            onOpenDashboard = { viewModel.openTeacherDashboard() },
            onSpeakTeacher = { text -> viewModel.speakTeacher(text, false, false) }
          )

          AppScreen.TEACHER_DASHBOARD -> TeacherDashboardScreen(
            currentResult = uiState.latestAssessmentResult,
            pastSessionsFlow = viewModel.pastSessions,
            onBack = { viewModel.backFromDashboard() }
          )

          AppScreen.REMEDIATION_GAME -> RemediationPlayScreen(
            gameType = uiState.activeRemediationGame,
            onBack = { viewModel.backFromRemediation() },
            onSpeakTeacher = { text, excited, gentle -> viewModel.speakTeacher(text, excited, gentle) },
            onPlaySuccessTone = { viewModel.playSuccessChime() },
            onPlaySupportTone = { viewModel.playSupportChime() }
          )
        }
      }
    }
  }
}

