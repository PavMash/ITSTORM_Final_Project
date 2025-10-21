package com.itstorm.finalproject.root.splash

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.itstorm.finalproject.shared.components.LogoImage
import com.itstorm.finalproject.shared.components.LogoText
import com.itstorm.finalproject.shared.components.SecondaryText
import com.itstorm.finalproject.shared.ui.theme.Black
import com.itstorm.finalproject.R

@Composable
fun SplashUI(component: SplashComponent) {
    val state by component.model.collectAsState()

    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Black)
                .padding(vertical = 20.dp, horizontal = 32.dp),
        ) {

            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(40.dp)
            ) {
                val infiniteTransition = rememberInfiniteTransition()
                val rotate by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 360f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 1500, easing = LinearEasing)
                    )
                )

                LogoImage(
                    modifier = Modifier.size(48.dp)
                        .graphicsLayer { rotationZ = rotate }
                )

                LogoText()
            }

            SecondaryText(
                modifier = Modifier.align(Alignment.BottomCenter),
                text = stringResource(R.string.app_version)
            )
        }
    }
}