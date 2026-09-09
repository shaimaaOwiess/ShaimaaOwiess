package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentProgressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: StudentSessionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnswers(answers: List<AnswerRecordEntity>)

    @Query("SELECT * FROM student_sessions ORDER BY timestamp DESC")
    fun getAllSessions(): Flow<List<StudentSessionEntity>>

    @Query("SELECT * FROM student_sessions WHERE studentName = :name ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestSessionForStudent(name: String): StudentSessionEntity?

    @Query("SELECT * FROM answer_records WHERE sessionId = :sessionId")
    suspend fun getAnswersForSession(sessionId: Long): List<AnswerRecordEntity>

    @Query("DELETE FROM student_sessions")
    suspend fun clearAllHistory()
}
