package com.microcareer.match.presentation.employer;

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
import com.microcareer.match.R;
import androidx.compose.ui.layout.ContentScale;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000^\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\u001a,\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a\u0088\u0001\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b26\u0010\u0004\u001a2\u0012\u0013\u0012\u00110\r\u00a2\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0013\u0012\u00110\r\u00a2\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\u00010\f26\u0010\u0006\u001a2\u0012\u0013\u0012\u00110\r\u00a2\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0013\u0012\u00110\r\u00a2\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\u00010\fH\u0003\u001a:\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001b\u0010\u001c\u001a\u0018\u0010\u001d\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0003\u001aP\u0010\u001e\u001a\u00020\u00012\b\b\u0002\u0010\u001f\u001a\u00020 2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010#\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00010$2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a:\u0010&\u001a\u00020\u00012\u0006\u0010\'\u001a\u00020(2\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a\b\u0010,\u001a\u00020\u0001H\u0003\u001ab\u0010-\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\f\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010)\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00010$2\u0012\u0010*\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00010$2\u0012\u0010+\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00010$H\u0003\u001a\u0010\u0010/\u001a\u00020\u00012\u0006\u00100\u001a\u00020\rH\u0003\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u00061"}, d2 = {"ApplicantCard", "", "applicant", "Lcom/microcareer/match/presentation/employer/ApplicantUiModel;", "onAccept", "Lkotlin/Function0;", "onReject", "ApplicantsTab", "uiState", "Lcom/microcareer/match/presentation/employer/EmployerUiState;", "padding", "Landroidx/compose/foundation/layout/PaddingValues;", "Lkotlin/Function2;", "", "Lkotlin/ParameterName;", "name", "applicantId", "jobId", "DashboardStat", "modifier", "Landroidx/compose/ui/Modifier;", "label", "value", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "tint", "Landroidx/compose/ui/graphics/Color;", "DashboardStat-xwkQ0AY", "(Landroidx/compose/ui/Modifier;Ljava/lang/String;Ljava/lang/String;Landroidx/compose/ui/graphics/vector/ImageVector;J)V", "DashboardTab", "EmployerHomeScreen", "viewModel", "Lcom/microcareer/match/presentation/employer/EmployerHomeViewModel;", "onNavigateToPostJob", "onNavigateToProfile", "onEditJob", "Lkotlin/Function1;", "onLogout", "EmployerJobCard", "job", "Lcom/microcareer/match/data/local/entity/JobEntity;", "onEdit", "onDelete", "onToggleStatus", "EmptyJobsPlaceholder", "MyJobsTab", "onRefresh", "StatusBadge", "status", "app_debug"})
public final class EmployerHomeScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void EmployerHomeScreen(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.presentation.employer.EmployerHomeViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToPostJob, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToProfile, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onEditJob, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onLogout) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    private static final void MyJobsTab(com.microcareer.match.presentation.employer.EmployerUiState uiState, androidx.compose.foundation.layout.PaddingValues padding, kotlin.jvm.functions.Function0<kotlin.Unit> onRefresh, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onEdit, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onDelete, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onToggleStatus) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void EmptyJobsPlaceholder() {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void EmployerJobCard(com.microcareer.match.data.local.entity.JobEntity job, kotlin.jvm.functions.Function0<kotlin.Unit> onEdit, kotlin.jvm.functions.Function0<kotlin.Unit> onDelete, kotlin.jvm.functions.Function0<kotlin.Unit> onToggleStatus) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void StatusBadge(java.lang.String status) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ApplicantsTab(com.microcareer.match.presentation.employer.EmployerUiState uiState, androidx.compose.foundation.layout.PaddingValues padding, kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.String, kotlin.Unit> onAccept, kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.String, kotlin.Unit> onReject) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ApplicantCard(com.microcareer.match.presentation.employer.ApplicantUiModel applicant, kotlin.jvm.functions.Function0<kotlin.Unit> onAccept, kotlin.jvm.functions.Function0<kotlin.Unit> onReject) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void DashboardTab(com.microcareer.match.presentation.employer.EmployerUiState uiState, androidx.compose.foundation.layout.PaddingValues padding) {
    }
}