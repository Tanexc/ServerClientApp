package ru.tanexc.server.presentation.screen.logs

import android.text.style.IconMarginSpan
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.tanexc.server.domain.model.SwipeLog
import ru.tanexc.server.presentation.ui.theme.Typography

@Composable
fun LogsCard(
    log: SwipeLog,
    onDelete: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(22.dp)
    ) {
        Row(Modifier.padding(22.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Row {
                Text("#${log.id}", style = Typography.labelMedium)
                Spacer(Modifier.size(16.dp))
                Text(log.client)
                Spacer(Modifier.size(16.dp))
                Text(log.info, overflow = TextOverflow.Ellipsis)
            }
            Spacer(Modifier.size(16.dp))
            IconButton(onDelete) {
                Icon(Icons.Default.Delete, null)
            }
        }

    }
}