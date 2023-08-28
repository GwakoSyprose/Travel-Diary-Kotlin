package com.example.mytraveldiary.data.repository

import androidx.annotation.WorkerThread
import com.example.mytraveldiary.data.db.Diary
import com.example.mytraveldiary.data.db.DiaryDao
import kotlinx.coroutines.flow.Flow

class DiaryRepositoryImpl(private val diaryDao: DiaryDao):  DiaryRepository {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    override fun getDiaries() : Flow<List<Diary>>  =  diaryDao.getAlphabetizedDiaries()

    override suspend fun addDiary(diary: Diary)  = diaryDao.insert(diary)


}
