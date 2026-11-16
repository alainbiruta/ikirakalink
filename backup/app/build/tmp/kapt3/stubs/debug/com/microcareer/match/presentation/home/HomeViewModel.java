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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0012\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u000e\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"J\b\u0010#\u001a\u00020 H\u0002J\u0006\u0010$\u001a\u00020 J\u001c\u0010%\u001a\u00020 2\b\b\u0002\u0010&\u001a\u00020\'2\n\b\u0002\u0010(\u001a\u0004\u0018\u00010\"J\u0006\u0010)\u001a\u00020 J\u0006\u0010*\u001a\u00020 J\u0006\u0010+\u001a\u00020 J\b\u0010,\u001a\u00020 H\u0002J\b\u0010-\u001a\u00020 H\u0002J\b\u0010.\u001a\u00020 H\u0002J\b\u0010/\u001a\u00020 H\u0002J\b\u00100\u001a\u00020 H\u0002J\u0006\u00101\u001a\u00020 J\u000e\u00102\u001a\u00020 2\u0006\u00103\u001a\u00020\"J\u0010\u00104\u001a\u00020 2\b\u0010(\u001a\u0004\u0018\u00010\"J\b\u00105\u001a\u00020 H\u0002J\b\u00106\u001a\u00020 H\u0002J\u0016\u00107\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\u0006\u00108\u001a\u00020\u001eR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00110\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0014R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u00069"}, d2 = {"Lcom/microcareer/match/presentation/home/HomeViewModel;", "Landroidx/lifecycle/ViewModel;", "jobRepository", "Lcom/microcareer/match/data/repository/JobRepository;", "networkMonitor", "Lcom/microcareer/match/utils/NetworkMonitor;", "notificationStore", "Lcom/microcareer/match/data/local/FcmNotificationStore;", "(Lcom/microcareer/match/data/repository/JobRepository;Lcom/microcareer/match/utils/NetworkMonitor;Lcom/microcareer/match/data/local/FcmNotificationStore;)V", "_uiEvents", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "Lcom/microcareer/match/presentation/home/HomeUiEvent;", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/microcareer/match/presentation/home/HomeUiState;", "fcmNotifications", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/microcareer/match/data/local/FcmNotification;", "getFcmNotifications", "()Lkotlinx/coroutines/flow/StateFlow;", "jobsCollectJob", "Lkotlinx/coroutines/Job;", "uiEvents", "Lkotlinx/coroutines/flow/SharedFlow;", "getUiEvents", "()Lkotlinx/coroutines/flow/SharedFlow;", "uiState", "getUiState", "wasOffline", "", "applyForJob", "", "jobId", "", "checkApplicationStatusNotifications", "clearError", "loadJobs", "skip", "", "category", "loadNextPage", "loadRecommendations", "markNotificationsRead", "observeAppliedJobs", "observeConnectivity", "observeJobsChanged", "observePendingSyncCount", "observeSavedJobs", "refresh", "search", "query", "setCategory", "silentReload", "syncAppliedJobs", "toggleSaveJob", "currentlySaved", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class HomeViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.data.repository.JobRepository jobRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.utils.NetworkMonitor networkMonitor = null;
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.data.local.FcmNotificationStore notificationStore = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.microcareer.match.presentation.home.HomeUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.microcareer.match.presentation.home.HomeUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableSharedFlow<com.microcareer.match.presentation.home.HomeUiEvent> _uiEvents = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.SharedFlow<com.microcareer.match.presentation.home.HomeUiEvent> uiEvents = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.microcareer.match.data.local.FcmNotification>> fcmNotifications = null;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job jobsCollectJob;
    private boolean wasOffline = false;
    
    @javax.inject.Inject()
    public HomeViewModel(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.repository.JobRepository jobRepository, @org.jetbrains.annotations.NotNull()
    com.microcareer.match.utils.NetworkMonitor networkMonitor, @org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.FcmNotificationStore notificationStore) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.microcareer.match.presentation.home.HomeUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<com.microcareer.match.presentation.home.HomeUiEvent> getUiEvents() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.microcareer.match.data.local.FcmNotification>> getFcmNotifications() {
        return null;
    }
    
    public final void markNotificationsRead() {
    }
    
    private final void observeSavedJobs() {
    }
    
    private final void observeAppliedJobs() {
    }
    
    private final void observeJobsChanged() {
    }
    
    private final void observeConnectivity() {
    }
    
    private final void observePendingSyncCount() {
    }
    
    public final void loadJobs(int skip, @org.jetbrains.annotations.Nullable()
    java.lang.String category) {
    }
    
    public final void loadNextPage() {
    }
    
    public final void loadRecommendations() {
    }
    
    public final void setCategory(@org.jetbrains.annotations.Nullable()
    java.lang.String category) {
    }
    
    public final void toggleSaveJob(@org.jetbrains.annotations.NotNull()
    java.lang.String jobId, boolean currentlySaved) {
    }
    
    public final void applyForJob(@org.jetbrains.annotations.NotNull()
    java.lang.String jobId) {
    }
    
    public final void refresh() {
    }
    
    private final void silentReload() {
    }
    
    public final void search(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    private final void syncAppliedJobs() {
    }
    
    private final void checkApplicationStatusNotifications() {
    }
    
    public final void clearError() {
    }
}