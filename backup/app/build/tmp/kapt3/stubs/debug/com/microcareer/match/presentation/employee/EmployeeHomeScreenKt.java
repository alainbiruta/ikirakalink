package com.microcareer.match.presentation.employee;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextOverflow;
import com.microcareer.match.data.local.entity.JobEntity;
import com.microcareer.match.presentation.home.HomeViewModel;
import com.microcareer.match.presentation.home.HomeUiEvent;
import com.microcareer.match.R;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000Z\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\u001al\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\f2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00060\f2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00060\f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00060\fH\u0007\u001aD\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00060\f2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00060\f2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00060\fH\u0003\u001a\u0094\u0001\u0010\u0019\u001a\u00020\u00062\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00130\u00012\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00130\u00012\u0006\u0010\u001c\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\u00152\u0006\u0010\u001f\u001a\u00020 2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00060\f2\u0012\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\n2\u0018\u0010\u0017\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00060#2\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\nH\u0003\u001a\u0010\u0010$\u001a\u00020\u00062\u0006\u0010%\u001a\u00020&H\u0003\u001a\u0010\u0010\'\u001a\u00020\u00062\u0006\u0010(\u001a\u00020)H\u0003\u001a$\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00130\u00012\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00130\u00012\u0006\u0010+\u001a\u00020\u0002H\u0002\"\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006,"}, d2 = {"CATEGORIES", "", "", "MIN_MATCH_SCORE", "", "EmployeeHomeScreen", "", "viewModel", "Lcom/microcareer/match/presentation/home/HomeViewModel;", "onNavigateToJobDetail", "Lkotlin/Function1;", "onNavigateToProfile", "Lkotlin/Function0;", "onNavigateToSkills", "onNavigateToLearn", "onNavigateToSaved", "onLogout", "EmployeeJobCard", "job", "Lcom/microcareer/match/data/local/entity/JobEntity;", "isHighlighted", "", "onClick", "onSave", "onApply", "EmployeeJobFeed", "jobs", "recommended", "isLoading", "isRefreshing", "isOffline", "padding", "Landroidx/compose/foundation/layout/PaddingValues;", "onRefresh", "onJobClick", "Lkotlin/Function2;", "NotificationCard", "notif", "Lcom/microcareer/match/data/local/FcmNotification;", "SyncPendingCard", "pendingCount", "", "filteredJobs", "query", "app_debug"})
public final class EmployeeHomeScreenKt {
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.String> CATEGORIES = null;
    private static final float MIN_MATCH_SCORE = 0.2F;
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void EmployeeHomeScreen(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.presentation.home.HomeViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigateToJobDetail, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToProfile, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToSkills, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToLearn, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToSaved, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onLogout) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void NotificationCard(com.microcareer.match.data.local.FcmNotification notif) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SyncPendingCard(int pendingCount) {
    }
    
    private static final java.util.List<com.microcareer.match.data.local.entity.JobEntity> filteredJobs(java.util.List<com.microcareer.match.data.local.entity.JobEntity> jobs, java.lang.String query) {
        return null;
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    private static final void EmployeeJobFeed(java.util.List<com.microcareer.match.data.local.entity.JobEntity> jobs, java.util.List<com.microcareer.match.data.local.entity.JobEntity> recommended, boolean isLoading, boolean isRefreshing, boolean isOffline, androidx.compose.foundation.layout.PaddingValues padding, kotlin.jvm.functions.Function0<kotlin.Unit> onRefresh, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onJobClick, kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.Boolean, kotlin.Unit> onSave, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onApply) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.foundation.layout.ExperimentalLayoutApi.class})
    @androidx.compose.runtime.Composable()
    private static final void EmployeeJobCard(com.microcareer.match.data.local.entity.JobEntity job, boolean isHighlighted, kotlin.jvm.functions.Function0<kotlin.Unit> onClick, kotlin.jvm.functions.Function0<kotlin.Unit> onSave, kotlin.jvm.functions.Function0<kotlin.Unit> onApply) {
    }
}