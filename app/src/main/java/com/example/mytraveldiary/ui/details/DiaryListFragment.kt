package com.example.mytraveldiary.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytraveldiary.R
import com.example.mytraveldiary.core.fragment.InjectionFragment
import com.example.mytraveldiary.data.db.Diary
import com.example.mytraveldiary.data.db.DiaryParcelable
import com.example.mytraveldiary.databinding.DiaryEntriesListBinding
import com.example.mytraveldiary.ui.create.DiaryViewModel
import org.kodein.di.instance


class DiaryListFragment : InjectionFragment(R.layout.diary_entries_list) {

    private val viewModel: DiaryViewModel by instance()
    private lateinit var binding:  DiaryEntriesListBinding
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding =  DiaryEntriesListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val diaryListAdapter = DiaryListAdapter { diary -> navigateToDairyDetails(diary)}
        recyclerView.adapter = diaryListAdapter


        viewModel.allDiaries.observe(viewLifecycleOwner) { diaries ->
            // Update the cached copy of the words in the adapter.
            diaries.let {
                diaryListAdapter.submitList(it)
                diaryListAdapter.setData(it)
                if(diaries.isEmpty()){
                    binding.noDiaries.visibility = VISIBLE
                    binding.recyclerView.visibility = GONE
                } else {
                    binding.noDiaries.visibility = GONE
                    binding.recyclerView.visibility = VISIBLE
                }
            }
        }

        binding.fab.setOnClickListener {
            navigateToAddDiary()
        }
    }

    private fun navigateToAddDiary(){
        val action = DiaryListFragmentDirections.actionDiariesListToAddDiary()
        findNavController().navigate(action)
    }
    private fun navigateToDairyDetails(diary: Diary){
        val diaryParcelable = DiaryParcelable(
            id = diary.id,
            title = diary.title,
            date = diary.date,
            location = diary.location,
            rating = diary.rating,
            notes = diary.notes,
            imagePaths = diary.imagePaths
        )
        val action = DiaryListFragmentDirections.actionDiariesListToDiaryDetails(diaryParcelable)
        findNavController().navigate(action)
    }

}