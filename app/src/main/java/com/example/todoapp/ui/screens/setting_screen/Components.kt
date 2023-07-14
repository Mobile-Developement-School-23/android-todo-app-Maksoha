package com.example.todoapp.ui.screens.setting_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.ui.model.Theme
import com.example.todoapp.ui.model.ToString


@Composable
fun ThemeItem(theme: Theme, selected: Boolean, onSelected: () -> Unit){
    Row(
        Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                onClick = onSelected,
                role = Role.RadioButton
            )
            .padding(16.dp, 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = theme.ToString(),
            style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground),
            modifier = Modifier.padding(16.dp)
        )
        RadioButton(
            selected = selected,
            onClick = null
        )
    }
}