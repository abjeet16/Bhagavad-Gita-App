package com.example.Bhagavad_gita_app.datasource.api.RoomDB

import androidx.room.TypeConverter
import com.example.models.Commentary
import com.example.models.Translation
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AppTypeConverter {
    @TypeConverter
    fun fromListToString(list:List<String>):String{
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringToList(string: String):List<String>{
        return Gson().fromJson(string,object : TypeToken<List<String>>(){}.type)
    }

    @TypeConverter
    fun fromCommentaryToString(commentary: List<Commentary>):String{
        return Gson().toJson(commentary)
    }

    @TypeConverter
    fun fromStringToCommentary(string: String):List<Commentary>{
        return Gson().fromJson(string,object : TypeToken<List<Commentary>>(){}.type)
    }

    @TypeConverter
    fun fromTranslationToString(translation: List<Translation>):String{
        return Gson().toJson(translation)
    }

    @TypeConverter
    fun fromStringToTranslation(string: String):List<Translation>{
        return Gson().fromJson(string,object : TypeToken<List<Translation>>(){}.type)
    }

}