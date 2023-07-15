package com.example.compose
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val md_theme_light_primary = Color(0xFF00639C)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFFCEE5FF)
val md_theme_light_onPrimaryContainer = Color(0xFF001D33)
val md_theme_light_secondary = Color(0xFF006B56)
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_secondaryContainer = Color(0xFF7EF8D4)
val md_theme_light_onSecondaryContainer = Color(0xFF002018)
val md_theme_light_tertiary = Color(0xFF68587A)
val md_theme_light_onTertiary = Color(0xFFFFFFFF)
val md_theme_light_tertiaryContainer = Color(0xFFEFDBFF)
val md_theme_light_onTertiaryContainer = Color(0xFF231533)
val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_errorContainer = Color(0xFFFFDAD6)
val md_theme_light_onError = Color(0xFFFFFFFF)
val md_theme_light_onErrorContainer = Color(0xFF410002)
val md_theme_light_background = Color(0xFFFCFCFF)
val md_theme_light_onBackground = Color(0xFF1A1C1E)
val md_theme_light_surface = Color(0xFFFCFCFF)
val md_theme_light_onSurface = Color(0xFF1A1C1E)
val md_theme_light_surfaceVariant = Color(0xFFDEE3EB)
val md_theme_light_onSurfaceVariant = Color(0xFF42474E)
val md_theme_light_outline = Color(0xFF72777F)
val md_theme_light_inverseOnSurface = Color(0xFFF1F0F4)
val md_theme_light_inverseSurface = Color(0xFF2F3033)
val md_theme_light_inversePrimary = Color(0xFF97CBFF)
val md_theme_light_shadow = Color(0xFF000000)
val md_theme_light_surfaceTint = Color(0xFF00639C)
val md_theme_light_outlineVariant = Color(0xFFC2C7CF)
val md_theme_light_scrim = Color(0xFF000000)

val md_theme_dark_primary = Color(0xFF97CBFF)
val md_theme_dark_onPrimary = Color(0xFF003354)
val md_theme_dark_primaryContainer = Color(0xFF004A77)
val md_theme_dark_onPrimaryContainer = Color(0xFFCEE5FF)
val md_theme_dark_secondary = Color(0xFF60DBB9)
val md_theme_dark_onSecondary = Color(0xFF00382B)
val md_theme_dark_secondaryContainer = Color(0xFF005140)
val md_theme_dark_onSecondaryContainer = Color(0xFF7EF8D4)
val md_theme_dark_tertiary = Color(0xFFD3BFE6)
val md_theme_dark_onTertiary = Color(0xFF392A49)
val md_theme_dark_tertiaryContainer = Color(0xFF504061)
val md_theme_dark_onTertiaryContainer = Color(0xFFEFDBFF)
val md_theme_dark_error = Color(0xFFFFB4AB)
val md_theme_dark_errorContainer = Color(0xFF93000A)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
val md_theme_dark_background = Color(0xFF1A1C1E)
val md_theme_dark_onBackground = Color(0xFFE2E2E5)
val md_theme_dark_surface = Color(0xFF1A1C1E)
val md_theme_dark_onSurface = Color(0xFFE2E2E5)
val md_theme_dark_surfaceVariant = Color(0xFF42474E)
val md_theme_dark_onSurfaceVariant = Color(0xFFC2C7CF)
val md_theme_dark_outline = Color(0xFF8C9199)
val md_theme_dark_inverseOnSurface = Color(0xFF1A1C1E)
val md_theme_dark_inverseSurface = Color(0xFFE2E2E5)
val md_theme_dark_inversePrimary = Color(0xFF00639C)
val md_theme_dark_shadow = Color(0xFF000000)
val md_theme_dark_surfaceTint = Color(0xFF97CBFF)
val md_theme_dark_outlineVariant = Color(0xFF42474E)
val md_theme_dark_scrim = Color(0xFF000000)


val seed = Color(0xFF00639C)


const val BLOCK_WIDTH = 200
const val BLOCK_HEIGHT = 90
const val PREVIEW_WIDTH = BLOCK_WIDTH * 4
const val PREVIEW_HEIGHT = BLOCK_HEIGHT * 6

