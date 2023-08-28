package com.example.mytraveldiary.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mytraveldiary.data.db.Diary
import com.example.mytraveldiary.data.repository.DiaryRepository
import kotlinx.coroutines.launch
import timber.log.Timber


class DiaryViewModel(
    private val diaryRepository: DiaryRepository
    ) : ViewModel() {


   val allDiaries: LiveData<List<Diary>> = diaryRepository.getDiaries().asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun addDiaryEntry(diary: Diary) {
        viewModelScope.launch {
         diaryRepository.addDiary(diary)
        }

    }

}

