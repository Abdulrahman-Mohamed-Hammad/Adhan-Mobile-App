package com.example.prayer_time.AdaptiveScreen

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.example.prayer_time.D
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours


class adaptive() {
//var check = true
    val width: Float = 390f
    var screenHeight: Float=0f
    var screenWidth: Float = 0f

    var sinceIn =0L
    @Composable
    fun AdaptiveText(sp: TextUnit): TextUnit {
        var text by remember {
            mutableStateOf(0.sp)
        }
        text = (screenWidth * sp) / (width.toFloat())

//        Log.d("text", text.toString())
        return text
    }

    @Composable
    fun GetDimensions() {
        val configration = LocalConfiguration.current
         screenHeight = configration.screenHeightDp.toFloat()
        screenWidth = configration.screenWidthDp.toFloat()
//        Log.d("width", screenWidth.toString())
//        Log.d("Height", screenHeight.toString())
    }

    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.O)
    fun GetDiffrenceTime(Start: String, End:String,check:Boolean): String {
       var endTime:LocalTime
        var start = Start.substringBefore(" ")
        //    Log.d("format",start)
        var currentTime = start.split(":")
        var end = End.substringBefore(" ")
        var TargetTime = end.split(":")
        var startTime =
            LocalTime.of(currentTime[0].toInt(), currentTime[1].toInt(), currentTime[2].toInt())
        endTime = LocalTime.of(TargetTime[0].toInt(), TargetTime[1].toInt(), 0)
        var startTimeS = startTime.toSecondOfDay()
        var  endTimeS=endTime.toSecondOfDay()

        if(D.api.IndexCurentPrayer == 5 && check)
        {
          var  DiffrenceTime = (86400L - startTimeS)+endTimeS
            return secoundToTimeFormat(DiffrenceTime)
        }
        var DiffrenceTime =  endTimeS-startTimeS.toLong()
        return secoundToTimeFormat(DiffrenceTime)
    }

@SuppressLint("SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.O)
fun SinceIn():String {
    sinceIn =sinceIn+1
  val Time =  secoundToTimeFormat(sinceIn)
//    Log.d("StopWAtch", Time)
    return Time
}

    @RequiresApi(Build.VERSION_CODES.O)
    fun secoundToTimeFormat(seconds: Long): String {
        var duration = Duration.parse("PT${seconds}S")
        return duration.toComponents { hours, minutes, seconds, _ ->
            String.format("%02d:%02d:%02d", hours, minutes, seconds)
        }
    }
}