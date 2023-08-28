package com.example.mytraveldiary.data.repository

import com.example.mytraveldiary.data.db.Diary
import kotlinx.coroutines.flow.Flow

interface DiaryRepository {
   suspend fun addDiary(diary: Diary)
   fun getDiaries(): Flow<List<Diary>>
}
