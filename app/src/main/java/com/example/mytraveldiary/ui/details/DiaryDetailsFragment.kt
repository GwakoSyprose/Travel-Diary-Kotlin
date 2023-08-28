package com.example.mytraveldiary.ui.details

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.mytraveldiary.R
import com.example.mytraveldiary.core.fragment.InjectionFragment
import com.example.mytraveldiary.databinding.DiaryDetailsBinding
import com.example.mytraveldiary.ui.create.ImageAdapter
import timber.log.Timber


class DiaryDetailsFragment : InjectionFragment(R.layout.diary_details) {

    private lateinit var binding: DiaryDetailsBinding
    private val args : DiaryDetailsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding =  DiaryDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val diary = args.diary
        binding.apply {
            tvTitle.text = diary.title
            tvDate.text = diary.date
            headerDate.text = diary.date
            tvLocation.text = diary.location
            tvNotes.text = diary.notes
            ratingBar.rating = diary.rating.toFloat()
            ratingBar.visibility = View.VISIBLE
        }

        diary.imagePaths?.let { displayImages(it) }
        binding.imageBack.setOnClickListener {
            navigateToDiaryList()
        }
    }
    private fun displayImages(imageUris: MutableList<Uri>) {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = ImageAdapter(imageUris)
            setHasFixedSize(false)
        }
    }

    private fun navigateToDiaryList(){
        val action = DiaryDetailsFragmentDirections.actionDetailsToDiaryList()
        findNavController().navigate(action)
    }

}