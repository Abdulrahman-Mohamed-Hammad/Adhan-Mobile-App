package com.example.prayer_time.API

import android.annotation.SuppressLint
import android.content.Context


import android.icu.util.TimeZone
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.prayer_time.D
import com.example.prayer_time.SCRREN.convertToformat12_HOURS
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.prefs.Preferences
import kotlin.time.Duration.Companion.seconds
import android.content.SharedPreferences

class GetData() {
    var F = File()
    lateinit var TimeFoRMAT24HOURS: String

    var flag = mutableStateOf(true)
    var Hash1 = HashMap<Int, HashMap<String?, String?>>()
    val Time = listOf("Fajr", "Shuruk", "Dhuhr", "Asr", "Maghrib", "Isha")
    var day: Int = 0
    var month: Int = 0
    var year: Int = 0
    lateinit var DataSet: List<data>
    lateinit var api: Api
    lateinit var mainActivity: Context
    lateinit var latitude: String
    lateinit var longitude: String


    @SuppressLint("SuspiciousIndentation")
    fun get(latitude: String, longitude: String, mainActivity: Context) {

        if(F.FileIsExist(mainActivity,"Prayer.json"))
        {
            getsaveUseSharedPrefrance()
        }

        var i = 0
        api = Api(year.toString(), latitude, longitude)

        saveUseSharedPrefrance("latitude", latitude)
        saveUseSharedPrefrance("longitude", longitude)
        check(mainActivity)
       ResponseSet()
        while (flag.value) {
                val value = api.get(D.api.NextMonthM, D.day-1)
                value.forEach()
                { index ->
                    var Hash = HashMap<String?, String?>()
                    Hash.set(Time[i], index)
                    Hash1.set(i, Hash)
                    i++
                }
                flag.value = false

        }

    }

    fun check(mainActivity: Context) {
        if (!F.FileIsExist(mainActivity,"PPrayer.json")) {

            runBlocking {
                launch {Delete_File(); api.GET1() }
            }
            while(api.response ==null && api.response1 == null){

            }
//            (java.io.File(mainActivity.filesDir, "ppp.json").delete())
            F.CreateAndWriteInFile(mainActivity,"PPrayer.json",D.api.response)
            F.CreateAndWriteInFile(mainActivity,"AAlkar.json",D.api.response1)

        } else {
            getsaveUseSharedPrefrance()
           D.api.response =  F.LoadData(mainActivity,"PPrayer.json",D.api.response)
            D.api.response1 =  F.LoadData(mainActivity,"AAlkar.json",D.api.response1)
        }
        D.api.response1!!.morning_azkar[1].text ="${D.api.response1!!.morning_azkar[1].text}\n\n ${D.api.response1!!.morning_azkar[2].text} \n\n${D.api.response1!!.morning_azkar[3].text}"
       D.api.response1!!.evening_azkar[2].text ="${D.api.response1!!.evening_azkar[2].text}\n\n ${D.api.response1!!.evening_azkar[3].text} \n\n ${D.api.response1!!.evening_azkar[4].text}"

    }
fun Delete_File()
{
//    (java.io.File(mainActivity.filesDir, Name).delete())
    mainActivity.cacheDir.deleteRecursively()
   mainActivity.filesDir.deleteRecursively()
}
    @RequiresApi(Build.VERSION_CODES.O)
    fun GetTime(): String {
        val format24 =
            DateTimeFormatter.ofPattern("HH:mm:ss",Locale.ENGLISH)
        val format12 =
            DateTimeFormatter.ofPattern("hh:mm:ss a").withLocale(Locale.ENGLISH)
        TimeFoRMAT24HOURS = LocalTime.now().format(format24).toString()
        return LocalTime.now().format(format12).toString()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun DateSetter(Date: LocalDateTime) {
        var TimeZone = TimeZone.getDefault().id
        day = Date.dayOfMonth
        month = Date.monthValue
        year = Date.year
      //  Log.d("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", Date.month.toString())
    }
    fun ResponseSet()
    {
        DataSet = D.api.response!!.data[month]!!
        D.api.response =null
    }
fun GetPrayFromObjectTime(Value: String,index:Int): String? {
  return  when(Value)
    {
        "Fajr" ->{convertToformat12_HOURS(D.DataSet[index].timings.Fajr!!.substringBefore(" ")) }
         "Shuruk" -> convertToformat12_HOURS(D.DataSet[index].timings.Sunrise!!.substringBefore(" "))
      "Dhuhr" -> convertToformat12_HOURS(D.DataSet[index].timings.Dhuhr!!.substringBefore(" "))
      "Asr" -> convertToformat12_HOURS(D.DataSet[index].timings.Asr!!.substringBefore(" "))
      "Maghrib" -> convertToformat12_HOURS(D.DataSet[index].timings.Maghrib!!.substringBefore(" "))
      "Isha" -> convertToformat12_HOURS(D.DataSet[index].timings.Isha!!.substringBefore(" "))
      else -> ""
    }
}

fun saveUseSharedPrefrance(Name:String, data:String)
{
    val sharedPreferences: SharedPreferences =mainActivity.getSharedPreferences("SharedPrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    editor.apply { putString(Name,data)
    }.apply()
}
    fun get11(name:String): String {
        val sharedPreferences = mainActivity.getSharedPreferences("SharedPrefs", Context.MODE_PRIVATE)
       return sharedPreferences.getString(name,null).toString()


    }
    fun getsaveUseSharedPrefrance()
    {
        val sharedPreferences = mainActivity.getSharedPreferences("SharedPrefs", Context.MODE_PRIVATE)
        latitude = sharedPreferences.getString("latitude",null).toString()
        longitude  = sharedPreferences.getString("longitude",null).toString()

    }

    fun set(mainActivity:Context) {
this.mainActivity=mainActivity
    }
}