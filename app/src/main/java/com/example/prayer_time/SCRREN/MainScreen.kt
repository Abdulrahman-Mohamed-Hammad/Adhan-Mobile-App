package com.example.prayer_time.SCRREN
import android.app.Activity
import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.graphics.RenderEffect
import android.graphics.Shader
import android.media.MediaPlayer
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prayer_time.AdaptiveScreen.adaptive
import com.example.prayer_time.D
import com.example.prayer_time.NotifactionAndPermission.Notification
import com.example.prayer_time.R

import com.example.prayer_time.ui.theme.DownLight
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.time.Duration.Companion.seconds
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.ui.geometry.Offset
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import java.time.LocalDateTime


var change = mutableStateOf(true)
var Bool = mutableStateOf(false)
val Dim = adaptive()
var play = MediaPlayer.create(D.mainActivity, R.raw.full)
var playFajr = MediaPlayer.create(D.mainActivity, R.raw.fajr)

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Stable
@Composable
fun Prayer(navBarcontroller: NavHostController) {
var flag by remember {
    mutableStateOf(false)
}
    val BlurEffect = remember {
        RenderEffect
            .createBlurEffect(100f, 100f, Shader.TileMode.CLAMP)
            .asComposeRenderEffect()
    }
    Column {
        Dim.GetDimensions()
        Box()
        {

            D.api.checkCurrentPrayer()

            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val canvasWidth = maxWidth  // Dp value
                val canvasHeight = maxHeight
                Canvas(modifier = Modifier
                    .size(canvasWidth, canvasHeight)
                    .graphicsLayer {
                        clip = false
                        renderEffect = BlurEffect
                    }) {
                    drawRect(
                        brush = Brush.linearGradient(colors = DownLight, start = Offset.Zero),
                        topLeft = Offset(x = 0f, y = size.height / 1.5f),
                        alpha = 0.24f
                    );
                }
            }
            Column {
                Badge(R.drawable.marker_pin_01, "${D.api.country} , ${D.api.city}")
                Spacer(modifier = Modifier.padding(top = 30.dp))
                Content(navBarcontroller){flag =true}
            }
            Table(navBarcontroller,flag)
        }
    }

}

