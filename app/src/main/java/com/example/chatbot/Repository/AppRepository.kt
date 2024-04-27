package com.example.chatbot.Repository

import com.example.chatbot.datasource.api.ApiUtilities
import com.example.models.ChaptersItem
import com.example.models.VercesItemItem
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppRepository {
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
}