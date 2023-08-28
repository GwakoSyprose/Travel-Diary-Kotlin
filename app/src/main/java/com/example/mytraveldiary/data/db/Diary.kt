package com.example.mytraveldiary.data.db

import android.net.Uri
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.parcelize.Parcelize


@Entity(tableName = "diary_table")
@TypeConverters(ListUriTypeConverter::class)
data class Diary(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val date: String,
    val location: String,
    val rating: Int = 4,
    val notes: String,
    val imagePaths: MutableList<Uri>? = null
)


@Parcelize
data class DiaryParcelable(
    val id: Int = 0,
    val title: String,
    val date: String,
    val location: String,
    val rating: Int = 0,
    val notes: String,
    val imagePaths: MutableList<Uri>? = null
) : Parcelable
