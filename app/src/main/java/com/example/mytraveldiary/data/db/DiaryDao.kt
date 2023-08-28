package com.example.mytraveldiary.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface DiaryDao {

    //When Room queries return Flow, the queries are automatically run asynchronously on a background thread
    @Query("SELECT * FROM diary_table ORDER BY title ASC")
    fun getAlphabetizedDiaries(): Flow<List<Diary>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(diary: Diary)

}