package ru.tanexc.server.presentation.screen.logs

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.tanexc.server.domain.model.SwipeLog
import ru.tanexc.server.presentation.ui.theme.Typography

@Composable
fun LogsCard(
    modifier: Modifier,
    log: SwipeLog,
    onDelete: () -> Unit,
) {
    val expanded = remember { mutableStateOf(false) }
    Card(
        onClick = { if (!expanded.value) expanded.value = true },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
    ) {
        AnimatedContent(expanded.value, label = "") { target ->
            if (target) {
                ExpandedContent(log, onDelete = onDelete, onCollapse = { expanded.value = false })
            } else {
                CollapsedContent(log, onExpand = { expanded.value = true })
            }
        }
    }
}

@Composable
fun ExpandedContent(
    log: SwipeLog,
    onCollapse: () -> Unit,
    onDelete: () -> Unit,
) {
    Column(
        Modifier.padding(16.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        ) {
            Text("${log.id}", style = Typography.bodyLarge)
            Spacer(Modifier.size(16.dp))
            Text(log.info, Modifier.fillMaxWidth().weight(1f))
            Spacer(Modifier.size(16.dp))
            IconButton(onDelete) {
                Icon(Icons.Default.Delete, null)
            }
            IconButton(onCollapse) {
                Icon(Icons.Default.KeyboardArrowUp, null)
            }
        }
        Row {
            Text("client:", fontWeight = FontWeight.ExtraBold, modifier = Modifier.width(84.dp))
            Text(log.client, modifier = Modifier.fillMaxWidth(), maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        Row {
            Text("dx:", fontWeight = FontWeight.ExtraBold, modifier = Modifier.width(84.dp))
            Text("${log.dx}")
        }
        Row {
            Text("dy:", fontWeight = FontWeight.ExtraBold, modifier = Modifier.width(84.dp))
            Text("${log.dy}")
        }
        Row {
            Text("duration:", fontWeight = FontWeight.ExtraBold, modifier = Modifier.width(84.dp))
            Text("${log.duration} ms")
        }
        Spacer(Modifier.size(16.dp))
    }
}

@Composable
fun CollapsedContent(
    log: SwipeLog,
    onExpand: () -> Unit,
) {
    Row(
        Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().weight(1f),
        ) {
            Text("${log.id}", style = Typography.bodyLarge)
            Spacer(Modifier.size(16.dp))
            Text(log.client)
        }
        Spacer(Modifier.size(16.dp))
        IconButton(onExpand) {
            Icon(Icons.Default.KeyboardArrowDown, null)
        }
    }
}
