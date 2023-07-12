package com.example.todoapp.ui.screens.list_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.compose.AppTheme
import com.example.todoapp.R

@Composable
fun VisibilityButton() {
    val isChecked = remember { mutableStateOf(true) }
    FilledIconToggleButton(checked = isChecked.value, onCheckedChange = { isChecked.value = it }) {
        val imageVector =
            painterResource(if (isChecked.value) R.drawable.outline_visibility_24 else R.drawable.outline_visibility_off_24)
        Icon(painter = imageVector, contentDescription = "Visibility")
    }
}

@Composable
fun Title() {
    Text(
        text = stringResource(id = R.string.my_tasks),
        style = MaterialTheme.typography.headlineLarge,
        color = MaterialTheme.colorScheme.onPrimaryContainer
    )
}


@Composable
fun ProgressBar(progress: Float, max: Float) {
    Box(contentAlignment = Alignment.Center) {
        LinearProgressIndicator(
            progress = progress / max,
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
                .clip(RoundedCornerShape(8.dp)),
        )
    }
}


@Composable
fun Header() {
    AppTheme {
        Surface {
            Column(Modifier.padding(24.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp, 0.dp, 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Title()
                    VisibilityButton()
                }
                ProgressBar(progress = 1F, max = 7F)
            }
        }

    }

}