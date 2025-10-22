package com.itstorm.finalproject.shared.utils

import androidx.compose.ui.graphics.Color

fun fillListWithRandomColors(size: Int): List<Color> {
    val colors = mutableListOf<Color>()
    for (i in 1..size) {
        colors.add(randomColorFromPool())
    }
    return colors
}