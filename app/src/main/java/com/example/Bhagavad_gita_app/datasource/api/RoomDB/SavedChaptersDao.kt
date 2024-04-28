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