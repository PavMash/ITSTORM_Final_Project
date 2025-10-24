package com.itstorm.finalproject.shared.utils

import androidx.compose.ui.graphics.Color
import com.itstorm.finalproject.shared.ui.theme.CyanFF
import com.itstorm.finalproject.shared.ui.theme.Green51
import com.itstorm.finalproject.shared.ui.theme.PurpleFF
import com.itstorm.finalproject.shared.ui.theme.White
import com.itstorm.finalproject.shared.ui.theme.Yellow93
import kotlin.random.Random

fun randomColorFromPool(userId: Int): Color {
    val pool = listOf(White, Yellow93, Green51, PurpleFF, CyanFF)
    val randomColor = pool[userId % pool.size]

    return randomColor
}