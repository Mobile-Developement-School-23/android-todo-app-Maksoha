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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentManager
import com.example.todoapp.R
import com.example.todoapp.data.models.Importance
import com.example.todoapp.ui.model.TaskEditAction
import com.example.todoapp.ui.model.TaskEditUiState
import com.example.todoapp.utils.toStringDate


@Composable
private fun TaskEditTopAppBar(
    onAction: (TaskEditAction) -> Unit,
    fragmentManager: FragmentManager
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = {
            fragmentManager.popBackStack()
        }) {
            Icon(imageVector = Icons.Rounded.Close, contentDescription = "Close")
        }
        Button(onClick = {
            onAction(TaskEditAction.SaveTask)
            fragmentManager.popBackStack()
        }) {
            Text(text = stringResource(id = R.string.save))
        }
    }
}

@Composable
private fun TaskEditTextField(description: String, onAction: (TaskEditAction) -> Unit) {
    ElevatedCard(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        BasicTextField(
            value = description,
            onValueChange = { onAction(TaskEditAction.UpdateDescription(it)) },
            Modifier
                .fillMaxWidth()
                .padding(24.dp),
            textStyle = MaterialTheme.typography.bodyLarge,
            decorationBox = { innerTextField ->
                Box(contentAlignment = Alignment.CenterStart) {
                    if (description.isEmpty()) {
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
    importance: Importance,
    onAction: (TaskEditAction) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    var selectedImportance by remember { mutableStateOf(importance) }
    Text(
        text = stringResource(id = R.string.importance),
        Modifier.padding(16.dp, 8.dp),
        style = MaterialTheme.typography.titleMedium
    )
    TaskEditBottomSheet(
        selectedImportance = selectedImportance,
        onImportanceSelected = {
            selectedImportance = it
        },
        onAction,
        content
    )
}

@Composable
private fun TaskEditDateField(uiState: TaskEditUiState, onAction: (TaskEditAction) -> Unit) {
    val showDialog = remember { mutableStateOf(false) }
    val switchChecked by remember(uiState) {
        derivedStateOf { uiState.deadline != null }
    }
    Row(
        modifier = Modifier
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
                text = uiState.deadline?.toStringDate() ?: "",
                Modifier.padding(top = 6.dp),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Switcher(showDialog, switchChecked, onAction)
        Calendar(showDialog, onAction)
    }


}

@Composable
fun TaskEditScreen(taskEditViewModel: TaskEditViewModel, fragmentManager: FragmentManager) {
    val uiState by taskEditViewModel.uiState.collectAsState()
    Column {
        TaskEditTopAppBar(taskEditViewModel::onAction, fragmentManager)
        TaskEditTextField(uiState.description, taskEditViewModel::onAction)
        TaskEditImportanceField(uiState.importance, taskEditViewModel::onAction, content = {
            Column {
                Divider(Modifier.padding(16.dp, 16.dp, 16.dp, 12.dp))
                TaskEditDateField(uiState, taskEditViewModel::onAction)
                Divider(Modifier.padding(16.dp, 16.dp, 16.dp, 12.dp))
                ButtonDelete(uiState, taskEditViewModel::onAction)
            }
        }
        )
    }

}