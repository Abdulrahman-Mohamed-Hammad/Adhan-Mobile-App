package com.example.prayer_time

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.BeyondBoundsLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.ResolvedTextDirection

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.view.WindowCompat
import com.example.prayer_time.API.GetData


import com.example.prayer_time.SCRREN.NavBarcontroller


import com.example.prayer_time.ui.theme.PRAYER_TIMETheme
import com.google.android.gms.location.LocationServices

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


var D = GetData()

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("WeekBasedYear")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.Transparent.toArgb()
        window.navigationBarColor = Color.Transparent.toArgb()
//        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
//        var datee = LocalDateTime.now().format(formatter).toString()

        var datee1 = LocalDateTime.now()
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        var flag by mutableStateOf(false)
        datee1.format(formatter)
        D.set(this)
        D.DateSetter(datee1)
        val fixedDensity = Density(density = resources.displayMetrics.density, fontScale = 1f)
        setContent {
            CompositionLocalProvider(LocalDensity provides fixedDensity,  LocalLayoutDirection provides LayoutDirection.Ltr) {
                PRAYER_TIMETheme {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize(), color = Color.White
                    ) {

                        NavBarcontroller()
                    }
                }
            }
        }
    }

}
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingPermission")
    fun fetchLocation(
        mainActivity: Context,
        datee1: LocalDateTime,
        function: (Boolean) -> Unit
    ) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(mainActivity)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                var gecoder = Geocoder(mainActivity, Locale.getDefault())
                var addresses = gecoder.getFromLocation(location.latitude, location.longitude, 1)

//                var city = addresses!![0]
//                var city1 = addresses!![0].adminArea.toString().substringBefore(" ")

                D.get(location.latitude.toString(), location.longitude.toString(), mainActivity)


                D.api.country = addresses!![0].countryName
                D.api.city = addresses!![0].adminArea.toString().substringBefore(" ")
                D.saveUseSharedPrefrance("Country",D.api.country)
                D.saveUseSharedPrefrance("City", D.api.city)
                function(true)
            }

//                Log.d("AAAAAAAAAAAAAAAAAAAAAAA", city1)
//                Log.d("bbbbbbbbbbbbbbbbbbbbbb", location.latitude.toString())
//                Log.d("bbbbbbbbbbbbbbbbbbbbbb", location.longitude.toString())
//                Log.d("bbbbbbbbbbbbbbbbbbbbbb", city.countryCode.toString())
//                Log.d("bbbbbbbbbbbbbbbbbbbbbb", city1)
//                Log.d("bbbbbbbbbbbbbbbbbbbbbb", addresses[0].longitude.toString())

    }




