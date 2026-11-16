package com.microcareer.match.presentation.home;

import androidx.lifecycle.ViewModel;
import android.util.Log;
import com.microcareer.match.data.local.entity.JobEntity;
import com.microcareer.match.data.repository.AppResult;
import com.microcareer.match.data.repository.JobRepository;
import com.microcareer.match.utils.NetworkMonitor;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;
import com.microcareer.match.data.local.FcmNotification;
import com.microcareer.match.data.local.FcmNotificationStore;
import kotlinx.coroutines.flow.SharingStarted;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b#\b\u0086\b\u0018\u00002\u00020\u0001B\u0099\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\r\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u000f\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u000f\u00a2\u0006\u0002\u0010\u0013J\t\u0010!\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\"\u001a\u00020\rH\u00c6\u0003J\u000b\u0010#\u001a\u0004\u0018\u00010\u000fH\u00c6\u0003J\t\u0010$\u001a\u00020\u000fH\u00c6\u0003J\t\u0010%\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u00c6\u0003J\u000f\u0010\'\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u00c6\u0003J\u000f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u00c6\u0003J\u000f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u00c6\u0003J\t\u0010*\u001a\u00020\u0003H\u00c6\u0003J\t\u0010+\u001a\u00020\rH\u00c6\u0003J\u000b\u0010,\u001a\u0004\u0018\u00010\u000fH\u00c6\u0003J\u009d\u0001\u0010-\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\r2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u000f2\b\b\u0002\u0010\u0012\u001a\u00020\u000fH\u00c6\u0001J\u0013\u0010.\u001a\u00020\u00032\b\u0010/\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00100\u001a\u00020\rH\u00d6\u0001J\t\u00101\u001a\u00020\u000fH\u00d6\u0001R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0010\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u001aR\u0011\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u001aR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u001aR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0015R\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0017R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0015R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0015R\u0011\u0010\u0012\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0019R\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0019\u00a8\u00062"}, d2 = {"Lcom/microcareer/match/presentation/home/HomeUiState;", "", "isLoading", "", "isRefreshing", "jobs", "", "Lcom/microcareer/match/data/local/entity/JobEntity;", "recommendations", "savedJobs", "appliedJobs", "isOffline", "pendingSyncCount", "", "error", "", "currentSkip", "selectedCategory", "searchQuery", "(ZZLjava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;ZILjava/lang/String;ILjava/lang/String;Ljava/lang/String;)V", "getAppliedJobs", "()Ljava/util/List;", "getCurrentSkip", "()I", "getError", "()Ljava/lang/String;", "()Z", "getJobs", "getPendingSyncCount", "getRecommendations", "getSavedJobs", "getSearchQuery", "getSelectedCategory", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class HomeUiState {
    private final boolean isLoading = false;
    private final boolean isRefreshing = false;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.microcareer.match.data.local.entity.JobEntity> jobs = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.microcareer.match.data.local.entity.JobEntity> recommendations = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.microcareer.match.data.local.entity.JobEntity> savedJobs = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.microcareer.match.data.local.entity.JobEntity> appliedJobs = null;
    private final boolean isOffline = false;
    private final int pendingSyncCount = 0;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String error = null;
    private final int currentSkip = 0;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String selectedCategory = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String searchQuery = null;
    
    public HomeUiState(boolean isLoading, boolean isRefreshing, @org.jetbrains.annotations.NotNull()
    java.util.List<com.microcareer.match.data.local.entity.JobEntity> jobs, @org.jetbrains.annotations.NotNull()
    java.util.List<com.microcareer.match.data.local.entity.JobEntity> recommendations, @org.jetbrains.annotations.NotNull()
    java.util.List<com.microcareer.match.data.local.entity.JobEntity> savedJobs, @org.jetbrains.annotations.NotNull()
    java.util.List<com.microcareer.match.data.local.entity.JobEntity> appliedJobs, boolean isOffline, int pendingSyncCount, @org.jetbrains.annotations.Nullable()
    java.lang.String error, int currentSkip, @org.jetbrains.annotations.Nullable()
    java.lang.String selectedCategory, @org.jetbrains.annotations.NotNull()
    java.lang.String searchQuery) {
        super();
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    public final boolean isRefreshing() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.microcareer.match.data.local.entity.JobEntity> getJobs() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.microcareer.match.data.local.entity.JobEntity> getRecommendations() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.microcareer.match.data.local.entity.JobEntity> getSavedJobs() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.microcareer.match.data.local.entity.JobEntity> getAppliedJobs() {
        return null;
    }
    
    public final boolean isOffline() {
        return false;
    }
    
    public final int getPendingSyncCount() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getError() {
        return null;
    }
    
    public final int getCurrentSkip() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSelectedCategory() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSearchQuery() {
        return null;
    }
    
    public HomeUiState() {
        super();
    }
    
    public final boolean component1() {
        return false;
    }
    
    public final int component10() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component12() {
        return null;
    }
    
    public final boolean component2() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.microcareer.match.data.local.entity.JobEntity> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.microcareer.match.data.local.entity.JobEntity> component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.microcareer.match.data.local.entity.JobEntity> component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.microcareer.match.data.local.entity.JobEntity> component6() {
        return null;
    }
    
    public final boolean component7() {
        return false;
    }
    
    public final int component8() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.microcareer.match.presentation.home.HomeUiState copy(boolean isLoading, boolean isRefreshing, @org.jetbrains.annotations.NotNull()
    java.util.List<com.microcareer.match.data.local.entity.JobEntity> jobs, @org.jetbrains.annotations.NotNull()
    java.util.List<com.microcareer.match.data.local.entity.JobEntity> recommendations, @org.jetbrains.annotations.NotNull()
    java.util.List<com.microcareer.match.data.local.entity.JobEntity> savedJobs, @org.jetbrains.annotations.NotNull()
    java.util.List<com.microcareer.match.data.local.entity.JobEntity> appliedJobs, boolean isOffline, int pendingSyncCount, @org.jetbrains.annotations.Nullable()
    java.lang.String error, int currentSkip, @org.jetbrains.annotations.Nullable()
    java.lang.String selectedCategory, @org.jetbrains.annotations.NotNull()
    java.lang.String searchQuery) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}