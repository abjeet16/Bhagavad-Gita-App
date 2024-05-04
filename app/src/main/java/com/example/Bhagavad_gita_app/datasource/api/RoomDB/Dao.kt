package com.example.Bhagavad_gita_app.datasource.api.RoomDB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SavedChaptersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapters(savedChapter : savedChapters)

    @Query("SELECT * FROM savedChapters")
    fun getSavedChapters(): LiveData<List<savedChapters>>

    @Query("Delete from savedChapters where id = :id")
    fun deleteChapter(id : Int)

    @Query("Select * from savedChapters where chapter_number = :chapter_number")
    fun getaParticularChapter(chapter_number : Int) : LiveData<savedChapters>
}
@Dao
interface SavedVersesDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVerse(verses:savedVerse)

    @Query("Select * From savedVerses")
    fun getAllSavedVerses():LiveData<List<savedVerse>>

    @Query("Select * From savedVerses where chapter_number = :chapterNumber And verse_number = :verseNumber")
    fun getParticularVerse(chapterNumber: Int,verseNumber:Int):LiveData<savedVerse>

    @Query("Delete From savedVerses where chapter_number = :chapterNumber And verse_number = :versesNumber")
    suspend fun deleteAParticularVerse(chapterNumber: Int,versesNumber:Int)
}