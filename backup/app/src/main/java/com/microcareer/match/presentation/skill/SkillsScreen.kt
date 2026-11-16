package com.microcareer.match.presentation.skill

import androidx.compose.foundation.layout.*
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.microcareer.match.R
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkillsScreen(
    viewModel  : SkillViewModel,
    onBack     : () -> Unit,          // ← ADDED: back button for easier nav
    onAddClick : () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.my_skills)) },
                navigationIcon = {
                    // ← ADDED: back arrow so the user can return from Skills → Home
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector        = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(
                    imageVector        = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_skill)
                )
            }
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                uiState.isLoading      -> LoadingState()
                uiState.skills.isEmpty() -> EmptySkillsState()
                else                   -> SkillsList(
                    skills   = uiState.skills,
                    onRemove = viewModel::removeSkill
                )
            }
        }
    }
}

@Composable
private fun LoadingState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun EmptySkillsState() {
    Box(
        modifier         = Modifier.fillMaxSize().padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text  = stringResource( R.string.no_skills_added_yet_type_a_skill_above_and_tap),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun SkillsList(skills: List<String>, onRemove: (String) -> Unit) {
    LazyColumn(
        modifier            = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding      = PaddingValues(vertical = 16.dp)
    ) {
        items(items = skills, key = { it }) { skill ->
            SkillItem(skill = skill, onRemove = { onRemove(skill) })
        }
    }
}

@Composable
private fun SkillItem(skill: String, onRemove: () -> Unit) {
    ElevatedCard(
        modifier  = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier              = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            Text(text = skill, style = MaterialTheme.typography.titleMedium)
            IconButton(onClick = onRemove) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = stringResource(R.string.delete_skill))
            }
        }
    }
}