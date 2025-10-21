package com.itstorm.finalproject.root.splash

import kotlinx.coroutines.flow.StateFlow

interface SplashComponent {
    val model: StateFlow<SplashState>

    data class SplashState(val isLoading: Boolean)
}