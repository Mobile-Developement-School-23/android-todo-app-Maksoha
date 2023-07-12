package com.example.todoapp.ui.screens.list_screen


import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.todoapp.data.models.ToDoItem


@Composable
fun Checkbox(modifier: Modifier, done: Boolean) {
    val isChecked = remember { mutableStateOf(done) }
    Checkbox(
        checked = isChecked.value, onCheckedChange = { isChecked.value = it },
        modifier = modifier, colors = CheckboxDefaults.colors(checkedColor = Color.Green)
    )
}

@Composable
fun TextItem(modifier: Modifier, text: String) {
    Text(
        text = text, modifier = modifier, overflow = TextOverflow.Ellipsis, maxLines = 3,
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
fun InfoBtn(modifier: Modifier) {
    IconButton(onClick = { /*TODO*/ }, modifier = modifier) {
        Icon(imageVector = Icons.Outlined.Info, contentDescription = "Info")
    }
}


@Composable
fun Item(item: ToDoItem, onLongClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .padding(8.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        onLongClick()
                    }
                )
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Checkbox(modifier = Modifier.weight(1f), done = item.done)
            TextItem(
                modifier = Modifier
                    .weight(5f)
                    .align(Alignment.CenterVertically),
                text = item.text
            )
            InfoBtn(modifier = Modifier.weight(1f))
        }
    }
}
