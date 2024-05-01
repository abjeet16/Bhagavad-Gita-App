package com.example.Bhagavad_gita_app.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.Bhagavad_gita_app.Repository.AppRepository
import com.example.Bhagavad_gita_app.datasource.api.RoomDB.AppDataBase
import com.example.Bhagavad_gita_app.datasource.api.RoomDB.savedChapters
import com.example.Bhagavad_gita_app.datasource.api.RoomDB.savedVerse
import com.example.models.ChaptersItem
import com.example.models.VercesItemItem
import kotlinx.coroutines.flow.Flow

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val savedChaptersDao = AppDataBase.getDatabaseInstance(application)?.savedChaptersDao()
    val savedVersesDao = AppDataBase.getDatabaseInstance(application)?.savedVersesDao()

    val appRepository = AppRepository(savedChaptersDao!!,savedVersesDao!!)

    //getting result from function in app appRepository name getAllChapter
    fun getAllChapter(): Flow<List<ChaptersItem>> = appRepository.getAllChapter()
    //getting result from function in app appRepository name getverses
    fun getverses(chapterNumber:Int):Flow<List<VercesItemItem>> = appRepository.getverses(chapterNumber)

    fun getVarseDetail(chapterNumber: Int,verseNumber:Int):Flow<VercesItemItem> = appRepository.getVarseDetail(chapterNumber,verseNumber)

    // saved chapters
    suspend fun insertChapters(savedChapter : savedChapters) = appRepository.insertChapters(savedChapter)

    fun getSavedChapters(): LiveData<List<savedChapters>> = appRepository.getSavedChapters()

    fun getaParticularChapter(chapter_number : Int) : LiveData<savedChapters> = appRepository.getaParticularChapter(chapter_number)

    //saved verses

    suspend fun insertVerse(verses: savedVerse)= appRepository.insertVerse(verses)

    fun getAllSavedVerses():LiveData<List<savedVerse>> = appRepository.getAllSavedVerses()

    fun getParticularVerse(chapter_number: Int,verseNumber:Int):LiveData<savedVerse> = appRepository.getParticularVerse(chapter_number, verseNumber)

    suspend fun deleteAParticularVerse(chapter_number: Int,versesNumber:Int) = appRepository.deleteAParticularVerse(chapter_number, versesNumber)
}