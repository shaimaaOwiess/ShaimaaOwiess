package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audio.TeacherVoiceManager
import com.example.data.local.AppDatabase
import com.example.data.model.AssessmentResult
import com.example.data.model.GrammarZone
import com.example.data.model.RemediationGameType
import com.example.data.model.StudentAnswer
import com.example.data.repository.StudentProgressRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppScreen {
    WELCOME,
    ADVENTURE_ZONE,
    CHALLENGE_ZONE,
    ASSESSMENT_REPORT,
    TEACHER_DASHBOARD,
    REMEDIATION_GAME
}

data class StoneAgeUiState(
    val currentScreen: AppScreen = AppScreen.WELCOME,
    val studentName: String = "",
    val studentAvatar: String = "👦",
    val currentZone: GrammarZone = GrammarZone.VILLAGE,
    val unlockedZoneId: Int = 1,
    val scoreStars: Int = 0,
    val answersList: List<StudentAnswer> = emptyList(),
    val latestAssessmentResult: AssessmentResult? = null,
    val activeRemediationGame: RemediationGameType = RemediationGameType.BUILD_SENTENCE,
    val isSpeaking: Boolean = false,
    val isMuted: Boolean = false
)

class StoneAgeViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val repository = StudentProgressRepository(database.studentProgressDao())
    val voiceManager = TeacherVoiceManager(application)

    private val _uiState = MutableStateFlow(StoneAgeUiState())
    val uiState: StateFlow<StoneAgeUiState> = _uiState.asStateFlow()

    val pastSessions = repository.allSessions.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        viewModelScope.launch {
            voiceManager.isSpeaking.collect { speaking ->
                _uiState.value = _uiState.value.copy(isSpeaking = speaking)
            }
        }
        viewModelScope.launch {
            voiceManager.isMuted.collect { muted ->
                _uiState.value = _uiState.value.copy(isMuted = muted)
            }
        }
    }

    fun startAdventure(name: String, avatar: String) {
        _uiState.value = _uiState.value.copy(
            studentName = name,
            studentAvatar = avatar,
            currentZone = GrammarZone.VILLAGE,
            currentScreen = AppScreen.ADVENTURE_ZONE
        )
    }

    fun onAnswerSubmitted(answer: StudentAnswer) {
        val updatedAnswers = _uiState.value.answersList + answer
        val bonusStars = if (answer.isCorrect) 10 else 2
        _uiState.value = _uiState.value.copy(
            answersList = updatedAnswers,
            scoreStars = _uiState.value.scoreStars + bonusStars
        )
    }

    fun advanceToNextZone(nextZone: GrammarZone) {
        val newUnlocked = maxOf(_uiState.value.unlockedZoneId, nextZone.id)
        if (nextZone == GrammarZone.CHALLENGE) {
            _uiState.value = _uiState.value.copy(
                currentZone = nextZone,
                unlockedZoneId = newUnlocked,
                currentScreen = AppScreen.CHALLENGE_ZONE
            )
        } else {
            _uiState.value = _uiState.value.copy(
                currentZone = nextZone,
                unlockedZoneId = newUnlocked,
                currentScreen = AppScreen.ADVENTURE_ZONE
            )
        }
    }

    fun navigateToZone(zone: GrammarZone) {
        if (zone.id <= _uiState.value.unlockedZoneId) {
            if (zone == GrammarZone.CHALLENGE) {
                _uiState.value = _uiState.value.copy(
                    currentZone = zone,
                    currentScreen = AppScreen.CHALLENGE_ZONE
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    currentZone = zone,
                    currentScreen = AppScreen.ADVENTURE_ZONE
                )
            }
        }
    }

    fun completeChallenge(challengeAnswers: List<StudentAnswer>, durationSeconds: Long) {
        val allAnswers = _uiState.value.answersList + challengeAnswers
        val result = StudentProgressRepository.buildAssessmentResult(
            studentName = _uiState.value.studentName,
            answers = allAnswers,
            timeSpentSeconds = durationSeconds
        )

        _uiState.value = _uiState.value.copy(
            latestAssessmentResult = result,
            currentScreen = AppScreen.ASSESSMENT_REPORT,
            unlockedZoneId = 6,
            scoreStars = _uiState.value.scoreStars + (result.correctAnswers * 15)
        )

        viewModelScope.launch {
            repository.saveSession(result)
        }
    }

    fun openTeacherDashboard() {
        _uiState.value = _uiState.value.copy(currentScreen = AppScreen.TEACHER_DASHBOARD)
    }

    fun playRemediationGame(gameType: RemediationGameType) {
        _uiState.value = _uiState.value.copy(
            activeRemediationGame = gameType,
            currentScreen = AppScreen.REMEDIATION_GAME
        )
    }

    fun backFromRemediation() {
        _uiState.value = _uiState.value.copy(
            currentScreen = if (_uiState.value.latestAssessmentResult != null) AppScreen.ASSESSMENT_REPORT else AppScreen.ADVENTURE_ZONE
        )
    }

    fun backFromDashboard() {
        _uiState.value = _uiState.value.copy(
            currentScreen = when {
                _uiState.value.latestAssessmentResult != null -> AppScreen.ASSESSMENT_REPORT
                _uiState.value.studentName.isNotBlank() -> AppScreen.ADVENTURE_ZONE
                else -> AppScreen.WELCOME
            }
        )
    }

    fun restartAdventure() {
        _uiState.value = StoneAgeUiState(
            studentName = _uiState.value.studentName,
            studentAvatar = _uiState.value.studentAvatar
        )
    }

    fun speakTeacher(text: String, isExcited: Boolean = false, isGentle: Boolean = false) {
        voiceManager.speakTeacher(text, isExcited, isGentle)
    }

    fun playSuccessChime() {
        voiceManager.playSuccessChime()
    }

    fun playSupportChime() {
        voiceManager.playGentleSupportChime()
    }

    fun toggleMute() {
        voiceManager.toggleMute()
    }

    override fun onCleared() {
        super.onCleared()
        voiceManager.destroy()
    }
}
