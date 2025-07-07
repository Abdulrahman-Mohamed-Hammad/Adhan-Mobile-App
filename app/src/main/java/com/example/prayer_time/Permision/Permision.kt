package com.example.prayer_time.Permision

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.widget.AutoCompleteTextView.Validator
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.prayer_time.NotifactionAndPermission.Notification
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("PermissionLaunchedDuringComposition")
    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun RPermision(permisionState: PermissionState): Boolean {
        if(!permisionState.status.isGranted)
        {
            LaunchedEffect(Unit) {
                    permisionState.launchPermissionRequest()
              //   Location =true
                }
            }
        return permisionState.status.isGranted
        }



