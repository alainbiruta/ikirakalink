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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2 = {"Lcom/microcareer/match/presentation/debug/MessageType;", "", "(Ljava/lang/String;I)V", "INFO", "SUCCESS", "ERROR", "app_debug"})
public enum MessageType {
    /*public static final*/ INFO /* = new INFO() */,
    /*public static final*/ SUCCESS /* = new SUCCESS() */,
    /*public static final*/ ERROR /* = new ERROR() */;
    
    MessageType() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.microcareer.match.presentation.debug.MessageType> getEntries() {
        return null;
    }
}