package com.example.prayer_time.API

import kotlinx.serialization.Serializable




@Serializable
data class prayerTime1(val code: Int, val status: String, val data:Map<Int,List<data>> )
@Serializable
data class data(
    val timings: Timings, val date: date
)

@Serializable
data class Timings(
    var Fajr: String?=null,
    val Sunrise: String?=null,
    val Dhuhr: String?=null,
    val Asr: String?=null,
    val Maghrib: String?=null,
    val Isha: String?=null
)
@Serializable
data class date(val readable: String, val hijri: hijri,val gregorian:gregorian)



@Serializable
data class hijri(val date: String, val format: String, val weekday: weekday, val month:month,val day:String,val year:String)
@Serializable
data class weekday(val en: String, val ar: String)
@Serializable
data class month(val en: String, val ar: String)
@Serializable
data class gregorian (val month:MonthName)
@Serializable
data class MonthName(val en:String)








