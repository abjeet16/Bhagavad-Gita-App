package com.example.Bhagavad_gita_app.datasource.api.RoomDB

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "savedChapters")
    data class savedChapters(
        val chapter_number: Int,
        val chapter_summary: String,
        val chapter_summary_hindi: String,
        @PrimaryKey
        val id: Int,
        val name: String,
        val name_meaning: String,
        val name_translated: String,
        val name_transliterated: String,
        val slug: String,
        val verses_count: Int,
        val verses : List<String>
    )
