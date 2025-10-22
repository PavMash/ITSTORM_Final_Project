package com.itstorm.finalproject.shared.utils

import androidx.compose.ui.graphics.Color

fun fillOrExtendColorList(colors: List<Color>, numOfUsers: Int): List<Color> =
    when {
        colors.isEmpty() -> fillListWithRandomColors(numOfUsers)

        colors.size < numOfUsers ->
            colors + fillListWithRandomColors(numOfUsers - colors.size)

        else -> colors
    }