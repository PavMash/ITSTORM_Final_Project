package com.itstorm.finalproject.root.splash

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.itstorm.finalproject.shared.ui.theme.FinalProjectTheme
import kotlinx.coroutines.flow.MutableStateFlow

@Preview(
    showSystemUi = true,
    showBackground = true,
    name = "Splash Screen Preview"
)
@Composable
fun SplashUIPreview() {
    FinalProjectTheme {
        val fakeComponent = object : SplashComponent {
            override val model = MutableStateFlow(
                SplashComponent.SplashState(isLoading = true)
            )
        }

        SplashUI(component = fakeComponent)
    }
}
