package com.example.prayer_time.SCRREN

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.prayer_time.D
import com.example.prayer_time.R
import com.example.prayer_time.fetchLocation
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.time.LocalDateTime
import android.util.Log
import com.example.prayer_time.Permision.RPermision

@SuppressLint("SuspiciousIndentation", "CoroutineCreationDuringComposition",
    "UnrememberedMutableState"
)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun splash(navBarcontroller: NavHostController) {

    var Location = rememberPermissionState(permission = Manifest.permission.ACCESS_COARSE_LOCATION)
    val Notfication = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
    var flag by remember { mutableStateOf(false) }
    var check by remember { mutableStateOf(0) }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.primary)
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.splashscreen),
            contentDescription = "",
            modifier = Modifier.padding(bottom = 19.dp, top = 270.dp),
            tint = Color.Unspecified
        )

        Text(
            text = "اللهم صلي وسلم على نبينا محمد",
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 24.sp, textAlign = TextAlign.Right,
                color = MaterialTheme.colorScheme.onSurface, fontFamily = FontFamily(
                    Font(R.font.reemkufi_bold)
                )
            )
        )
    }
    var datee1 = LocalDateTime.now()
    if (RPermision(Location)) {
        if (Location.status.isGranted) {
            if (!D.F.FileIsExist(D.mainActivity, "Prayer.json"))
            fetchLocation(D.mainActivity, datee1) {
                flag = true
            }
        } else {
            D.api.country = D.get11("Country")
            D.api.city = D.get11("City")
            D.getsaveUseSharedPrefrance()
            D.get(D.latitude, D.longitude, D.mainActivity)
        }
        if (!D.flag.value) {
            navBarcontroller.navigate("A")
            RPermision(Notfication)

        }

    }
}







