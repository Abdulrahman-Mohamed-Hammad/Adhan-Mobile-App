package com.example.prayer_time.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.prayer_time.R

// Set of Material typography styles to start with

val notosans = FontFamily(Font(R.font.notosans_medium))

val English = Typography(
    bodyMedium = TextStyle(
        fontFamily = notosans
        , fontWeight = FontWeight.Medium
        , fontSize = 14.sp
        , lineHeight = 20.sp
        , textAlign = TextAlign.Center
              ,  letterSpacing = 0.em
    ),
    bodyLarge = TextStyle(
        fontFamily = notosans
        , fontWeight = FontWeight.SemiBold
        , fontSize = 36.sp
        , lineHeight = 44.sp
        , textAlign = TextAlign.Start
        , letterSpacing = (-0.02).em
    )

)

