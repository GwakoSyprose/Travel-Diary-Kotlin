package com.example.mytraveldiary.data.db

import android.net.Uri
import androidx.room.TypeConverter

class ListUriTypeConverter {

    @TypeConverter
    fun fromList(list: List<Uri>): String {
        return list.joinToString(",") { it.toString() }
    }

    @TypeConverter
    fun toList(string: String): List<Uri> {
        return string.split(",").map { Uri.parse(it) }
    }
}