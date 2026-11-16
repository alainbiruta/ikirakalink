package com.microcareer.match.presentation.debug

import androidx.compose.foundation.layout.*
import androidx.compose.ui.res.stringResource
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microcareer.match.BuildConfig
import com.microcareer.match.data.local.seeder.DatabaseSeeder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.microcareer.match.R

/**
 * Debug menu for database operations.
 *
 * IMPORTANT: Only show this in DEBUG builds!
 * Add to Profile screen or create a hidden debug menu.
 *
 * Usage in ProfileScreen:
 * ```kotlin
 * if (BuildConfig.DEBUG) {
 *     DebugDatabaseMenu(viewModel = hiltViewModel())
 * }
 * ```
 */

/* ══════════════════════════════════════════════════════════
   DEBUG MENU VIEW MODEL
   ══════════════════════════════════════════════════════════ */

@HiltViewModel
class DebugViewModel @Inject constructor(
    private val databaseSeeder: DatabaseSeeder
) : ViewModel() {

    private val _state = MutableStateFlow(DebugState())
    val state: StateFlow<DebugState> = _state.asStateFlow()

    fun seedDatabase() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isSeeding = true,
                message = "Seeding database...",
                messageType = MessageType.INFO
            )

            try {
                databaseSeeder.seedDatabase()
                _state.value = _state.value.copy(
                    isSeeding = false,
                    message = "✅ Database seeded successfully!\n10 jobs and 5 learning modules added.",
                    messageType = MessageType.SUCCESS
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isSeeding = false,
                    message = "❌ Seeding failed: ${e.message}",
                    messageType = MessageType.ERROR
                )
            }
        }
    }

    fun resetDatabase() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isSeeding = true,
                message = "Resetting database...",
                messageType = MessageType.INFO
            )

            try {
                databaseSeeder.clearAndReseed()
                _state.value = _state.value.copy(
                    isSeeding = false,
                    message = "✅ Database reset complete!\nAll data cleared and reseeded.",
                    messageType = MessageType.SUCCESS
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isSeeding = false,
                    message = "❌ Reset failed: ${e.message}",
                    messageType = MessageType.ERROR
                )
            }
        }
    }

    fun clearMessage() {
        _state.value = _state.value.copy(message = null)
    }
}

data class DebugState(
    val isSeeding: Boolean = false,
    val message: String? = null,
    val messageType: MessageType = MessageType.INFO
)

enum class MessageType {
    INFO, SUCCESS, ERROR
}

/* ══════════════════════════════════════════════════════════
   DEBUG MENU UI
   ══════════════════════════════════════════════════════════ */

@Composable
fun DebugDatabaseMenu(
    viewModel: DebugViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()
    var showResetDialog by remember { mutableStateOf(false) }

    // Show message snackbar
    state.message?.let { message ->
        LaunchedEffect(message) {
            kotlinx.coroutines.delay(3000) // Show for 3 seconds
            viewModel.clearMessage()
        }
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.BugReport,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
                Text(
                    text = "🔧 Debug Tools",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Divider()

            // Seed Database Button
            Button(
                onClick = { viewModel.seedDatabase() },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isSeeding,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                if (state.isSeeding) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(Modifier.width(8.dp))
                }
                Icon(Icons.Default.DataObject, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text(if (state.isSeeding) "Seeding..." else "Seed Database")
            }

            // Reset Database Button
            OutlinedButton(
                onClick = { showResetDialog = true },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isSeeding,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Icon(Icons.Default.RestartAlt, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text(stringResource(R.string.reset_database))
            }

            // Status Message
            state.message?.let { message ->
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = when (state.messageType) {
                        MessageType.SUCCESS -> MaterialTheme.colorScheme.primaryContainer
                        MessageType.ERROR -> MaterialTheme.colorScheme.errorContainer
                        MessageType.INFO -> MaterialTheme.colorScheme.surfaceVariant
                    },
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = message,
                        modifier = Modifier.padding(12.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = when (state.messageType) {
                            MessageType.SUCCESS -> MaterialTheme.colorScheme.onPrimaryContainer
                            MessageType.ERROR -> MaterialTheme.colorScheme.onErrorContainer
                            MessageType.INFO -> MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                }
            }

            // Info text
            Text(
                text = "⚠️ Debug mode only. Not available in production.",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }

    // Reset Confirmation Dialog
    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            icon = {
                Icon(
                    Icons.Default.Warning,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            },
            title = { Text(stringResource(R.string.reset_database)) },
            text = {
                Text(stringResource(R.string.this_will_delete_all_jobs_and_learning_m))
            },
            confirmButton = {
                Button(
                    onClick = {
                        showResetDialog = false
                        viewModel.resetDatabase()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text(stringResource(R.string.reset))
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}