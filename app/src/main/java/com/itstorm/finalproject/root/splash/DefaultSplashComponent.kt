package com.itstorm.finalproject.root.splash

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.itstorm.finalproject.root.splash.SplashComponent.SplashState
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DefaultSplashComponent(
    private val onFinished: () -> Unit,
    private val preloadAppResources: () -> Unit,
    componentContext:  ComponentContext
): SplashComponent, ComponentContext by componentContext {

    private val _model = MutableStateFlow(SplashState(isLoading = true))
    override val model: StateFlow<SplashState> = _model

    private val scope = coroutineScope()

    init {
        scope.launch {
            val resourceLoadingJob = async { preloadAppResources() }
            val delayJob = async { delay(2000) }

            resourceLoadingJob.await()
            delayJob.await()

            _model.value = SplashState(isLoading = false)
            onFinished()
        }
    }
}