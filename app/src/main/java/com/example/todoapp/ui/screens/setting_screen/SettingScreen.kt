package com.example.todoapp.ui.screens.setting_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.compose.AppTheme
import com.example.todoapp.R
import com.example.todoapp.ui.model.SettingAction
import com.example.todoapp.ui.model.Theme
import com.example.todoapp.ui.model.applyTheme


@Composable
fun SettingScreenTopAppBar(onAction: (SettingAction) -> Unit) {
    Row(
        verticalAlignment = CenterVertically, modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        IconButton(onClick = { onAction(SettingAction.Navigate) }) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        Text(
            text = stringResource(id = R.string.theme),
            style = MaterialTheme.typography.headlineMedium.copy(color = MaterialTheme.colorScheme.onBackground),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }

}


@Composable
fun SettingScreenThemeField(theme: Theme, onAction: (SettingAction) -> Unit) {
    val items = listOf(Theme.Light, Theme.Dark, Theme.System)
    val selectedOption by remember(theme) {
        derivedStateOf { theme }
    }

    Column(Modifier.selectableGroup()) {
        items.forEach { theme ->
            ThemeItem(
                theme = theme,
                selected = (theme == selectedOption),
                onSelected = {onAction(SettingAction.UpdateTheme(theme))
                }
            )
        }
    }
    LaunchedEffect(selectedOption) {
        applyTheme(selectedOption)
    }
}


@Composable
fun SettingScreen(viewModel: SettingViewModel) {
    val state by viewModel.state.collectAsState()
    AppTheme {
        Column {
            SettingScreenTopAppBar(viewModel::onAction)
            SettingScreenThemeField(state, viewModel::onAction)
        }
    }
}