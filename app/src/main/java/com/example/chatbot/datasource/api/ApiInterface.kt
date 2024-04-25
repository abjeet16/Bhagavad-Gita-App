package com.example.chatbot.datasource.api

import com.example.models.ChaptersItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiInterface {

    @Headers(
        "Accept : application/json",
        "X-RapidAPI-Key : 698288842dmsh3ac4bfc14995624p169245jsn2a84c745a562",
        "X-RapidAPI-Host : bhagavad-gita3.p.rapidapi.com"
    )

    @GET("/v2/chapters/")
    fun getAllChapter():Call<List<ChaptersItem>>
}