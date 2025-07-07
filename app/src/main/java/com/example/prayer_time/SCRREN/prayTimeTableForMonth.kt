package com.example.prayer_time.SCRREN

import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

import com.example.prayer_time.D

import com.example.prayer_time.R
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun PrayerTimeForMonth(navBarcontroller: NavHostController, title: String, flag: Boolean) {
    var size by remember {
        mutableStateOf(D.get11("size").toFloatOrNull() ?: 16f)
    }
    var LineHeight by remember {
        mutableStateOf(D.get11("LineHeight").toFloatOrNull() ?: 30f)
    }
    var count by remember {
        mutableStateOf(0)
    }
    Surface(color = MaterialTheme.colorScheme.primary) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 13.dp, top = 32.dp, bottom = 21.dp)
            ) {
                IconButton(onClick = {if(!flag){D.saveUseSharedPrefrance("size",size.toString());D.saveUseSharedPrefrance("LineHeight",LineHeight.toString())}else{}
                    navBarcontroller.navigate("A") {
                        popUpTo("A") {
                            inclusive = true;saveState = true
                        }
                    }
                }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.caret_left_fill),
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.padding(PaddingValues(end = 16.dp))
                    )
                }
                Text(
                    text = "${title}",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold, lineHeight = 24.sp
                    ))
                    if(!flag) {
                        size()
                        {
                            if(count==5){size=16f;count =0;LineHeight=30f} else{ size=(4f + size);LineHeight+=4;count++}
                        }
                        BackHandler {
                            D.saveUseSharedPrefrance("size",size.toString());D.saveUseSharedPrefrance("LineHeight",LineHeight.toString())
                            navBarcontroller.navigate("A")
                        }
                    }
            }
            if (flag) {
                Tabels()
            } else {
                TopNav(navBarcontroller, size, LineHeight)

            }
        }
    }
}


@Composable
fun Tabels() {
    val Scroll = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var flag by remember {
        mutableStateOf(true)
    }


    LazyColumn(state = Scroll) {
        if (flag) {
            coroutineScope.launch {
                Scroll.scrollToItem(D.day - 1) // or scrollToItem(8)
                flag = false
            }
        }
        itemsIndexed(D.DataSet, key = { index, item -> " ${index.hashCode()}${item.hashCode()}" })

        { index, item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 9.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${item.date.readable}",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold, lineHeight = 24.sp
                        ), color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(bottom = 11.dp)
                    )
                    getData(index, false)
                    Spacer(modifier = Modifier.padding(bottom = 6.dp))
                    getData(index, true)
                }
                Spacer(modifier = Modifier.padding(bottom = 29.dp))
            }
            Spacer(modifier = Modifier.padding(bottom = 15.dp))
        }
    }
}

@Composable
fun getData(index: Int, flag: Boolean) {
    val Time = listOf("FJR", "SUNR", "DHR", "ASR", "MARB", "Ish")
    var i = 0
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 19.dp, end = 19.dp), horizontalArrangement = Arrangement.Center
    ) {
        while (i < 6) {
            if (flag) {
                val time = D.GetPrayFromObjectTime(Value = D.Time[i], index)
                Text(
                    text = time!!,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.width(width = 45.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                    textAlign = TextAlign.Center
                )
            } else {
                Text(
                    text = Time[i],
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.width(width = 45.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.padding(end = 10.dp))
            i++
        }
    }
}