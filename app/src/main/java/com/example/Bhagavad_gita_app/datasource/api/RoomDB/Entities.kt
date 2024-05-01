package com.example.Bhagavad_gita_app.datasource.api.RoomDB

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.models.Commentary
import com.example.models.Translation


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
@Entity(tableName = "savedVerses")
    data class savedVerse(
    val chapter_number: Int,
    val commentaries: List<Commentary>,
    @PrimaryKey
    val id: Int,
    val slug: String,
    val text: String,
    val translations: List<Translation>,
    val transliteration: String,
    val verse_number: Int,
    val word_meanings: String
    )
