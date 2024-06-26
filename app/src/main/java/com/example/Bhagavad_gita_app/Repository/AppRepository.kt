package com.example.Bhagavad_gita_app.Repository

import androidx.lifecycle.LiveData
import com.example.Bhagavad_gita_app.datasource.api.ApiUtilities
import com.example.Bhagavad_gita_app.datasource.api.RoomDB.SavedChaptersDao
import com.example.Bhagavad_gita_app.datasource.api.RoomDB.SavedVersesDao
import com.example.Bhagavad_gita_app.datasource.api.RoomDB.savedChapters
import com.example.Bhagavad_gita_app.datasource.api.RoomDB.savedVerse
import com.example.models.ChaptersItem
import com.example.models.VercesItemItem
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppRepository(val savedChaptersDao: SavedChaptersDao,val savedVersesDao: SavedVersesDao) {
    fun getAllChapter(): Flow<List<ChaptersItem>> = callbackFlow {
        val callBack = object : Callback<List<ChaptersItem>>{
            override fun onResponse(
                call: Call<List<ChaptersItem>>,
                response: Response<List<ChaptersItem>>
            ) {
                if (response.isSuccessful && response.body()!= null){
                    trySend(response.body()!!)
                    close()
                }
            }

            override fun onFailure(call: Call<List<ChaptersItem>>, t: Throwable) {
                close(t)
            }
        }

        ApiUtilities.api.getAllChapter().enqueue(callBack)
        awaitClose{}

    }

    //function to get the verses of a particular chapter clicked
    fun getverses(chapterNumber:Int):Flow<List<VercesItemItem>> = callbackFlow {
        val callback = object :Callback<List<VercesItemItem>>{
            override fun onResponse(
                call: Call<List<VercesItemItem>>,
                response: Response<List<VercesItemItem>>
            ) {
                if (response.isSuccessful && response.body()!= null){
                    trySend(response.body()!!)
                    close()
                }
            }

            override fun onFailure(call: Call<List<VercesItemItem>>, t: Throwable) {
                close(t)
            }
        }
        ApiUtilities.api.getVarses(chapterNumber).enqueue(callback)
        awaitClose{}
    }

    fun getVarseDetail(chapterNumber: Int,verseNumber:Int):Flow<VercesItemItem> = callbackFlow {
        val callback = object : Callback<VercesItemItem>{
            override fun onResponse(
                call: Call<VercesItemItem>,
                response: Response<VercesItemItem>
            ) {
                if (response.isSuccessful && response.body()!= null){
                    trySend(response.body()!!)
                    close()
                }
            }

            override fun onFailure(call: Call<VercesItemItem>, t: Throwable) {
                close(t)
            }
        }
        ApiUtilities.api.getVarseDetail(chapterNumber,verseNumber).enqueue(callback)
        awaitClose{}
    }

    suspend fun insertChapters(savedChapter : savedChapters) = savedChaptersDao.insertChapters(savedChapter)

    fun getSavedChapters(): LiveData<List<savedChapters>> = savedChaptersDao.getSavedChapters()

    fun getaParticularChapter(chapter_number : Int) : LiveData<savedChapters> = savedChaptersDao.getaParticularChapter(chapter_number)

    //Saved Verses

    suspend fun insertVerse(verses: savedVerse)= savedVersesDao.insertVerse(verses)

    fun getAllSavedVerses():LiveData<List<savedVerse>> = savedVersesDao.getAllSavedVerses()

    fun getParticularVerse(chapter_number: Int,verseNumber:Int):LiveData<savedVerse> = savedVersesDao.getParticularVerse(chapter_number, verseNumber)

    suspend fun deleteAParticularVerse(chapter_number: Int,versesNumber:Int) = savedVersesDao.deleteAParticularVerse(chapter_number, versesNumber)
}
