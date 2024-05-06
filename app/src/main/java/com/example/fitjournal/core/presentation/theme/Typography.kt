package com.example.fitjournal.core.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.fitjournal.R

object Inter {
    val bold: FontFamily =
        FontFamily(
            Font(R.font.inter_bold, FontWeight.Bold)
        )
    val regular: FontFamily =
        FontFamily(
            Font(R.font.inter_regular, FontWeight.Normal)
        )
}

val typography = Typography(
    headlineLarge = TextStyle(
        fontSize = 28.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
        fontFamily = Inter.bold
    ),
    headlineMedium = TextStyle(
        fontSize = 26.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
        fontFamily = Inter.bold
    ),
    headlineSmall = TextStyle(
        fontSize = 22.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.sp,
        fontFamily = Inter.regular
    ),
    titleLarge = TextStyle(
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        fontFamily = Inter.bold
    ),
    titleMedium = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
        fontFamily = Inter.bold
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
        fontFamily = Inter.regular
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        lineHeight = 12.sp,
        letterSpacing = 0.sp,
        fontFamily = Inter.regular
    ),
    labelSmall = TextStyle(
        fontSize = 10.sp,
        lineHeight = 12.sp,
        letterSpacing = 0.sp,
        fontFamily = Inter.regular
    )
)