@Composable
fun Badge(icon: Int, text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 75.dp), contentAlignment = Alignment.TopCenter
    ) {
        Card(
            onClick = { /*TODO*/ }, modifier = Modifier.graphicsLayer(alpha = 0.9f),
            shape = RoundedCornerShape(16.dp),
            colors = CardColors(
                containerColor = Color(0x35000000),
                contentColor = MaterialTheme.colorScheme.onSurface,
                disabledContentColor = MaterialTheme.colorScheme.background,
                disabledContainerColor = Color.Transparent

            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 12.dp, end = 10.dp, top = 4.dp, bottom = 4.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = icon),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.padding(horizontal = 1.dp))
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }


    }
}
@SuppressLint("NewApi", "SuspiciousIndentation")
@Composable
fun Content(navBarcontroller: NavHostController,onClick: () -> Unit) {
    var listTheame = listOf(
        MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.tertiary),
        MaterialTheme.typography.bodyLarge.copy(
            color = MaterialTheme.colorScheme.onSurface
        ),
        MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.tertiary)
    )
    var Notificationn by remember {
        mutableStateOf(true)
    }
    var f by remember {
        mutableStateOf(D.api.IndexNextPrayer)
    }
    var dataHij by remember {
        mutableStateOf(
            "${D.DataSet.get(D.day - 1)!!.date.hijri.day} ${D.DataSet.get(D.day - 1)!!.date.hijri.month.en} ${
                D.DataSet.get(
                    D.day - 1
                )!!.date.hijri.year
            }H "
        )
    }

    var list: SnapshotStateList<Any?> = remember {
        mutableStateListOf(
            dataHij,
            D.GetTime(),
            (D.Hash1[f]?.keys?.joinToString("", "") ?: "") + " will begin in "
        )
    }

    var check by remember {
        mutableStateOf(true)
    }
    var DIFF by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    var Notification = Notification()

    LaunchedEffect(key1 = true) {
        withContext(Dispatchers.IO) {
        while (true) {
                val currentTime = D.GetTime()

                list[1] = D.TimeFoRMAT24HOURS
                if (check) {
                    if (LocalTime.parse(D.TimeFoRMAT24HOURS).toSecondOfDay() == 1) {
                        var datee1 = LocalDateTime.now()
                        D.DateSetter(datee1)
                        if (D.month <= 12) {
                            Toast.makeText(
                                context,
                                "App need to restart Please open Internet",
                                Toast.LENGTH_LONG
                            ).show()
                            D.Delete_File();(context as? Activity)?.finish()
                        } else if (D.day >= D.api.calander.get(D.month)!!) {
                            D.ResponseSet()
                        }
                    }
                    if (D.api.IndexCurentPrayer == 5 && LocalTime.parse(D.TimeFoRMAT24HOURS)
                            .toSecondOfDay() > 43200
                    ) {
                        DIFF = Dim.GetDiffrenceTime(
                            D.TimeFoRMAT24HOURS,
                            D.DataSet!!.get(D.day).timings.Fajr!!, true
                        )
                    } else {
                        DIFF = Dim.GetDiffrenceTime(
                            D.TimeFoRMAT24HOURS,
                            D.Hash1[f]?.get((D.Hash1[f]?.keys?.joinToString("", "") ?: ""))!!, false
                        )
                    }
                    f = D.api.CHeckNextPrayer1(D.TimeFoRMAT24HOURS)!!
                }
            val formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.ENGLISH)
          DIFF = splitArabicToEnglish(DIFF)
                if (LocalTime.parse(DIFF,formatter).toSecondOfDay() == 0) {
                    Notification.notification("123456","Pray",
                        context,
                        D.Hash1[f]?.get((D.Hash1[f]?.keys?.joinToString("", "") ?: ""))!!,
                        "اللهم صلي وسلم على نبيتا محمد ",
                        NotificationManager.IMPORTANCE_HIGH,
                        android.app.Notification.PRIORITY_HIGH
                    )
                    Bool.value = true
                    PryerNow(context)
                    list[2] = (D.Hash1[D.api.IndexCurentPrayer]?.keys?.joinToString("", "")
                        ?: "") + " since in "
                    check = false
                    change.value = !change.value
                    Dim.sinceIn = 0L
                    onClick()
                }
                if (check == false) {
                    DIFF = Dim.SinceIn()
                }
                if (Dim.sinceIn == 60 * 30L) {
                    list[2] = " ${(D.Hash1[f]?.keys?.joinToString("", "") ?: "")} +  will begin in "
                    check = true
                }
                delay(1.seconds)
            }
        }
    }
    var i = 0

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 28.dp, bottom = 4.dp)
//            ,    .wrapContentWidth(),
        , verticalArrangement = Arrangement.Center
    ) {
        list.forEach { index ->
            if (i == list.size - 1) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = " $index".trimStart(),
                        style = listTheame[i],
                    )
//                        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                    Box(
                        modifier = Modifier
                            .size(width = 70.dp, height = 28.dp)
                            .background(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                    )

                    {
                        Text(
                            text = if (check == true) {
                                "- ${DIFF}"
                            } else {
                                "+ ${DIFF}"
                            },
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .offset(x = 1.dp, y = -9.dp)
                                .padding(start = 1.dp, end = 1.dp, top = 0.5.dp, bottom = 0.5.dp)
                                .fillMaxWidth(),
                        )


                    }

                }
            } else {
                Text(
                    text = " $index".trimStart(),
                    style = listTheame[i],
                    modifier = Modifier
                        .padding(bottom = 4.dp)

                )
            }
            i++
        }


    }
        Notification.notification(
        "1234","Time",   context,
            " ",
            "${list[2]} ${DIFF}",
            NotificationManager.IMPORTANCE_LOW,
            android.app.Notification.PRIORITY_LOW
        )


}
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Stable
@SuppressLint("SuspiciousIndentation", "RememberReturnType")
@Composable
fun Table(navBarcontroller: NavHostController, flag: Boolean) {

    val IconTime =
        listOf(
            R.drawable._2_cloudy_clear_at_times,
            R.drawable._2_cloudy_clear_at_times__2_,
            R.drawable._2_sunny,
            R.drawable._2_cloudy_clear_at_times__2_,
            R.drawable.fi_1852617__111_,
            R.drawable._2_clear_night
        )

    val TimeDown = remember {
        listOf("Dawn", "Sunrise", "Noon", "Afternoon", "Sunset", "Night")
    }
    var text by remember {
        mutableStateOf(
            D.DataSet[D.api.NextDayM]!!.date.readable
        )
    }

    var prayer = remember {
        mutableStateListOf<String>().apply {
            addAll(
                D.api.get(D.month, D.day - 1).filterNotNull()
            )
        }
    }
    var currentPrayer by remember {
        mutableStateOf(D.api.IndexCurentPrayer)
    }
    var CHECK by remember {
        mutableStateOf(false)
    }
    val COLORT1 = MaterialTheme.colorScheme.onSurface
    val COLORT2 = MaterialTheme.colorScheme.onPrimary
    val COLORcard = MaterialTheme.colorScheme.surface.copy()
    var style = remember {
        mutableStateListOf<Color>(COLORT1, COLORT2, Color.Unspecified, COLORcard)
    }
    var style1 = remember {
        mutableStateListOf<Color>(Color.White, Color.White, Color.White, Color(0xFFE47E5D))
    }

    var keyy by remember {
        mutableStateOf(D.api.NextDayM)
    }

if (flag)
{
    currentPrayer = D.api.checkCurrentPrayer()
}
    LazyColumn() {
        item(key = keyy) {

            Header(
                navBarcontroller,
                text1 = text.toString(),
                icon1 = R.drawable.chevron_left,
                icon2 = R.drawable.chevron_right, CHECK = CHECK
            ) { value ->

                text = D.DataSet[D.api.NextDayM].date.readable
//                text[1] = ""
                prayer.clear()
                prayer.apply { addAll(D.api.get(D.month, D.api.NextDayM).filterNotNull()) }
                CHECK = value
                style[0] = COLORT1;style[1] = COLORT2
                style[2] = Color.Unspecified;style[3] = COLORcard
                keyy = D.api.NextDayM
            }
        }


        itemsIndexed(
            prayer,
            key = { index, item -> " ${index.hashCode()}${item.hashCode()}" }) { index, item ->
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                color = MaterialTheme.colorScheme.background,
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                shape = when {
                    index == 0 -> RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    else -> {
                        RoundedCornerShape(0.dp)
                    }
                }
            ) {
                if (currentPrayer == index && !CHECK) {
                    TimeSection(
                        text = D.Time[index],
                        TimeDown[index],
                        convertToformat12_HOURS(item).toString(),
                        IconTime[index], style1, index
                    )
                } else {
                    TimeSection(
                        text = D.Time[index],
                        TimeDown[index],
                        convertToformat12_HOURS(item).toString(),
                        IconTime[index], style, index
                    )
                }


            }
        }
    }

}
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
@Stable
fun Header(
    navBarcontroller: NavHostController,
    text1: String,
    icon1: Int,
    icon2: Int,
    CHECK: Boolean,
    onClick: (Boolean) -> Unit
) {
    var i by remember {
        mutableStateOf(0.sp)
    }
    Spacer(
        modifier = if (CHECK) {
            Modifier.padding(top = 244.dp)
        } else {
            Modifier.padding(top = 292.dp)
        }
    )
    if (CHECK) {
        Box(
            modifier = Modifier
                .padding(end = 25.dp)
                .fillMaxWidth(), contentAlignment = Alignment.CenterEnd
        ) {
            IconButton(onClick = { D.api.NextDayM = D.day - 1;onClick(false) }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.group_9),
                    contentDescription = "",
                    tint = Color.Unspecified, modifier = Modifier.padding(bottom = 2.dp),
                )
            }
        }
    }
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .height(80.dp)
            .padding(1.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                shape = RoundedCornerShape(16.dp)
            ),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.background,
            disabledContainerColor = Color.Unspecified,
            disabledContentColor = Color.Unspecified,
            contentColor = Color.Unspecified
        ),

        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize()
        ) {
            IconButton(
                onClick = {
                    D.api.GoLeft();
                    if (D.api.NextDayM !=D.day-1) {
                        onClick(true)
                    } else {
                        onClick(false)
                    }
                },
                enabled = if(D.api.NextDayM==0){false}else {true}
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = icon1),
                    contentDescription = "",
                    tint = if(D.api.NextDayM==0){Color.Unspecified}else{MaterialTheme.colorScheme.onSurface}
                )
            }
            i = Dim.AdaptiveText(18.sp)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        //    D.saveUseSharedPrefrance("Dayy",(D.day-1).toString())
                        D.api.NextDayM = D.day - 1
                        navBarcontroller.navigate("B") {
                            popUpTo("A") {
                                inclusive = false
                            }
                        }
                    }

            ) {

                Text(
                    text = text1,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondary, fontSize = i
                    ), modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            IconButton(
                onClick = { D.api.GoRight();
                    if (D.api.NextDayM !=D.day-1) {
                        onClick(true)
                    } else {
                        onClick(false)
                    }},
                enabled = if(D.api.NextDayM==D.api.calander.get(D.month)!! -1){false}else {true},
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = icon2),
                    contentDescription = "",
                    tint = if(D.api.NextDayM==D.api.calander.get(D.month)!! -1){Color.Unspecified}else{MaterialTheme.colorScheme.onSurface}
                )
            }
        }
    }

    Spacer(modifier = Modifier.padding(bottom = 12.dp))
}
@RequiresApi(Build.VERSION_CODES.TIRAMISU)

