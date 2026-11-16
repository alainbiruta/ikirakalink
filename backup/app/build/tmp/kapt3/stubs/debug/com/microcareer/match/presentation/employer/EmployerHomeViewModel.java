package com.microcareer.match.presentation.employer;

import androidx.lifecycle.ViewModel;
import com.microcareer.match.data.local.entity.JobEntity;
import com.microcareer.match.data.local.TokenManager;
import com.microcareer.match.data.repository.AppResult;
import com.microcareer.match.data.repository.JobRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;
import com.microcareer.match.data.local.FcmNotificationStore;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001eJ\u000e\u0010 \u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020\u001eJ\u0006\u0010!\u001a\u00020\u001cJ\u0006\u0010\"\u001a\u00020\u001cJ\u0006\u0010#\u001a\u00020\u001cJ\b\u0010$\u001a\u00020\u001cH\u0002J\u0006\u0010%\u001a\u00020\u001cJ\u0016\u0010&\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001eJ\u000e\u0010\'\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020\u001eJ \u0010(\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001e2\u0006\u0010)\u001a\u00020\u001eH\u0002R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001d\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u00150\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0018\u00a8\u0006*"}, d2 = {"Lcom/microcareer/match/presentation/employer/EmployerHomeViewModel;", "Landroidx/lifecycle/ViewModel;", "jobRepository", "Lcom/microcareer/match/data/repository/JobRepository;", "tokenManager", "Lcom/microcareer/match/data/local/TokenManager;", "notificationStore", "Lcom/microcareer/match/data/local/FcmNotificationStore;", "(Lcom/microcareer/match/data/repository/JobRepository;Lcom/microcareer/match/data/local/TokenManager;Lcom/microcareer/match/data/local/FcmNotificationStore;)V", "_events", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "Lcom/microcareer/match/presentation/employer/EmployerUiEvent;", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/microcareer/match/presentation/employer/EmployerUiState;", "events", "Lkotlinx/coroutines/flow/SharedFlow;", "getEvents", "()Lkotlinx/coroutines/flow/SharedFlow;", "fcmNotifications", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/microcareer/match/data/local/FcmNotification;", "getFcmNotifications", "()Lkotlinx/coroutines/flow/StateFlow;", "uiState", "getUiState", "acceptApplicant", "", "applicantId", "", "jobId", "deleteJob", "loadApplicants", "loadMyJobs", "markNotificationsRead", "observeJobsChanged", "refresh", "rejectApplicant", "toggleJobStatus", "updateApplicantStatus", "status", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class EmployerHomeViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.data.repository.JobRepository jobRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.data.local.TokenManager tokenManager = null;
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.data.local.FcmNotificationStore notificationStore = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.microcareer.match.data.local.FcmNotification>> fcmNotifications = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.microcareer.match.presentation.employer.EmployerUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.microcareer.match.presentation.employer.EmployerUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableSharedFlow<com.microcareer.match.presentation.employer.EmployerUiEvent> _events = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.SharedFlow<com.microcareer.match.presentation.employer.EmployerUiEvent> events = null;
    
    @javax.inject.Inject()
    public EmployerHomeViewModel(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.repository.JobRepository jobRepository, @org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.TokenManager tokenManager, @org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.FcmNotificationStore notificationStore) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.microcareer.match.data.local.FcmNotification>> getFcmNotifications() {
        return null;
    }
    
    public final void markNotificationsRead() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.microcareer.match.presentation.employer.EmployerUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<com.microcareer.match.presentation.employer.EmployerUiEvent> getEvents() {
        return null;
    }
    
    public final void loadMyJobs() {
    }
    
    public final void loadApplicants() {
    }
    
    public final void toggleJobStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String jobId) {
    }
    
    private final void observeJobsChanged() {
    }
    
    public final void deleteJob(@org.jetbrains.annotations.NotNull()
    java.lang.String jobId) {
    }
    
    public final void acceptApplicant(@org.jetbrains.annotations.NotNull()
    java.lang.String applicantId, @org.jetbrains.annotations.NotNull()
    java.lang.String jobId) {
    }
    
    public final void rejectApplicant(@org.jetbrains.annotations.NotNull()
    java.lang.String applicantId, @org.jetbrains.annotations.NotNull()
    java.lang.String jobId) {
    }
    
    private final void updateApplicantStatus(java.lang.String applicantId, java.lang.String jobId, java.lang.String status) {
    }
    
    public final void refresh() {
    }
}