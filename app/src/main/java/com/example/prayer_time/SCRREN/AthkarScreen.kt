package com.example.prayer_time.SCRREN

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.prayer_time.API.Zikr
import com.example.prayer_time.D
import com.example.prayer_time.R
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition", "SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable


fun size(onClick: () -> Unit)
{
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(end = 16.dp), contentAlignment = Alignment.CenterEnd) {
        IconButton(onClick = {onClick()}) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.size),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onSurface,
                )
        }
    }

}

@Stable
@SuppressLint("CoroutineCreationDuringComposition", "SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun TopNav(navBarcontroller: NavHostController, size: Float, LineHeight: Float) {

    var x = MaterialTheme.colorScheme.tertiary
    var y = MaterialTheme.colorScheme.onSurface

    var Change by remember { mutableStateOf(y) }
    var change1 by remember { mutableStateOf(x) }
    val coroutineScope = rememberCoroutineScope()
    var pagerState = rememberPagerState(initialPage = 0, pageCount = { 2 })

    Scaffold(
        topBar = {

            Column() {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        .background(MaterialTheme.colorScheme.background)
                        .padding(top = 9.dp, bottom = 9.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = "أذكار الصباح",
                            modifier = Modifier.clickable {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(0)
                                };Change = y;change1 = x
                            },
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                lineHeight = 24.sp,
                                color = Change, fontFamily = FontFamily(
                                    Font(R.font.playpenwansqrabic_medium) ))

                        )
                        Text(
                            text = "أذكار المساء",
                            modifier = Modifier.clickable {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(1)
                                };Change = x;change1 = y
                            },
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                lineHeight = 24.sp,
                                color = change1, fontFamily = FontFamily(
                                    Font(R.font.playpenwansqrabic_medium) ))
                            )


                    }
                }
                //
            }

        }) { innerPadding ->
        HorizontalPager(state = pagerState) { page ->
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding((innerPadding))
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                when {
                    page == 0 -> {
                        Change = y;change1 = x;getAlathkar(
                            D.api.response1!!.morning_azkar,
                            D.api.response1!!.morning_azkar.size, true,size,LineHeight
                        )
                    }

                    page == 1 -> {
                        Change = x;change1 = y;getAlathkar(
                            D.api.response1!!.evening_azkar,
                            D.api.response1!!.evening_azkar.size, false, size, LineHeight
                        )
                    }
                }
            }
        }

    }
}
@Stable
@Composable
fun getAlathkar(Azkar: List<Zikr>, Size: Int, flag: Boolean, value: Float, LineHeight: Float) {
    var size: Int
    var Filter: List<Zikr>
    if (flag) {
        Filter = Azkar.filterNot { it.id == 3 || it.id == 4 }
    } else {
        Filter = Azkar.filterNot { it.id == 4 || it.id == 5 }

    }
    size = Filter.size
    LazyColumn {
        items(size, key = { it }) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 13.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                shape = RoundedCornerShape(16.dp),
                colors = CardColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = Color.Unspecified,
                    disabledContainerColor = Color.Unspecified,
                    disabledContentColor = Color.Unspecified
                )
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = Filter[item].text,
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.tertiary, fontFamily = FontFamily(
                            Font(R.font.playpenwansqrabic_medium) ), fontSize = value.sp, lineHeight = LineHeight.sp),
                        textAlign = TextAlign.Right,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp)
                    )
                    Text(
                        text = NumberToArabiccount(Filter[item].count),
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface, fontFamily = FontFamily(
                            Font(R.font.playpenwansqrabic_medium) ),fontSize = value.sp, lineHeight = LineHeight.sp),
                        textAlign = TextAlign.Right,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

fun NumberToArabiccount(Number: Int): String {
    when (Number) {
        1 ->   return "مرة واحدة "
        3 -> return "ثلات مرات "
        4 -> return "أربع مرات"
        7 -> return "سبع مرات"
        10 -> return "عشر مرات"
        100 -> return "مائة مرة "
    }
    return ""
}