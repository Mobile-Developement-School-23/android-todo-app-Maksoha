package com.example.todoapp.ui.screens.taskEdit_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.data.models.Importance
import com.example.todoapp.data.models.convertToString
import com.example.todoapp.ui.model.TaskEditAction
import com.example.todoapp.ui.model.TaskEditUiState
import kotlinx.coroutines.launch


@Composable
fun ImportanceItem(
    importance: Importance,
    onClick: () -> Unit
) {
    Text(
        text = importance.convertToString(),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditBottomSheet(
    selectedImportance: Importance,
    onImportanceSelected: (Importance) -> Unit,
    onAction: (TaskEditAction) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val importanceItems = listOf(Importance.LOW, Importance.COMMON, Importance.HIGH)

    LaunchedEffect(scaffoldState.bottomSheetState) {
        if (scaffoldState.bottomSheetState.isVisible) {
            scaffoldState.bottomSheetState.show()
        } else {
            scaffoldState.bottomSheetState.hide()
        }
    }


    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            Column(Modifier.fillMaxWidth()) {
                importanceItems.forEach { item ->
                    ImportanceItem(
                        importance = item,
                        onClick = {
                            onImportanceSelected(item)
                            scope.launch {
                                scaffoldState.bottomSheetState.show()
                            }
                            onAction(TaskEditAction.UpdateImportance(item))
                        },
                    )
                    Divider()
                }
                Spacer(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )
            }
        },
    )
    { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            Text(
                text = selectedImportance.convertToString(),
                Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { scope.launch { scaffoldState.bottomSheetState.expand() } }
                    .padding(16.dp, 0.dp),
                style = MaterialTheme.typography.bodyMedium
            )
            content(innerPadding)
        }
    }
}


@Composable
fun Switcher(
    showDialog: MutableState<Boolean>,
    switchChecked: Boolean,
    onAction: (TaskEditAction) -> Unit,
) {
    Switch(
        checked = switchChecked,
        onCheckedChange = {
            showDialog.value = it; if (!it) onAction(TaskEditAction.UpdateDeadline(null))
            if (switchChecked) {
                onAction(TaskEditAction.UpdateDeadline(null))
            }
        },
        modifier = Modifier.padding(16.dp, 0.dp)
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Calendar(
    showDialog: MutableState<Boolean>,
    onAction: (TaskEditAction) -> Unit
) {
    if (showDialog.value) {
        val datePickerState = rememberDatePickerState()
        val confirmEnabled by remember(datePickerState.selectedDateMillis) {
            derivedStateOf { datePickerState.selectedDateMillis != null }
        }
        DatePickerDialog(
            onDismissRequest = {
                showDialog.value = false
                onAction(TaskEditAction.UpdateDeadline(null))
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            onAction(TaskEditAction.UpdateDeadline(it))
                        }
                        showDialog.value = false
                    },
                    enabled = confirmEnabled
                ) {
                    Text(text = stringResource(id = R.string.ok))
                }

            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog.value = false
                        onAction(TaskEditAction.UpdateDeadline(null))
                    }
                ) {
                    Text(stringResource(id = R.string.cancel))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}


@Composable
fun ButtonDelete(uiState: TaskEditUiState, onAction: (TaskEditAction) -> Unit) {
    Button(
        onClick = { onAction(TaskEditAction.DeleteTask)
            onAction(TaskEditAction.Navigate)},
        Modifier
            .padding(16.dp)
            .width(192.dp)
            .height(48.dp),
        enabled = uiState.isEditing
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = "Delete",
                modifier = Modifier.padding(end = 36.dp)
            )
            Text(text = stringResource(id = R.string.delete))
        }
    }
}