package ru.tanexc.server.presentation.screen.logs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.TextSnippet
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.SettingsAccessibility
import androidx.compose.material.icons.outlined.TextSnippet
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.tanexc.server.presentation.ui.theme.colorScheme

@Composable
fun LogsScreen(modifier: Modifier) {
    val viewModel: LogsViewModel = koinViewModel()
    val showDialog = remember { mutableStateOf(false) }
    Box(modifier.fillMaxSize()) {
        if (viewModel.data.isNotEmpty()) {
            LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp, 0.dp)) {
                items(viewModel.data) { log ->
                    LogsCard(
                        modifier = Modifier.padding(0.dp, 4.dp),
                        log,
                        onDelete = { viewModel.deleteById(log.id) },
                    )
                }
                item {
                    LaunchedEffect(true) {
                        viewModel.getNextPage()
                    }
                    Spacer(Modifier.size(16.dp))
                }
            }
            FloatingActionButton(
                modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
                onClick = { showDialog.value = true },
                content = {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(Icons.Outlined.Delete, null)
                        Text("Delete all")
                    }
                },
            )
        } else {
            Box(
                Modifier
                    .align(Alignment.Center)
                    .background(colorScheme.secondary.copy(0.12f), RoundedCornerShape(16.dp)),
            ) {
                Column(
                    Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        Icons.AutoMirrored.Outlined.TextSnippet,
                        null,
                        modifier = Modifier.size(56.dp),
                    )
                    Text("Logs will be displayed here")
                }
            }
        }
    }
    AnimatedVisibility(showDialog.value) {
        AlertDialog(
            icon = {
                Icon(Icons.Outlined.SettingsAccessibility, contentDescription = null)
            },
            title = {
                Text(
                    "Delete logs",
                    textAlign = TextAlign.Center,
                )
            },
            text = {
                Text("Are you sure you want to delete all logs?", textAlign = TextAlign.Center)
            },
            onDismissRequest = {
                showDialog.value = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteAll()
                        showDialog.value = false
                    },
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog.value = false
                    },
                ) {
                    Text("Cancel")
                }
            },
        )
    }
}
