package com.example.prayer_time.API


import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.prayer_time.D
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.time.LocalTime


class Api(var year: String, var latitude: String, var longitude: String) {
    lateinit var NextPrayer: String
    var IndexCurentPrayer: Int? = null
    var IndexNextPrayer: Int? = null
    var Client: HttpClient
    lateinit var city: String
    lateinit var country: String
    var response: prayerTime1? = null
    var response1: Athkar? = null
    var NextDayM = D.day-1
    var NextMonthM = D.month
    var febDays = if (D.year % 4 == 0) 29 else 28
    var calander = mapOf(
        1 to 31,
        2 to febDays ,
        3 to 31,
        4 to 30,
        5 to 31,
        6 to 30,
        7 to 31,
        8 to 31,
        9 to 30,
        10 to 31,
        11 to 30,
        12 to 31,
    )

    init {
        Client = HttpClient(Android)
        {
            install(ContentNegotiation)
            {
                json(Json { ignoreUnknownKeys = true })
            }
        }

    }

    suspend fun GET1() {
        val url = "https://api.aladhan.com/v1/calendar/${year}?latitude=${latitude}&longitude=${longitude}"
        response = Client.get(url).body()
        val url1 = "https://alquran.vip/APIs/azkar"
        response1 = Client.get(url1).body()
    }

    fun get(Month:Int ,Day:Int): List<String?> {

        var list =
            listOf(
                D.DataSet[Day].timings.Fajr!!.substringBefore(" "),
                D.DataSet[Day].timings.Sunrise!!.substringBefore(" "),
                D.DataSet[Day].timings.Dhuhr!!.substringBefore(" "),
                D.DataSet[Day].timings.Asr!!.substringBefore(" "),
                D.DataSet[Day].timings.Maghrib!!.substringBefore(" "),
                D.DataSet[Day].timings.Isha!!.substringBefore(" ")
//                "05:55","10:55","11:55","16:12","18:08","22:59"
            )
        return list
    }


    fun GoLeft()
    {
        if(NextDayM > 0 )
        {
            NextDayM-=1
        }
    }
    fun GoRight()
    {
        if(NextDayM < calander.get(NextMonthM)!!-1)
        {
            NextDayM+=1
        }

    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun pray(pray: String): Int {
        var time = pray.split(":")
        var timeP = LocalTime.of(time[0].toInt(), time[1].toInt(), 0)
        return timeP.toSecondOfDay()


    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun CHeckNextPrayer1(currentTime: String): Int? {
        var time = currentTime.split(":")
        var timeP = LocalTime.of(time[0].toInt(), time[1].toInt(), time[2].toInt())
        var timeInt = timeP.toSecondOfDay()
        var Prayer = get(D.api.NextMonthM,D.api.NextDayM)
        when {
            timeInt >= pray(Prayer[0]!!) && timeInt < pray(Prayer[1]!!) -> {
                NextPrayer = (D.Hash1[1]?.keys?.joinToString("", "") ?: "");IndexCurentPrayer =
                    0;IndexNextPrayer = 1; return 1
            }

            timeInt >= pray(Prayer[1]!!) && timeInt < pray(Prayer[2]!!) -> {
                NextPrayer = (D.Hash1[2]?.keys?.joinToString("", "") ?: "");IndexCurentPrayer =
                    1;IndexNextPrayer = 2; return 2
            }

            timeInt >= pray(Prayer[2]!!) && timeInt < pray(Prayer[3]!!) -> {
                NextPrayer = (D.Hash1[3]?.keys?.joinToString("", "") ?: "");IndexCurentPrayer =
                    2;IndexNextPrayer = 3; return 3
            }

            timeInt >= pray(Prayer[3]!!) && timeInt < pray(Prayer[4]!!) -> {
                NextPrayer = (D.Hash1[4]?.keys?.joinToString("", "") ?: "");IndexCurentPrayer =
                    3;IndexNextPrayer = 4; return 4
            }

            timeInt >= pray(Prayer[4]!!) && timeInt < pray(Prayer[5]!!) -> {
                NextPrayer = (D.Hash1[5]?.keys?.joinToString("", "") ?: "");IndexCurentPrayer =
                    4;IndexNextPrayer = 5; return 5
            }
            (timeInt >= pray(Prayer[5]!!)  ) ||  (timeInt < pray(Prayer[0]!!))-> {
                NextPrayer = (D.Hash1[0]?.keys?.joinToString("", "") ?: "");IndexCurentPrayer =
                    5;IndexNextPrayer = 0; return 0
            }
        }
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SuspiciousIndentation")
    fun checkCurrentPrayer(): Int? {
        D.GetTime()
        CHeckNextPrayer1(D.TimeFoRMAT24HOURS)
        return IndexCurentPrayer

    }

}