package com.example.Bhagavad_gita_app.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.Bhagavad_gita_app.Repository.AppRepository
import com.example.Bhagavad_gita_app.datasource.api.RoomDB.AppDataBase
import com.example.Bhagavad_gita_app.datasource.api.RoomDB.savedChapters
import com.example.models.ChaptersItem
import com.example.models.VercesItemItem
import kotlinx.coroutines.flow.Flow

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val savedChaptersDao = AppDataBase.getDatabaseInstance(application)?.savedChaptersDao()
    val appRepository = AppRepository(savedChaptersDao!!)

    //getting result from function in app appRepository name getAllChapter
    fun getAllChapter(): Flow<List<ChaptersItem>> = appRepository.getAllChapter()
    //getting result from function in app appRepository name getverses
    fun getverses(chapterNumber:Int):Flow<List<VercesItemItem>> = appRepository.getverses(chapterNumber)

    fun getVarseDetail(chapterNumber: Int,verseNumber:Int):Flow<VercesItemItem> = appRepository.getVarseDetail(chapterNumber,verseNumber)

    // saved chapters
    suspend fun insertChapters(savedChapter : savedChapters) = appRepository.insertChapters(savedChapter)

    fun getSavedChapters(): LiveData<List<savedChapters>> = appRepository.getSavedChapters()
}