package com.example.Bhagavad_gita_app.Repository

import androidx.lifecycle.LiveData
import com.example.Bhagavad_gita_app.datasource.api.ApiUtilities
import com.example.Bhagavad_gita_app.datasource.api.RoomDB.SavedChaptersDao
import com.example.Bhagavad_gita_app.datasource.api.RoomDB.savedChapters
import com.example.models.ChaptersItem
import com.example.models.VercesItemItem
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppRepository(val savedChaptersDao: SavedChaptersDao) {
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
}