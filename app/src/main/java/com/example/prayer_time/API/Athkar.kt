package com.example.prayer_time.API

import kotlinx.serialization.Serializable


@Serializable
data class Athkar(
    val morning_azkar: List<Zikr>,
    val evening_azkar: List<Zikr>
)

@Serializable
data class Zikr(
    val id: Int,
    var text: String,
    val count: Int
)