@Composable
@Preview(widthDp = PREVIEW_WIDTH, heightDp = PREVIEW_HEIGHT)
fun LightThemePreview(){
    AppTheme(useDarkTheme = false) {
        Column {
            Row {
                ThemeBlock("Primary", MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.onPrimary)
                ThemeBlock("On Primary", MaterialTheme.colorScheme.onPrimary, MaterialTheme.colorScheme.primary)
                ThemeBlock("Primary Container", MaterialTheme.colorScheme.primaryContainer, MaterialTheme.colorScheme.onPrimaryContainer)
                ThemeBlock("On Primary Container", MaterialTheme.colorScheme.onPrimaryContainer, MaterialTheme.colorScheme.primaryContainer)
            }
            Row {
                ThemeBlock("Secondary", MaterialTheme.colorScheme.secondary, MaterialTheme.colorScheme.onSecondary)
                ThemeBlock("On Secondary", MaterialTheme.colorScheme.onSecondary, MaterialTheme.colorScheme.secondary)
                ThemeBlock("Secondary Container", MaterialTheme.colorScheme.secondaryContainer, MaterialTheme.colorScheme.onSecondaryContainer)
                ThemeBlock("On Secondary Container", MaterialTheme.colorScheme.onSecondaryContainer, MaterialTheme.colorScheme.secondaryContainer)
            }
            Row {
                ThemeBlock("Tertiary", MaterialTheme.colorScheme.tertiary, MaterialTheme.colorScheme.onTertiary)
                ThemeBlock("On Tertiary", MaterialTheme.colorScheme.onTertiary, MaterialTheme.colorScheme.tertiary)
                ThemeBlock("Tertiary Container", MaterialTheme.colorScheme.tertiaryContainer, MaterialTheme.colorScheme.onTertiaryContainer)
                ThemeBlock("On Tertiary Container", MaterialTheme.colorScheme.onTertiaryContainer, MaterialTheme.colorScheme.tertiaryContainer)
            }
            Row {
                ThemeBlock("Error", MaterialTheme.colorScheme.error, MaterialTheme.colorScheme.onError)
                ThemeBlock("On Error", MaterialTheme.colorScheme.onError, MaterialTheme.colorScheme.error)
                ThemeBlock("Error Container", MaterialTheme.colorScheme.errorContainer, MaterialTheme.colorScheme.onErrorContainer)
                ThemeBlock("On Error Container", MaterialTheme.colorScheme.onErrorContainer, MaterialTheme.colorScheme.errorContainer)
            }
            Row {
                ThemeBlock("Background", MaterialTheme.colorScheme.background, MaterialTheme.colorScheme.onBackground)
                ThemeBlock("On Background", MaterialTheme.colorScheme.onBackground, MaterialTheme.colorScheme.background)
                ThemeBlock("Surface", MaterialTheme.colorScheme.surface, MaterialTheme.colorScheme.onSurface)
                ThemeBlock("On Surface", MaterialTheme.colorScheme.onSurface, MaterialTheme.colorScheme.surface)
            }
            Row {
                ThemeBlock("Outline", MaterialTheme.colorScheme.outline, MaterialTheme.colorScheme.surface)
                ThemeBlock("Surface-Variant", MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.colorScheme.onSurfaceVariant)
                ThemeBlock("On Surface-Variant", MaterialTheme.colorScheme.onSurfaceVariant, MaterialTheme.colorScheme.surfaceVariant)
            }
        }
    }
}

@Composable
@Preview(widthDp = PREVIEW_WIDTH)
fun DarkThemePreview(){
    AppTheme(useDarkTheme = true) {
        Column {
            Row {
                ThemeBlock("Primary", MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.onPrimary)
                ThemeBlock("On Primary", MaterialTheme.colorScheme.onPrimary, MaterialTheme.colorScheme.primary)
                ThemeBlock("Primary Container", MaterialTheme.colorScheme.primaryContainer, MaterialTheme.colorScheme.onPrimaryContainer)
                ThemeBlock("On Primary Container", MaterialTheme.colorScheme.onPrimaryContainer, MaterialTheme.colorScheme.primaryContainer)
            }
            Row {
                ThemeBlock("Secondary", MaterialTheme.colorScheme.secondary, MaterialTheme.colorScheme.onSecondary)
                ThemeBlock("On Secondary", MaterialTheme.colorScheme.onSecondary, MaterialTheme.colorScheme.secondary)
                ThemeBlock("Secondary Container", MaterialTheme.colorScheme.secondaryContainer, MaterialTheme.colorScheme.onSecondaryContainer)
                ThemeBlock("On Secondary Container", MaterialTheme.colorScheme.onSecondaryContainer, MaterialTheme.colorScheme.secondaryContainer)
            }
            Row {
                ThemeBlock("Tertiary", MaterialTheme.colorScheme.tertiary, MaterialTheme.colorScheme.onTertiary)
                ThemeBlock("On Tertiary", MaterialTheme.colorScheme.onTertiary, MaterialTheme.colorScheme.tertiary)
                ThemeBlock("Tertiary Container", MaterialTheme.colorScheme.tertiaryContainer, MaterialTheme.colorScheme.onTertiaryContainer)
                ThemeBlock("On Tertiary Container", MaterialTheme.colorScheme.onTertiaryContainer, MaterialTheme.colorScheme.tertiaryContainer)
            }
            Row {
                ThemeBlock("Error", MaterialTheme.colorScheme.error, MaterialTheme.colorScheme.onError)
                ThemeBlock("On Error", MaterialTheme.colorScheme.onError, MaterialTheme.colorScheme.error)
                ThemeBlock("Error Container", MaterialTheme.colorScheme.errorContainer, MaterialTheme.colorScheme.onErrorContainer)
                ThemeBlock("On Error Container", MaterialTheme.colorScheme.onErrorContainer, MaterialTheme.colorScheme.errorContainer)
            }
            Row {
                ThemeBlock("Background", MaterialTheme.colorScheme.background, MaterialTheme.colorScheme.onBackground)
                ThemeBlock("On Background", MaterialTheme.colorScheme.onBackground, MaterialTheme.colorScheme.background)
                ThemeBlock("Surface", MaterialTheme.colorScheme.surface, MaterialTheme.colorScheme.onSurface)
                ThemeBlock("On Surface", MaterialTheme.colorScheme.onSurface, MaterialTheme.colorScheme.surface)
            }
            Row {
                ThemeBlock("Outline", MaterialTheme.colorScheme.outline, MaterialTheme.colorScheme.surface)
                ThemeBlock("Surface-Variant", MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.colorScheme.onSurfaceVariant)
                ThemeBlock("On Surface-Variant", MaterialTheme.colorScheme.onSurfaceVariant, MaterialTheme.colorScheme.surfaceVariant)
            }
        }
    }
}

@Composable
fun ThemeBlock(
    text: String = "Default Text",
    color: Color = Color.White,
    textColor: Color = Color.Black,
){
    Box(
        Modifier
            .width(BLOCK_WIDTH.dp)
            .height(BLOCK_HEIGHT.dp)
            .padding(3.dp)
            .background(color = color)
    ){
        Text(
            text = text,
            color = textColor,
            modifier = Modifier.padding(10.dp)
        )
    }
}