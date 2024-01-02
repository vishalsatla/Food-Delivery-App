package uk.ac.tees.mad.w9617154.foodapp.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun rememberWindowInfo(): WindowInfo {
    val configuration = LocalConfiguration.current
    return WindowInfo(
        width = when {
            configuration.screenWidthDp < 600 -> WindowType.COMPACT
            configuration.screenWidthDp < 840 -> WindowType.MEDIUM
            else -> WindowType.EXPANDED
        },
        height = when {
            configuration.screenHeightDp < 800 -> WindowType.COMPACT
            configuration.screenHeightDp < 1200 -> WindowType.MEDIUM
            else -> WindowType.EXPANDED
        },
    )
}

data class WindowInfo(
    val width: WindowType,
    val height: WindowType
)

enum class WindowType {
    COMPACT,
    MEDIUM,
    EXPANDED
}