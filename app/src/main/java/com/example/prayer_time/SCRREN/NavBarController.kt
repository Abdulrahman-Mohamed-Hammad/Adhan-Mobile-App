package com.example.prayer_time.SCRREN

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prayer_time.D


//
//@RequiresApi(Build.VERSION_CODES.TIRAMISU)


  @RequiresApi(Build.VERSION_CODES.TIRAMISU)
  @Composable
  fun NavBarcontroller() {
    var navBarcontroller = rememberNavController()
    NavHost(navController = navBarcontroller, startDestination = "0") {
      composable(route="0"){ splash(navBarcontroller)}
      composable(route = "A") { NAV_BAR(navBarcontroller) }
      composable(route = "B") { PrayerTimeForMonth(navBarcontroller,"Prayer Time For ${D.DataSet[D.month].date.gregorian.month.en}",true) }
      composable(route = "C") {PrayerTimeForMonth(navBarcontroller,"Athkar",false) }

    }
  }

