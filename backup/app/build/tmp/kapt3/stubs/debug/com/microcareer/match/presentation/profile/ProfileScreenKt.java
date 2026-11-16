package com.microcareer.match.presentation.profile;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.ContentScale;
import androidx.compose.ui.text.font.FontWeight;
import com.google.firebase.auth.FirebaseAuth;
import com.microcareer.match.R;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000T\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a&\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003\u001a\b\u0010\b\u001a\u00020\u0001H\u0003\u001aT\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00032\u0018\u0010\u000b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\r0\f2\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u0012H\u0003\u001a\u0010\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0005H\u0003\u001a\u0016\u0010\u0014\u001a\u00020\u00012\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003\u001a$\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u000f2\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u0012H\u0003\u001aV\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u001c\u001a\u00020\u001d2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u00122\u0012\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u0012H\u0003\u001af\u0010\u001f\u001a\u00020\u00012\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001a\u001a\u00020\u001b2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u00122\u0012\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u00122\b\b\u0002\u0010!\u001a\u00020\"H\u0007\u001a\u0010\u0010#\u001a\u00020\u00012\u0006\u0010$\u001a\u00020\u0003H\u0003\u001a\u0016\u0010%\u001a\u00020\u00012\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00030\fH\u0003\u001a \u0010\'\u001a\u00020\u00012\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u00032\u0006\u0010+\u001a\u00020\u0003H\u0003\u001a\u0010\u0010,\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u001bH\u0003\u001a\u0018\u0010-\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0003\u00a8\u0006."}, d2 = {"ErrorContent", "", "error", "", "padding", "Landroidx/compose/foundation/layout/PaddingValues;", "onDismiss", "Lkotlin/Function0;", "FirebaseTokenButton", "LanguageSelector", "selectedLanguage", "languages", "", "Lkotlin/Pair;", "expanded", "", "onExpandedChange", "onLanguageSelected", "Lkotlin/Function1;", "LoadingContent", "LogoutButton", "onLogout", "NotificationToggle", "checked", "onCheckedChange", "ProfileContent", "uiState", "Lcom/microcareer/match/presentation/profile/ProfileUiState;", "viewModel", "Lcom/microcareer/match/presentation/profile/ProfileViewModel;", "onNotificationsChanged", "ProfileScreen", "onBack", "modifier", "Landroidx/compose/ui/Modifier;", "SectionTitle", "text", "SkillsSection", "skills", "StatItem", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "value", "label", "StatsCard", "UserHeader", "app_debug"})
public final class ProfileScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void ProfileScreen(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.presentation.profile.ProfileViewModel viewModel, @org.jetbrains.annotations.NotNull()
    com.microcareer.match.presentation.profile.ProfileUiState uiState, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onLogout, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onLanguageSelected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onNotificationsChanged, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void LoadingContent(androidx.compose.foundation.layout.PaddingValues padding) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ErrorContent(java.lang.String error, androidx.compose.foundation.layout.PaddingValues padding, kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    private static final void ProfileContent(com.microcareer.match.presentation.profile.ProfileUiState uiState, androidx.compose.foundation.layout.PaddingValues padding, com.microcareer.match.presentation.profile.ProfileViewModel viewModel, kotlin.jvm.functions.Function0<kotlin.Unit> onLogout, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onLanguageSelected, kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onNotificationsChanged) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void UserHeader(com.microcareer.match.presentation.profile.ProfileUiState uiState, com.microcareer.match.presentation.profile.ProfileViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void StatsCard(com.microcareer.match.presentation.profile.ProfileUiState uiState) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void StatItem(androidx.compose.ui.graphics.vector.ImageVector icon, java.lang.String value, java.lang.String label) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    private static final void LanguageSelector(java.lang.String selectedLanguage, java.util.List<kotlin.Pair<java.lang.String, java.lang.String>> languages, boolean expanded, kotlin.jvm.functions.Function0<kotlin.Unit> onExpandedChange, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onLanguageSelected) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void NotificationToggle(boolean checked, kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onCheckedChange) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void FirebaseTokenButton() {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void LogoutButton(kotlin.jvm.functions.Function0<kotlin.Unit> onLogout) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SectionTitle(java.lang.String text) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SkillsSection(java.util.List<java.lang.String> skills) {
    }
}