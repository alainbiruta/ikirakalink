package com.microcareer.match.presentation.debug;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.lifecycle.ViewModel;
import com.microcareer.match.BuildConfig;
import com.microcareer.match.data.local.seeder.DatabaseSeeder;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;
import com.microcareer.match.R;

/**
 * Debug menu for database operations.
 *
 * IMPORTANT: Only show this in DEBUG builds!
 * Add to Profile screen or create a hidden debug menu.
 *
 * Usage in ProfileScreen:
 * ```kotlin
 * if (BuildConfig.DEBUG) {
 *    DebugDatabaseMenu(viewModel = hiltViewModel())
 * }
 * ```
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\f\u001a\u00020\rJ\u0006\u0010\u000e\u001a\u00020\rJ\u0006\u0010\u000f\u001a\u00020\rR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0010"}, d2 = {"Lcom/microcareer/match/presentation/debug/DebugViewModel;", "Landroidx/lifecycle/ViewModel;", "databaseSeeder", "Lcom/microcareer/match/data/local/seeder/DatabaseSeeder;", "(Lcom/microcareer/match/data/local/seeder/DatabaseSeeder;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/microcareer/match/presentation/debug/DebugState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "clearMessage", "", "resetDatabase", "seedDatabase", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class DebugViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.data.local.seeder.DatabaseSeeder databaseSeeder = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.microcareer.match.presentation.debug.DebugState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.microcareer.match.presentation.debug.DebugState> state = null;
    
    @javax.inject.Inject()
    public DebugViewModel(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.seeder.DatabaseSeeder databaseSeeder) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.microcareer.match.presentation.debug.DebugState> getState() {
        return null;
    }
    
    public final void seedDatabase() {
    }
    
    public final void resetDatabase() {
    }
    
    public final void clearMessage() {
    }
}