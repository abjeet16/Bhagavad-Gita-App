package com.example.chatbot.viewModel

import androidx.lifecycle.ViewModel
import com.example.chatbot.Repository.AppRepository
import com.example.models.ChaptersItem
import com.example.models.VercesItemItem
import kotlinx.coroutines.flow.Flow

class MainViewModel : ViewModel() {

    val appRepository = AppRepository()

    //getting result from function in app appRepository name getAllChapter
    fun getAllChapter(): Flow<List<ChaptersItem>> = appRepository.getAllChapter()
    //getting result from function in app appRepository name getverses
    fun getverses(chapterNumber:Int):Flow<List<VercesItemItem>> = appRepository.getverses(chapterNumber)
}