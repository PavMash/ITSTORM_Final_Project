package com.itstorm.core_domain.usecases

import android.content.Context

class AssetFileReader(private val context: Context) {

    fun readAssetFile(fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }
}