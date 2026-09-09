package com.example.data.repository

import com.example.data.local.AnswerRecordEntity
import com.example.data.local.StudentProgressDao
import com.example.data.local.StudentSessionEntity
import com.example.data.model.AssessmentResult
import com.example.data.model.GrammarSkill
import com.example.data.model.PerformanceLevel
import com.example.data.model.SkillScore
import com.example.data.model.SkillStatus
import com.example.data.model.StudentAnswer
import com.example.data.model.WeakAreaDetail
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class StudentProgressRepository(private val dao: StudentProgressDao) {

    val allSessions: Flow<List<StudentSessionEntity>> = dao.getAllSessions()

    suspend fun saveSession(result: AssessmentResult): Long {
        val entity = StudentSessionEntity(
            studentName = result.studentName,
            timestamp = System.currentTimeMillis(),
            totalQuestions = result.totalQuestions,
            correctAnswers = result.correctAnswers,
            scorePercentage = result.scorePercentage,
            timeSpentSeconds = result.timeSpentSeconds,
            levelName = result.level.title,
            strongestSkill = result.strongestSkill.displayName,
            weakestSkill = result.weakestSkill.displayName
        )
        val sessionId = dao.insertSession(entity)

        val answerEntities = result.allAnswers.map { ans ->
            AnswerRecordEntity(
                sessionId = sessionId,
                questionId = ans.questionId,
                questionText = ans.questionText,
                selectedOption = ans.selectedOption,
                correctOption = ans.correctOption,
                isCorrect = ans.isCorrect,
                skillCode = ans.skill.code,
                responseTimeMs = ans.responseTimeMs
            )
        }
        dao.insertAnswers(answerEntities)
        return sessionId
    }

    suspend fun clearHistory() {
        dao.clearAllHistory()
    }

    companion object {
        fun buildAssessmentResult(
            studentName: String,
            answers: List<StudentAnswer>,
            timeSpentSeconds: Long
        ): AssessmentResult {
            val total = answers.size
            val correct = answers.count { it.isCorrect }
            val incorrect = total - correct
            val percentage = if (total > 0) ((correct * 100) / total) else 0
            val level = PerformanceLevel.fromPercentage(percentage)

            // Calculate skill breakdown
            val skillScores = GrammarSkill.entries.map { skill ->
                val forSkill = answers.filter { it.skill == skill }
                val skillTotal = forSkill.size
                val skillCorrect = forSkill.count { it.isCorrect }
                val accuracy = if (skillTotal > 0) (skillCorrect * 100) / skillTotal else 100
                val status = when {
                    accuracy >= 80 -> SkillStatus.STRONG
                    accuracy >= 50 -> SkillStatus.DEVELOPING
                    else -> SkillStatus.NEEDS_PRACTICE
                }
                val recommendation = when (status) {
                    SkillStatus.STRONG -> "Mastered! Keep this grammar skill shining bright in daily conversations."
                    SkillStatus.DEVELOPING -> "Good progress! A few quick Stone Age mini-games will lock this rule in."
                    SkillStatus.NEEDS_PRACTICE -> "Priority focus! Practice with Teacher Shaimaa's friendly tips."
                }
                SkillScore(
                    skill = skill,
                    totalQuestions = skillTotal,
                    correctCount = skillCorrect,
                    accuracyPercentage = accuracy,
                    status = status,
                    recommendation = recommendation
                )
            }

            // Identify strongest and weakest
            val assessedSkills = skillScores.filter { it.totalQuestions > 0 }
            val strongestSkill = assessedSkills.maxByOrNull { it.accuracyPercentage }?.skill
                ?: GrammarSkill.CONCEPT_ROUTINES
            val weakestSkill = assessedSkills.minByOrNull { it.accuracyPercentage }?.skill
                ?: GrammarSkill.THIRD_PERSON_S

            // Identify weak areas
            val weakAreas = assessedSkills
                .filter { it.accuracyPercentage < 80 }
                .map { score ->
                    val missedForSkill = answers.filter { it.skill == score.skill && !it.isCorrect }
                    val examples = missedForSkill.take(3).map {
                        "Asked: \"${it.questionText}\" | Chosen: \"${it.selectedOption}\" | Correct: \"${it.correctOption}\""
                    }
                    val why = when (score.skill) {
                        GrammarSkill.THIRD_PERSON_S -> "The student tended to miss adding '-s' or '-es' when the subject is He, She, or It."
                        GrammarSkill.NEGATIVES_DONT_DOESNT -> "The student tended to leave '-s' on the main verb after doesn't, or confused don't vs doesn't."
                        GrammarSkill.QUESTIONS_DO_DOES -> "The student confused when to use auxiliary 'Do' vs 'Does' based on the subject pronoun."
                        GrammarSkill.BASE_VERBS -> "The student mistakenly added '-s' to plural subjects (I, You, We, They)."
                        GrammarSkill.FREQUENCY_ADVERBS -> "The student placed frequency words after the verb or was unsure of their order."
                        GrammarSkill.SPELLING_RULES -> "The student needed extra guidance with verbs ending in -ch, -sh, -o, or consonant+y."
                        else -> "The student is developing confidence with Present Simple daily routines and sentence patterns."
                    }
                    val game = when (score.skill) {
                        GrammarSkill.THIRD_PERSON_S -> "🪨 Build the Sentence (He/She/It + S)"
                        GrammarSkill.NEGATIVES_DONT_DOESNT -> "👾 Feed the Friendly Dinosaur (Don't / Doesn't)"
                        GrammarSkill.QUESTIONS_DO_DOES -> "🔥 Campfire Challenge (Do / Does Questions)"
                        GrammarSkill.SPELLING_RULES -> "💎 Grammar Treasure (Cave Spelling Secret)"
                        GrammarSkill.FREQUENCY_ADVERBS -> "🌳 Forest Word Hunt (Frequency Adverbs)"
                        else -> "🏹 Hit the Grammar Target"
                    }
                    WeakAreaDetail(
                        skill = score.skill,
                        mistakeCount = score.totalQuestions - score.correctCount,
                        examples = examples,
                        whyStruggled = why,
                        practiceRecommendation = score.skill.ruleSummary,
                        remediationGameTitle = game
                    )
                }

            // Generate personalized teacher message
            val teacherMessage = if (percentage >= 80) {
                "My brilliant superstar, ${studentName.ifBlank { "Explorer" }}! 🌟 You did such a magnificent job traveling through the Stone Age! Your understanding of the Present Simple is truly inspiring. Keep smiling, keep practicing, and continue shining your bright light! With all my love and pride, Shaimaa Owiess 💜"
            } else {
                "My dear learner, ${studentName.ifBlank { "Explorer" }}! 💜 Please don't feel sad about your score. Your score does NOT define you! It only shows us what wonderful adventures we can explore next together. Every mistake is a stepping stone to greatness. I believe in you with all my heart, and we are going to master this together! Love, Shaimaa Owiess 🌸"
            }

            // Personalized tips
            val tips = mutableListOf<String>()
            tips.add("Remember our Stone Age Golden Rule: I, You, We, They use the base verb. He, She, It love the letter -S! 🪨")
            if (answers.any { it.skill == GrammarSkill.NEGATIVES_DONT_DOESNT && !it.isCorrect }) {
                tips.add("Secret from Shaimaa: In negative sentences, 'doesn't' already stole the -S! So the main verb stays base form: 'He doesn't play.' 👾")
            }
            if (answers.any { it.skill == GrammarSkill.QUESTIONS_DO_DOES && !it.isCorrect }) {
                tips.add("Question trick: Check the subject first! Does he / Does she / Does it? Do you / Do they? 🌊")
            }
            if (answers.any { it.skill == GrammarSkill.SPELLING_RULES && !it.isCorrect }) {
                tips.add("Spelling tip: Verbs ending in -ch, -sh, -x, -ss, -o take -ES (watches, washes, goes)! 💎")
            }
            tips.add("Always remember: You don't have to be perfect to learn. You just have to keep trying! 💜✨")

            val dateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale.getDefault())

            return AssessmentResult(
                studentName = studentName.ifBlank { "Young Explorer" },
                dateString = dateFormat.format(Date()),
                totalQuestions = total,
                correctAnswers = correct,
                incorrectAnswers = incorrect,
                scorePercentage = percentage,
                timeSpentSeconds = timeSpentSeconds,
                level = level,
                strongestSkill = strongestSkill,
                weakestSkill = weakestSkill,
                skillScores = skillScores,
                weakAreas = weakAreas,
                missedQuestions = answers.filter { !it.isCorrect },
                allAnswers = answers,
                teacherSpecialMessage = teacherMessage,
                personalizedTips = tips
            )
        }

        fun generateExcelCsvReport(result: AssessmentResult): String {
            val sb = StringBuilder()
            sb.append("====================================================\n")
            sb.append("PRESENT SIMPLE STONE AGE ADVENTURE - TEACHER REPORT\n")
            sb.append("Teacher: Shaimaa Owiess | \"Learn with love. Dream big. Shine bright!\"\n")
            sb.append("====================================================\n\n")

            // SHEET 1
            sb.append("[SHEET 1: STUDENT SUMMARY]\n")
            sb.append("Field,Value\n")
            sb.append("Student Name,\"${result.studentName}\"\n")
            sb.append("Date,\"${result.dateString}\"\n")
            sb.append("Total Questions,${result.totalQuestions}\n")
            sb.append("Correct Answers,${result.correctAnswers}\n")
            sb.append("Incorrect Answers,${result.incorrectAnswers}\n")
            sb.append("Score,${result.correctAnswers}/${result.totalQuestions}\n")
            sb.append("Percentage,${result.scorePercentage}%\n")
            sb.append("Final Level,\"${result.level.title}\"\n")
            sb.append("Time Spent,\"${result.timeSpentSeconds} seconds\"\n")
            sb.append("Strongest Skill,\"${result.strongestSkill.displayName}\"\n")
            sb.append("Weakest Skill,\"${result.weakestSkill.displayName}\"\n")
            sb.append("Recommended Action,\"${if (result.scorePercentage >= 80) "Celebrate achievement and reinforce with reading" else "Complete personalized remediation mini-games"}\"\n\n")

            // SHEET 2
            sb.append("[SHEET 2: QUESTION ANALYSIS]\n")
            sb.append("No,Question,Student Answer,Correct Answer,Status,Skill,Error Type,Explanation\n")
            result.allAnswers.forEachIndexed { idx, ans ->
                val status = if (ans.isCorrect) "CORRECT" else "INCORRECT"
                val errorType = if (ans.isCorrect) "None" else ans.skill.name
                sb.append("${idx + 1},\"${ans.questionText.replace("\"", "\"\"")}\",\"${ans.selectedOption}\",\"${ans.correctOption}\",$status,\"${ans.skill.displayName}\",\"$errorType\",\"${ans.explanation.replace("\"", "\"\"")}\"\n")
            }
            sb.append("\n")

            // SHEET 3
            sb.append("[SHEET 3: SKILL ANALYSIS]\n")
            sb.append("Skill,Attempted,Correct,Incorrect,Accuracy %,Status,Recommendation\n")
            result.skillScores.forEach { s ->
                val incorrect = s.totalQuestions - s.correctCount
                sb.append("\"${s.skill.displayName}\",${s.totalQuestions},${s.correctCount},$incorrect,${s.accuracyPercentage}%,${s.status.label},\"${s.recommendation}\"\n")
            }
            sb.append("\n")

            // SHEET 4
            sb.append("[SHEET 4: WEAK AREAS]\n")
            sb.append("Weak Area,Mistakes,Why Student Struggled,Practice Recommendation,Remediation Game\n")
            if (result.weakAreas.isEmpty()) {
                sb.append("None,0,\"Excellent performance across all assessed areas\",\"Maintain daily practice\",\"Grammar Hero Quest\"\n")
            } else {
                result.weakAreas.forEach { w ->
                    sb.append("\"${w.skill.displayName}\",${w.mistakeCount},\"${w.whyStruggled.replace("\"", "\"\"")}\",\"${w.practiceRecommendation.replace("\"", "\"\"")}\",\"${w.remediationGameTitle}\"\n")
                }
            }
            sb.append("\n")

            // SHEET 5
            sb.append("[SHEET 5: PERSONALIZED PLAN]\n")
            sb.append("Student Name,\"${result.studentName}\"\n")
            sb.append("Strengths,\"${result.strongestSkill.displayName} (${result.skillScores.firstOrNull { it.skill == result.strongestSkill }?.accuracyPercentage ?: 100}% accuracy)\"\n")
            sb.append("Areas to Improve,\"${result.weakAreas.joinToString("; ") { it.skill.displayName }.ifBlank { "All skills strong" }}\"\n")
            sb.append("Daily Practice Recommendation,\"10 minutes of Present Simple storytelling using daily routine verbs\"\n")
            sb.append("Fun Activity,\"${result.weakAreas.firstOrNull()?.remediationGameTitle ?: "Feed the Friendly Dinosaur"}\"\n")
            sb.append("Teacher Message,\"${result.teacherSpecialMessage.replace("\"", "\"\"")}\"\n")
            sb.append("Next Review,\"In 3 days with Teacher Shaimaa Owiess\"\n")

            return sb.toString()
        }
    }
}
