package com.example.todoapp.ui.screens.list_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.AppTheme
import com.example.todoapp.data.models.Importance
import com.example.todoapp.data.models.ToDoItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun fab(modifier: Modifier) {
    FloatingActionButton(onClick = { /*TODO*/ }, modifier = modifier.padding(24.dp)) {
        Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add")
    }
}

val dummyItems = listOf(
    ToDoItem(
        id = "1",
        text = "Task 1",
        importance = Importance.HIGH,
        deadline = System.currentTimeMillis() + 86400000,
        done = false,
        color = "#FF0000",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "John Doe"
    ),
    ToDoItem(
        id = "2",
        text = "Task 2",
        importance = Importance.COMMON,
        deadline = System.currentTimeMillis() + 172800000,
        done = true,
        color = "#00FF00",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "Jane Smith"
    ),
    ToDoItem(
        id = "3",
        text = "Task 3",
        importance = Importance.LOW,
        deadline = System.currentTimeMillis() + 259200000,
        done = false,
        color = "#0000FF",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "Alice Johnson"
    ),
    ToDoItem(
        id = "4",
        text = "Task 4",
        importance = Importance.HIGH,
        deadline = System.currentTimeMillis() + 345600000,
        done = true,
        color = "#FF00FF",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "Bob Thompson"
    ),
    ToDoItem(
        id = "23",
        text = "Task 23",
        importance = Importance.LOW,
        deadline = System.currentTimeMillis() + 1728000000,
        done = false,
        color = "#FFFF00",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "Emma Wilson"
    ),
    ToDoItem(
        id = "24",
        text = "Task 24",
        importance = Importance.COMMON,
        deadline = System.currentTimeMillis() + 2592000000,
        done = true,
        color = "#00FFFF",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "Daniel Brown"
    ),
    ToDoItem(
        id = "1",
        text = "Task 1",
        importance = Importance.HIGH,
        deadline = System.currentTimeMillis() + 86400000,
        done = false,
        color = "#FF0000",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "John Doe"
    ),
    ToDoItem(
        id = "2",
        text = "Task 2",
        importance = Importance.COMMON,
        deadline = System.currentTimeMillis() + 172800000,
        done = true,
        color = "#00FF00",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "Jane Smith"
    ),
    ToDoItem(
        id = "3",
        text = "Task 3",
        importance = Importance.LOW,
        deadline = System.currentTimeMillis() + 259200000,
        done = false,
        color = "#0000FF",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "Alice Johnson"
    ),
    ToDoItem(
        id = "4",
        text = "Task 4",
        importance = Importance.HIGH,
        deadline = System.currentTimeMillis() + 345600000,
        done = true,
        color = "#FF00FF",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "Bob Thompson"
    ),
    ToDoItem(
        id = "23",
        text = "Task 23",
        importance = Importance.LOW,
        deadline = System.currentTimeMillis() + 1728000000,
        done = false,
        color = "#FFFF00",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "Emma Wilson"
    ),
    ToDoItem(
        id = "24",
        text = "Task 24",
        importance = Importance.COMMON,
        deadline = System.currentTimeMillis() + 2592000000,
        done = true,
        color = "#00FFFF",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "Daniel Brown"
    ),
    ToDoItem(
        id = "1",
        text = "Task 1",
        importance = Importance.HIGH,
        deadline = System.currentTimeMillis() + 86400000,
        done = false,
        color = "#FF0000",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "John Doe"
    ),
    ToDoItem(
        id = "2",
        text = "Task 2",
        importance = Importance.COMMON,
        deadline = System.currentTimeMillis() + 172800000,
        done = true,
        color = "#00FF00",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "Jane Smith"
    ),
    ToDoItem(
        id = "3",
        text = "Task 3",
        importance = Importance.LOW,
        deadline = System.currentTimeMillis() + 259200000,
        done = false,
        color = "#0000FF",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "Alice Johnson"
    ),
    ToDoItem(
        id = "4",
        text = "Task 4",
        importance = Importance.HIGH,
        deadline = System.currentTimeMillis() + 345600000,
        done = true,
        color = "#FF00FF",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "Bob Thompson"
    ),
    ToDoItem(
        id = "23",
        text = "Task 23",
        importance = Importance.LOW,
        deadline = System.currentTimeMillis() + 1728000000,
        done = false,
        color = "#FFFF00",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "Emma Wilson"
    ),
    ToDoItem(
        id = "24",
        text = "Task 24",
        importance = Importance.COMMON,
        deadline = System.currentTimeMillis() + 2592000000,
        done = true,
        color = "#00FFFF",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "Daniel Brown"
    ),
    ToDoItem(
        id = "1",
        text = "Task 1",
        importance = Importance.HIGH,
        deadline = System.currentTimeMillis() + 86400000,
        done = false,
        color = "#FF0000",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "John Doe"
    ),
    ToDoItem(
        id = "2",
        text = "Task 2",
        importance = Importance.COMMON,
        deadline = System.currentTimeMillis() + 172800000,
        done = true,
        color = "#00FF00",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "Jane Smith"
    ),
    ToDoItem(
        id = "3",
        text = "Task 3",
        importance = Importance.LOW,
        deadline = System.currentTimeMillis() + 259200000,
        done = false,
        color = "#0000FF",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "Alice Johnson"
    ),
    ToDoItem(
        id = "4",
        text = "Task 4",
        importance = Importance.HIGH,
        deadline = System.currentTimeMillis() + 345600000,
        done = true,
        color = "#FF00FF",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "Bob Thompson"
    ),
    ToDoItem(
        id = "23",
        text = "Task 23",
        importance = Importance.LOW,
        deadline = System.currentTimeMillis() + 1728000000,
        done = false,
        color = "#FFFF00",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "Emma Wilson"
    ),
    ToDoItem(
        id = "24",
        text = "Task 24",
        importance = Importance.COMMON,
        deadline = System.currentTimeMillis() + 2592000000,
        done = true,
        color = "#00FFFF",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "Daniel Brown"
    ),
    ToDoItem(
        id = "1",
        text = "Task 1",
        importance = Importance.HIGH,
        deadline = System.currentTimeMillis() + 86400000,
        done = false,
        color = "#FF0000",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "John Doe"
    ),
    ToDoItem(
        id = "2",
        text = "Task 2",
        importance = Importance.COMMON,
        deadline = System.currentTimeMillis() + 172800000,
        done = true,
        color = "#00FF00",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "Jane Smith"
    ),
    ToDoItem(
        id = "3",
        text = "Task 3",
        importance = Importance.LOW,
        deadline = System.currentTimeMillis() + 259200000,
        done = false,
        color = "#0000FF",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "Alice Johnson"
    ),
    ToDoItem(
        id = "4",
        text = "Task 4",
        importance = Importance.HIGH,
        deadline = System.currentTimeMillis() + 345600000,
        done = true,
        color = "#FF00FF",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "Bob Thompson"
    ),
    ToDoItem(
        id = "23",
        text = "Task 23",
        importance = Importance.LOW,
        deadline = System.currentTimeMillis() + 1728000000,
        done = false,
        color = "#FFFF00",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "Emma Wilson"
    ),
    ToDoItem(
        id = "24",
        text = "Task 24",
        importance = Importance.COMMON,
        deadline = System.currentTimeMillis() + 2592000000,
        done = true,
        color = "#00FFFF",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "Daniel Brown"
    ),
    ToDoItem(
        id = "1",
        text = "Task 1",
        importance = Importance.HIGH,
        deadline = System.currentTimeMillis() + 86400000,
        done = false,
        color = "#FF0000",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "John Doe"
    ),
    ToDoItem(
        id = "2",
        text = "Task 2",
        importance = Importance.COMMON,
        deadline = System.currentTimeMillis() + 172800000,
        done = true,
        color = "#00FF00",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "Jane Smith"
    ),
    ToDoItem(
        id = "3",
        text = "Task 3",
        importance = Importance.LOW,
        deadline = System.currentTimeMillis() + 259200000,
        done = false,
        color = "#0000FF",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "Alice Johnson"
    ),
    ToDoItem(
        id = "4",
        text = "Task 4",
        importance = Importance.HIGH,
        deadline = System.currentTimeMillis() + 345600000,
        done = true,
        color = "#FF00FF",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "Bob Thompson"
    ),
    ToDoItem(
        id = "23",
        text = "Task 23",
        importance = Importance.LOW,
        deadline = System.currentTimeMillis() + 1728000000,
        done = false,
        color = "#FFFF00",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "Emma Wilson"
    ),
    ToDoItem(
        id = "24",
        text = "Task 24",
        importance = Importance.COMMON,
        deadline = System.currentTimeMillis() + 2592000000,
        done = true,
        color = "#00FFFF",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "Daniel Brown"
    ),
    ToDoItem(
        id = "1",
        text = "Task 1",
        importance = Importance.HIGH,
        deadline = System.currentTimeMillis() + 86400000,
        done = false,
        color = "#FF0000",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "John Doe"
    ),
    ToDoItem(
        id = "2",
        text = "Task 2",
        importance = Importance.COMMON,
        deadline = System.currentTimeMillis() + 172800000,
        done = true,
        color = "#00FF00",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "Jane Smith"
    ),
    ToDoItem(
        id = "3",
        text = "Task 3",
        importance = Importance.LOW,
        deadline = System.currentTimeMillis() + 259200000,
        done = false,
        color = "#0000FF",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "Alice Johnson"
    ),
    ToDoItem(
        id = "4",
        text = "Task 4",
        importance = Importance.HIGH,
        deadline = System.currentTimeMillis() + 345600000,
        done = true,
        color = "#FF00FF",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "Bob Thompson"
    ),
    ToDoItem(
        id = "23",
        text = "Task 23",
        importance = Importance.LOW,
        deadline = System.currentTimeMillis() + 1728000000,
        done = false,
        color = "#FFFF00",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "Emma Wilson"
    ),
    ToDoItem(
        id = "24",
        text = "Task 24",
        importance = Importance.COMMON,
        deadline = System.currentTimeMillis() + 2592000000,
        done = true,
        color = "#00FFFF",
        createdAt = System.currentTimeMillis(),
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = "Daniel Brown"
    )
)

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Preview
@Composable
fun ListScreen() {
    AppTheme {
        val refreshScope = rememberCoroutineScope()
        var refreshing by remember { mutableStateOf(false) }
        fun refresh() = refreshScope.launch {
            refreshing = true
            delay(1000)
            refreshing = false
        }

        val pullRefreshState = rememberPullRefreshState(refreshing, ::refresh)
        Box(
            Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
        ) {

            LazyColumn {
                stickyHeader {
                    Header()
                }
                items(dummyItems) { item ->
                    Item(item,
                        onLongClick = {

                        })
                }
            }
            PullRefreshIndicator(
                refreshing = refreshing,
                state = pullRefreshState,
                Modifier.align(Alignment.TopCenter)
            )
            fab(modifier = Modifier.align(Alignment.BottomEnd))
        }
    }
}





