package com.example.data.model

enum class PerformanceLevel(
    val title: String,
    val badgeEmoji: String,
    val stickerTitle: String,
    val stickerQuote: String,
    val minPercentage: Int
) {
    SUPERSTAR(
        title = "GRAMMAR SUPERSTAR",
        badgeEmoji = "🌟",
        stickerTitle = "🌟 GRAMMAR SUPERSTAR! 🌟",
        stickerQuote = "You did AMAZING!\nKeep shining!\n— Shaimaa Owiess 💜",
        minPercentage = 90
    ),
    HERO(
        title = "GRAMMAR HERO",
        badgeEmoji = "🏆",
        stickerTitle = "🏆 GRAMMAR HERO! 🏆",
        stickerQuote = "Super smart work in the Stone Age!\nYou are a true champion!\n— Shaimaa Owiess ⭐",
        minPercentage = 80
    ),
    GREAT_PROGRESS(
        title = "GREAT PROGRESS",
        badgeEmoji = "💪",
        stickerTitle = "🌱 GRAMMAR GROWER 🌱",
        stickerQuote = "You are getting stronger every day!\nKeep practicing!\n— Shaimaa Owiess 💜",
        minPercentage = 70
    ),
    KEEP_GROWING(
        title = "KEEP GROWING",
        badgeEmoji = "🌱",
        stickerTitle = "🌱 GRAMMAR GROWER 🌱",
        stickerQuote = "Great effort! Each step brings you closer to mastery!\n— Shaimaa Owiess 💜",
        minPercentage = 60
    ),
    BRAVE_LEARNER(
        title = "BRAVE LEARNER — LET'S PRACTICE TOGETHER",
        badgeEmoji = "💜",
        stickerTitle = "💜 BRAVE LEARNER 💜",
        stickerQuote = "I'm proud of you for trying!\nWe will learn it together.\nNever give up!\n— Shaimaa Owiess 🌟",
        minPercentage = 0
    );

    companion object {
        fun fromPercentage(percentage: Int): PerformanceLevel = when {
            percentage >= 90 -> SUPERSTAR
            percentage >= 80 -> HERO
            percentage >= 70 -> GREAT_PROGRESS
            percentage >= 60 -> KEEP_GROWING
            else -> BRAVE_LEARNER
        }
    }
}

enum class SkillStatus(val label: String, val icon: String) {
    STRONG("Strong", "🟢"),
    DEVELOPING("Developing", "🟡"),
    NEEDS_PRACTICE("Needs Practice", "🔴")
}

data class SkillScore(
    val skill: GrammarSkill,
    val totalQuestions: Int,
    val correctCount: Int,
    val accuracyPercentage: Int,
    val status: SkillStatus,
    val recommendation: String
)

data class WeakAreaDetail(
    val skill: GrammarSkill,
    val mistakeCount: Int,
    val examples: List<String>,
    val whyStruggled: String,
    val practiceRecommendation: String,
    val remediationGameTitle: String
)

data class AssessmentResult(
    val studentName: String,
    val dateString: String,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val incorrectAnswers: Int,
    val scorePercentage: Int,
    val timeSpentSeconds: Long,
    val level: PerformanceLevel,
    val strongestSkill: GrammarSkill,
    val weakestSkill: GrammarSkill,
    val skillScores: List<SkillScore>,
    val weakAreas: List<WeakAreaDetail>,
    val missedQuestions: List<StudentAnswer>,
    val allAnswers: List<StudentAnswer>,
    val teacherSpecialMessage: String,
    val personalizedTips: List<String>
)
