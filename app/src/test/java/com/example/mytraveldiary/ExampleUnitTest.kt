package com.example.mytraveldiary

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mytraveldiary.ui.create.CreateDiaryFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class AddDiaryFragmentTest {

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun validateAllFields_emptyFields_returnsFalse() {
        val fragment = CreateDiaryFragment()
        val inputLayouts = listOf(
            fragment.binding.tilTitle,
            fragment.binding.tilNotes,
            fragment.binding.tilDate
        )

        val isValid = fragment.validateAllFields(inputLayouts)

        assertEquals(false, isValid)
    }

    // more tests for other functions...
}
