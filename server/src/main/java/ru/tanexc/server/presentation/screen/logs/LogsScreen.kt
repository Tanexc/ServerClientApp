package ru.tanexc.server.presentation.screen.logs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel

@Composable
fun LogsScreen(modifier: Modifier) {
    val viewModel: LogsViewModel = koinViewModel()
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