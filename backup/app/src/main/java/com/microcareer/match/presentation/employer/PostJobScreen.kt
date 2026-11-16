package com.microcareer.match.presentation.employer

import androidx.compose.foundation.layout.*
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.microcareer.match.R

// Add at top of any file showing red


// ─────────────────────────────────────────────────────────
// CATEGORIES — match your backend
// ─────────────────────────────────────────────────────────

private val JOB_CATEGORIES = listOf(
    "Construction", "Cleaning", "Delivery", "Agriculture",
    "Hospitality", "IT & Tech", "Teaching", "Healthcare",
    "Security", "Retail", "Transportation", "Manufacturing", "Other"
)

private val CURRENCIES = listOf("RWF", "USD", "KES", "UGX")

// ─────────────────────────────────────────────────────────
// SCREEN
// ─────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun PostJobScreen(
    viewModel : PostJobViewModel = hiltViewModel(),
    editJobId : String?          = null,
    onBack    : () -> Unit,
    onSuccess : () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    // Load job data if editing
    LaunchedEffect(editJobId) {
        editJobId?.let { viewModel.loadJob(it) }
    }

    // Navigate on success
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) onSuccess()
    }

    // ── Form state ────────────────────────────────────────
    var title            by remember { mutableStateOf(uiState.title) }
    var description      by remember { mutableStateOf(uiState.description) }
    var location         by remember { mutableStateOf(uiState.location) }
    var payment          by remember { mutableStateOf(uiState.payment) }
    var currency         by remember { mutableStateOf(uiState.currency) }
    var duration         by remember { mutableStateOf(uiState.duration) }
    var category         by remember { mutableStateOf(uiState.category) }
    var skillInput       by remember { mutableStateOf("") }
    var skills           by remember { mutableStateOf(uiState.skills) }
    // ── New fields ────────────────────────────────────────
    var isUrgent         by remember { mutableStateOf(uiState.isUrgent) }
    var requiredHours    by remember { mutableStateOf(uiState.requiredHours) }
    var maxApplicants    by remember { mutableStateOf(uiState.maxApplicants) }
    var deadline         by remember { mutableStateOf(uiState.deadline) }
    // ── Dropdowns ─────────────────────────────────────────
    var categoryExpanded by remember { mutableStateOf(false) }
    var currencyExpanded by remember { mutableStateOf(false) }

    // Sync pre-filled values when editing
    LaunchedEffect(uiState.title) {
        if (editJobId != null) {
            title         = uiState.title
            description   = uiState.description
            location      = uiState.location
            payment       = uiState.payment
            currency      = uiState.currency
            duration      = uiState.duration
            category      = uiState.category
            skills        = uiState.skills
            isUrgent      = uiState.isUrgent
            requiredHours = uiState.requiredHours
            maxApplicants = uiState.maxApplicants
            deadline      = uiState.deadline
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (editJobId != null) "Edit Job" else "Post a Job") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(Modifier.height(4.dp))

            // ══════════════════════════════════════════════
            // JOB DETAILS
            // ══════════════════════════════════════════════

            SectionLabel("Job Details")

            OutlinedTextField(
                value         = title,
                onValueChange = { title = it },
                label         = { Text(stringResource(R.string.job_title)) },
                leadingIcon   = { Icon(Icons.Default.Work, null) },
                singleLine    = true,
                modifier      = Modifier.fillMaxWidth(),
                placeholder   = { Text(stringResource(R.string.e_g_experienced_electrician_needed)) }
            )

            OutlinedTextField(
                value         = description,
                onValueChange = { description = it },
                label         = { Text(stringResource(R.string.description)) },
                leadingIcon   = { Icon(Icons.Default.Description, null) },
                minLines      = 3,
                maxLines      = 6,
                modifier      = Modifier.fillMaxWidth(),
                placeholder   = { Text(stringResource(R.string.describe_the_job)) }
            )

            // Category dropdown
            ExposedDropdownMenuBox(
                expanded         = categoryExpanded,
                onExpandedChange = { categoryExpanded = it }
            ) {
                OutlinedTextField(
                    value         = category.ifBlank { "" },
                    onValueChange = {},
                    readOnly      = true,
                    label         = { Text(stringResource(R.string.category)) },
                    leadingIcon   = { Icon(Icons.Default.Category, null) },
                    trailingIcon  = { ExposedDropdownMenuDefaults.TrailingIcon(categoryExpanded) },
                    modifier      = Modifier.menuAnchor().fillMaxWidth(),
                    placeholder   = { Text(stringResource(R.string.select_category)) }
                )
                ExposedDropdownMenu(
                    expanded         = categoryExpanded,
                    onDismissRequest = { categoryExpanded = false }
                ) {
                    JOB_CATEGORIES.forEach { cat ->
                        DropdownMenuItem(
                            text    = { Text(cat) },
                            onClick = { category = cat; categoryExpanded = false },
                            leadingIcon = if (cat == category) {
                                { Icon(Icons.Default.Check, null) }
                            } else null
                        )
                    }
                }
            }

            // ── Urgent toggle ─────────────────────────────
            // Maps to backend "is_urgent": Boolean
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier              = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment     = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment     = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            Icons.Default.PriorityHigh,
                            null,
                            tint = if (isUrgent) MaterialTheme.colorScheme.error
                            else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Column {
                            Text(
                                stringResource(R.string.urgent_hiring),
                                style      = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                stringResource(R.string.urgent_hiring_desc),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    Switch(
                        checked         = isUrgent,
                        onCheckedChange = { isUrgent = it }
                    )
                }
            }

            HorizontalDivider()

            // ══════════════════════════════════════════════
            // LOCATION & DURATION
            // ══════════════════════════════════════════════

            SectionLabel("Location & Duration")

            OutlinedTextField(
                value         = location,
                onValueChange = { location = it },
                label         = { Text(stringResource(R.string.location_required)) },
                leadingIcon   = { Icon(Icons.Default.LocationOn, null) },
                singleLine    = true,
                modifier      = Modifier.fillMaxWidth(),
                placeholder   = { Text(stringResource(R.string.e_g_kigali_gasabo)) }
            )

            // Duration + Required hours on same row
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // FIX: duration is now String — backend key "duration", type String
                OutlinedTextField(
                    value         = duration,
                    onValueChange = { duration = it },
                    label         = { Text(stringResource(R.string.duration_days)) },
                    leadingIcon   = { Icon(Icons.Default.Schedule, null) },
                    singleLine    = true,
                    modifier      = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    placeholder   = { Text(stringResource(R.string.e_g_3)) }
                )

                // Maps to backend "required_hours": Int
                OutlinedTextField(
                    value         = requiredHours,
                    onValueChange = { requiredHours = it },
                    label         = { Text(stringResource(R.string.hours_day)) },
                    leadingIcon   = { Icon(Icons.Default.HourglassBottom, null) },
                    singleLine    = true,
                    modifier      = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    placeholder   = { Text(stringResource(R.string.e_g_8)) },
                    supportingText = { Text(stringResource(R.string.optional)) }
                )
            }

            // Deadline — maps to backend "deadline": ISO-8601 string
            OutlinedTextField(
                value         = deadline,
                onValueChange = { deadline = it },
                label         = { Text(stringResource(R.string.application_deadline)) },
                leadingIcon   = { Icon(Icons.Default.DateRange, null) },
                singleLine    = true,
                modifier      = Modifier.fillMaxWidth(),
                placeholder   = { Text(stringResource(R.string.date_format_hint)) },
                supportingText = { Text(stringResource(R.string.optional_no_deadline)) }
            )

            HorizontalDivider()

            // ══════════════════════════════════════════════
            // PAYMENT
            // ══════════════════════════════════════════════

            SectionLabel("Payment")

            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value           = payment,
                    onValueChange   = { payment = it },
                    label           = { Text(stringResource(R.string.amount)) },
                    leadingIcon     = { Icon(Icons.Default.Payments, null) },
                    singleLine      = true,
                    modifier        = Modifier.weight(2f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )

                ExposedDropdownMenuBox(
                    expanded         = currencyExpanded,
                    onExpandedChange = { currencyExpanded = it },
                    modifier         = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value         = currency,
                        onValueChange = {},
                        readOnly      = true,
                        label         = { Text(stringResource(R.string.currency)) },
                        trailingIcon  = { ExposedDropdownMenuDefaults.TrailingIcon(currencyExpanded) },
                        modifier      = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded         = currencyExpanded,
                        onDismissRequest = { currencyExpanded = false }
                    ) {
                        CURRENCIES.forEach { cur ->
                            DropdownMenuItem(
                                text    = { Text(cur) },
                                onClick = { currency = cur; currencyExpanded = false }
                            )
                        }
                    }
                }
            }

            HorizontalDivider()

            // ══════════════════════════════════════════════
            // APPLICANTS LIMIT
            // ══════════════════════════════════════════════

            SectionLabel("Applicants")

            // Maps to backend "max_applicants": Int (0 = unlimited)
            OutlinedTextField(
                value           = maxApplicants,
                onValueChange   = { maxApplicants = it },
                label           = { Text(stringResource(R.string.max_applicants)) },
                leadingIcon     = { Icon(Icons.Default.Group, null) },
                singleLine      = true,
                modifier        = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                placeholder     = { Text("0") },
                supportingText  = { Text(stringResource(R.string.optional_unlimited)) }
            )

            HorizontalDivider()

            // ══════════════════════════════════════════════
            // REQUIRED SKILLS
            // ══════════════════════════════════════════════

            SectionLabel("Required Skills")

            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment     = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value         = skillInput,
                    onValueChange = { skillInput = it },
                    label         = { Text(stringResource(R.string.add_a_skill)) },
                    singleLine    = true,
                    modifier      = Modifier.weight(1f),
                    placeholder   = { Text(stringResource(R.string.e_g_plumbing_welding)) }
                )
                IconButton(
                    onClick = {
                        val s = skillInput.trim()
                        if (s.isNotBlank() && !skills.contains(s)) {
                            skills = skills + s
                        }
                        skillInput = ""
                    },
                    enabled = skillInput.isNotBlank()
                ) {
                    Icon(
                        Icons.Default.Add,
                        "Add skill",
                        tint = if (skillInput.isNotBlank()) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.outline
                    )
                }
            }

            if (skills.isNotEmpty()) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement   = Arrangement.spacedBy(6.dp)
                ) {
                    skills.forEach { skill ->
                        InputChip(
                            selected     = false,
                            onClick      = {},
                            label        = { Text(skill) },
                            trailingIcon = {
                                IconButton(
                                    onClick  = { skills = skills - skill },
                                    modifier = Modifier.size(18.dp)
                                ) {
                                    Icon(
                                        Icons.Default.Close,
                                        "Remove skill",
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                        )
                    }
                }
            }

            // ── Error card ────────────────────────────────
            uiState.error?.let { err ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Row(
                        modifier          = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Error,
                            null,
                            tint     = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            err,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            Spacer(Modifier.height(4.dp))

            // ── Submit button ─────────────────────────────
            Button(
                onClick  = {
                    // Build deadline string — only send if the employer filled it
                    val deadlineToSend = deadline.trim().ifBlank { null }?.let {
                        // Append time component if employer only typed a date
                        if (it.length == 10) "${it}T00:00:00.000Z" else it
                    }

                    viewModel.submitJob(
                        title         = title.trim(),
                        description   = description.trim(),
                        location      = location.trim(),
                        payment       = payment.toDoubleOrNull() ?: 0.0,
                        currency      = currency,
                        // FIX: pass as String — was toLongOrNull()
                        duration      = duration.trim().ifBlank { "1" },
                        category      = category.trim(),
                        skills        = skills,
                        isUrgent      = isUrgent,
                        requiredHours = requiredHours.toIntOrNull() ?: 0,
                        maxApplicants = maxApplicants.toIntOrNull() ?: 0,
                        deadline      = deadlineToSend,
                        editJobId     = editJobId
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                enabled  = !uiState.isLoading &&
                        title.isNotBlank() &&
                        description.isNotBlank() &&
                        location.isNotBlank() &&
                        payment.isNotBlank() &&
                        category.isNotBlank()
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color    = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Icon(
                        if (editJobId != null) Icons.Default.Edit
                        else Icons.Default.PublishedWithChanges,
                        null,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(if (editJobId != null) "Save Changes" else "Post Job")
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text       = text,
        style      = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.SemiBold,
        color      = MaterialTheme.colorScheme.primary
    )
}