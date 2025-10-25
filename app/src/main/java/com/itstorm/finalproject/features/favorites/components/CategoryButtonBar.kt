package com.itstorm.finalproject.features.favorites.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.itstorm.core_domain.models.news.NewsCategory
import com.itstorm.finalproject.shared.utils.categoryToUI

@Composable
fun CategoryButtonBar(
    modifier: Modifier = Modifier,
    onFilterClick: (NewsCategory?) -> Unit,
    currentFilter: NewsCategory?
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(space = 6.dp,
            alignment = Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val filterList = listOf(null) + NewsCategory.entries
        items(filterList) { item ->

            val onClick = { onFilterClick(item) }

            CategoryButton(
                text = categoryToUI(item),
                onClick = onClick,
                enabled = (currentFilter != item)
            )
        }
    }
}