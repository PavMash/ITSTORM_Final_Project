package com.itstorm.finalproject.shared.utils

import com.itstorm.core_domain.models.news.NewsCategory

fun categoryToUI(category: NewsCategory?) =
    when(category) {
        (NewsCategory.Technology) -> "Технологии"
        (NewsCategory.Culture) -> "Культура"
        (NewsCategory.Travel) -> "Путешествия"
        else -> "Всё"
    }