package com.example.mytraveldiary.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mytraveldiary.R
import com.example.mytraveldiary.data.db.Diary
import timber.log.Timber

class DiaryListAdapter(private val onClick: (Diary) -> Unit) :
    ListAdapter<Diary, DiaryListAdapter.DiaryViewHolder>(FlowerDiffCallback) {

    private var originalList: List<Diary> = ArrayList()

    /* ViewHolder for Diary, takes in the inflated view and the onClick behavior. */
    class DiaryViewHolder(itemView: View, val onClick: (Diary) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val diaryImageView: ImageView = itemView.findViewById(R.id.iv_diary_image)
        private val titleTextView: TextView = itemView.findViewById(R.id.tv_title)
        private val dateTextView: TextView = itemView.findViewById(R.id.tv_date)
        private val locationTextView: TextView = itemView.findViewById(R.id.tv_location)

        private var currentDiary: Diary? = null

        init {
            itemView.setOnClickListener {
                currentDiary?.let {
                    onClick(it)
                }
            }
        }

        fun bind(diary: Diary) {
            currentDiary = diary

            titleTextView.text = diary.title
            dateTextView.text = diary.date
            locationTextView.text = diary.location
            diaryImageView.setImageResource(R.drawable.ic_logo)
        }
    }

    fun setData(list: List<Diary>) {
        originalList = list
        super.submitList(list)
    }

    fun filter(query: String) {
        val newList = originalList
            .filter { diary -> diary.title.contains(query, ignoreCase = true) }
        submitList(newList)
    }

    /* Creates and inflates view and return FlowerViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return DiaryViewHolder(view, onClick)
    }

    /* Gets current flower and uses it to bind view. */
    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        val flower = getItem(position)
        holder.bind(flower)

    }
}

object FlowerDiffCallback : DiffUtil.ItemCallback<Diary>() {
    override fun areItemsTheSame(oldItem: Diary, newItem: Diary): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Diary, newItem: Diary): Boolean {
        return oldItem.id == newItem.id
    }
}