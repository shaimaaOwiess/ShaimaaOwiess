package com.example.data.model

enum class QuestionType {
    MULTIPLE_CHOICE,
    SENTENCE_COMPLETION,
    ERROR_CORRECTION,
    TRUE_FALSE,
    DIALOGUE_RESPONSE
}

data class GrammarQuestion(
    val id: Int,
    val zone: GrammarZone,
    val skill: GrammarSkill,
    val storyContext: String,
    val characterAvatar: String,
    val questionText: String,
    val options: List<String>,
    val correctOptionIndex: Int,
    val explanation: String,
    val questionType: QuestionType = QuestionType.MULTIPLE_CHOICE
) {
    val correctOption: String
        get() = options.getOrElse(correctOptionIndex) { "" }
}

data class StudentAnswer(
    val questionId: Int,
    val questionText: String,
    val selectedIndex: Int,
    val selectedOption: String,
    val correctOption: String,
    val isCorrect: Boolean,
    val skill: GrammarSkill,
    val explanation: String,
    val responseTimeMs: Long
)