@Stable
@SuppressLint("UnrememberedMutableState")
@Composable
fun TimeSection(
    text: String,
    Text: String,
    Text3: String,
    icon: Int,
    style: SnapshotStateList<Color>,
    index: Int
) {
    var Time by remember {
        mutableStateOf(Text3)
    }
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(start = 12.dp, end = 12.dp, top = 12.dp, bottom = 12.dp)
            .fillMaxWidth()
            .padding(1.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                shape = RoundedCornerShape(8.dp)
            ),
        colors = CardColors(
            containerColor = style[3],
            contentColor = Color.Unspecified,
            disabledContentColor = Color.Unspecified,
            disabledContainerColor = Color.Unspecified
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            // horizontalArrangement = Arrangement.Start,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = icon),
                contentDescription = "",
                tint = style[2],

                )
            Spacer(modifier = Modifier.padding(horizontal = 6.dp))
            Column() {
                Text(
                    text = text, style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 16.sp,
                        color = style[0],
                    )
                )

                Text(
                    text = Text,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = style[1],
                        fontSize = 14.sp
                    )
                )
            }
            Text(
                text = Time,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 12.dp),
                style = MaterialTheme.typography.bodyMedium.copy(color = style[1], fontSize = 16.sp)
            )


        }
    }

}

fun convertToformat12_HOURS(timeString: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("H:mm")
    val outputFormatter =
        DateTimeFormatter.ofPattern("h:mm a",Locale.ENGLISH) // 'h' removes leading zero, 'a' adds AM/PM
    val time = LocalTime.parse(timeString, inputFormatter)
    return time.format(outputFormatter).uppercase(Locale.getDefault())
}

@RequiresApi(Build.VERSION_CODES.R)
fun PryerNow(context: Context) {
    if(D.api.IndexNextPrayer == 1)
    {
    playFajr.start()
    }
    else if(D.api.IndexNextPrayer == 0  || D.api.IndexNextPrayer == 3 || D.api.IndexNextPrayer == 4 || D.api.IndexNextPrayer == 5){
        play.start()
    }
}


fun StopPryerNow() {
    if(D.api.IndexNextPrayer == 1)
    {
        playFajr.stop()
    }
    else if(D.api.IndexNextPrayer == 0  || D.api.IndexNextPrayer == 3 || D.api.IndexNextPrayer == 4 || D.api.IndexNextPrayer == 5){
        play.stop()
    }
}
fun splitArabicToEnglish(Time:String): String {
    return Time.map {
        when (it) {
            '٠' -> '0'
            '١' -> '1'
            '٢' -> '2'
            '٣' -> '3'
            '٤' -> '4'
            '٥' -> '5'
            '٦' -> '6'
            '٧' -> '7'
            '٨' -> '8'
            '٩' -> '9'
            else -> it
        }
    }.joinToString("")
    }


