package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_sessions")
data class StudentSessionEntity(
    @PrimaryKey(autoGenerate = true)
    val sessionId: Long = 0,
    val studentName: String,
    val timestamp: Long = System.currentTimeMillis(),
    val totalQuestions: Int,
    val correctAnswers: Int,
    val scorePercentage: Int,
    val timeSpentSeconds: Long,
    val levelName: String,
    val strongestSkill: String,
    val weakestSkill: String
)

@Entity(tableName = "answer_records")
data class AnswerRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val sessionId: Long,
    val questionId: Int,
    val questionText: String,
    val selectedOption: String,
    val correctOption: String,
    val isCorrect: Boolean,
    val skillCode: String,
    val responseTimeMs: Long
)
