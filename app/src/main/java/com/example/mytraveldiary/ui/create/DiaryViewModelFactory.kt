package com.example.mytraveldiary.ui.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mytraveldiary.data.repository.DiaryRepository

//will create instances of quotesViewModel
class DiaryViewModelFactory(
    private val diaryRepository: DiaryRepository
): ViewModelProvider.NewInstanceFactory() {

    //@Suppress(...names: "UNCHECKED-CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DiaryViewModel(diaryRepository) as T
    }
}