package com.example.Bhagavad_gita_app.datasource.api

import com.example.models.ChaptersItem
import com.example.models.VercesItemItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("/v2/chapters/")
    fun getAllChapter():Call<List<ChaptersItem>>

    @GET("/v2/chapters/{chapterNumber}/verses/")
    fun getVarses(@Path("chapterNumber") chapterNumber:Int) : Call<List<VercesItemItem>>

    @GET("/v2/chapters/{chapterNumber}/verses/{verseNumber}/")
    fun getVarseDetail(
        @Path("chapterNumber") chapterNumber: Int,
        @Path("verseNumber") versesNumber : Int)
    :Call<VercesItemItem>
}