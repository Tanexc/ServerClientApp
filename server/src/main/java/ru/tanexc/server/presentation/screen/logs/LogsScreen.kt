package ru.tanexc.server.presentation.screen.logs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogsScreen(modifier: Modifier) {
    val viewModel: LogsViewModel = koinViewModel()
    Column {
        TopAppBar(
            title = {},
            actions = { IconButton(onClick = {viewModel.deleteAll()}, Icon(Icons.Outlined.Delete, null)}
        )
        LazyColumn(modifier, contentPadding = PaddingValues(16.dp, 0.dp)) {
            items(viewModel.data) { log ->
                LogsCard(modifier = Modifier.padding(0.dp, 4.dp), log, onDelete = {viewModel.deleteById(log.id)})
            }
            item {
                LaunchedEffect(true) {
                    viewModel.getNextPage()
                }
            }
        }
    }

}