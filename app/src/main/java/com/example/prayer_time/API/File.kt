package com.example.prayer_time.API

import android.content.Context
import android.util.Log
import com.example.prayer_time.D
import kotlinx.serialization.json.Json
import java.io.File



class File {

    inline fun <reified T>CreateAndWriteInFile(context: Context, NAME:String, response: T) {
      //  Log.d("Test", D.api.response.toString())
        val json = Json.encodeToString(response)
        context.openFileOutput(NAME, Context.MODE_PRIVATE).use {
            it.write(json.toByteArray())

        }
    }
    inline fun <reified T > LoadData(context: Context, NAME:String, response: T): T {

    val json1 = context.openFileInput(NAME).bufferedReader().use { it.readText() }
    var savedData = Json.decodeFromString<T>(json1)
    return savedData


}
    fun FileIsExist(context: Context,Name:String): Boolean {

        if(File(context.filesDir,Name).exists())
        {

            return true
        }
        else{

            return false}
    }

}