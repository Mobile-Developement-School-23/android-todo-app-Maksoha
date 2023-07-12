@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.todoapp.ui.screens.taskEdit_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.data.models.Importance
import java.time.LocalDate


@Composable
private fun TaskEditTopAppBar() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Rounded.Close, contentDescription = "Close")
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = stringResource(id = R.string.save))
        }
    }
}

@Composable
private fun TaskEditTextField() {
    ElevatedCard(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val text = remember { mutableStateOf("") }
        BasicTextField(
            value = text.value,
            onValueChange = { text.value = it },
            Modifier
                .fillMaxWidth()
                .padding(24.dp),
            textStyle = MaterialTheme.typography.bodyLarge,
            decorationBox = { innerTextField ->
                Box(contentAlignment = Alignment.CenterStart) {
                    if (text.value.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.task_hint),
                            style = MaterialTheme.typography.bodyLarge.copy(Color.Gray)
                        )
                    }
                }
                innerTextField()
            },
            minLines = 3
        )
    }
}


@Composable
private fun TaskEditImportanceField(
    content: @Composable (PaddingValues) -> Unit
) {
    var selectedImportance by remember { mutableStateOf(Importance.COMMON) }
    Text(
        text = stringResource(id = R.string.importance),
        Modifier
            .fillMaxWidth()
            .padding(16.dp, 16.dp, 16.dp, 0.dp),
        style = MaterialTheme.typography.titleMedium
    )
    TaskEditBottomSheet(
        selectedImportance = selectedImportance,
        onImportanceSelected = { importance ->
            selectedImportance = importance
        },
        content
    )
}

@Composable
private fun TaskEditDateField() {
    val showDialog = remember { mutableStateOf(false) }
    val selectedDate = remember { mutableStateOf<LocalDate?>(null) }
    val switchChecked = remember { mutableStateOf(false) }
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp, 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.deadline),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = selectedDate.value?.toString() ?: "",
                Modifier.padding(top = 6.dp),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Switcher(showDialog, switchChecked)
        Calendar(showDialog, selectedDate)
    }


}





@Preview
@Composable
fun TaskEditScreen(/*taskEditViewModel : TaskEditViewModel = viewModel()*/) {
    Column() {
        TaskEditTopAppBar()
        TaskEditTextField()
        TaskEditImportanceField {
            Column {
                Divider(Modifier.padding(16.dp, 16.dp, 16.dp, 12.dp))
                TaskEditDateField()
                Divider(Modifier.padding(16.dp, 16.dp, 16.dp, 12.dp))
                ButtonDelete()
            }
        }
    }

}