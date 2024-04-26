package com.example.chatbot.datasource.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiUtilities {

    val header = mapOf<String,String>(
        "Accept" to "application/json",
        "X-RapidAPI-Key" to  "698288842dmsh3ac4bfc14995624p169245jsn2a84c745a562",
        "X-RapidAPI-Host" to  "bhagavad-gita3.p.rapidapi.com"
    )

    val client = OkHttpClient.Builder().apply {
        addInterceptor{chain->
            val newRequired = chain.request().newBuilder().apply {
                header.forEach{key,value->addHeader(key,value)}
            }.build()

            chain.proceed(newRequired)
        }
    }.build()

    val api :ApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl("https://bhagavad-gita3.p.rapidapi.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }
}