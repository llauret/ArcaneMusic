package com.example.arcanemusic.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.arcanemusic.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val earwigFactory = FontFamily(
    Font(R.font.earwigfactoryrg)
)


val fontsona = FontFamily(
    Font(R.font.fontsona),
    Font(R.font.fontsona1psp),
    Font(R.font.fontsona2innocentsin),
    Font(R.font.fontsona3fes),
    Font(R.font.fontsona4golden),
    Font(R.font.fontsona5jp),
    Font(R.font.fontsonaq)
)

val p5royal = FontFamily(
    Font(R.font.p5royal)
)