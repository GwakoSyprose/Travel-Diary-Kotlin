package com.example.mytraveldiary.ui.create

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mytraveldiary.R

class ImageAdapter(private val imagePaths: MutableList<Uri>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return ImageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imagePath = imagePaths[position]
        // Load and display image using Glide or other image loading library
        Glide.with(holder.itemView)
            .load(imagePath)
            .centerCrop()
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return imagePaths.size
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Initialize views and set up image loading
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }
}
