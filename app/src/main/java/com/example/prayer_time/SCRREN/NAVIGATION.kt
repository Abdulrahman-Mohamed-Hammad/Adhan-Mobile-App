package com.example.prayer_time.SCRREN

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.prayer_time.D
import com.example.prayer_time.R




@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun NAV_BAR(navBarcontroller: NavHostController) {
    var x = MaterialTheme.colorScheme.onBackground
    var y  = MaterialTheme.colorScheme.onSurface

    var Change by remember { mutableStateOf(x) }
    var change1 by remember { mutableStateOf(y) }
    var change2 by remember { mutableStateOf(x) }
    var change3 by remember { mutableStateOf(x) }
var pager = rememberPagerState (initialPage = 0, pageCount = {2})
    Scaffold(contentWindowInsets = WindowInsets(left = 0.dp, bottom = 0.dp, top = 0.dp, right = 0.dp),bottomBar = {
        BottomAppBar(containerColor = MaterialTheme.colorScheme.background, actions = {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = { change1 = y; Change = x;change2 =
                        x;change3 = x
                    },
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(100.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.frame_4),
                        contentDescription = "",
                        tint = change1
                    )

                }


                IconButton(
                    onClick = { change2 = y; Change = x;change1 =  x;change3 =x; D.api.NextDayM = D.day -1;navBarcontroller.navigate("C")
                    },
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(75.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.frame_5),
                        contentDescription = "",
                        tint = change2
                    )
                }
            }
        })
BackHandler {
    (D.mainActivity as? Activity)?.finish()
}
    }) { innerPadding ->

        HorizontalPager(state = pager) {pager->

            Box(
                Modifier
                    .padding((innerPadding))
                    .background(MaterialTheme.colorScheme.primary)
                    .fillMaxSize(),
            ) {
                when(pager){
                    0->{Prayer(navBarcontroller)}
                    1->{  D.api.NextDayM = D.day -1;navBarcontroller.navigate("C"){popUpTo("A")}}
                }

            }
        }
    }
}


