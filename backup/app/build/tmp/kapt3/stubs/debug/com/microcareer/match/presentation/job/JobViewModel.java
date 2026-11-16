package com.microcareer.match.presentation.job;

import androidx.lifecycle.ViewModel;
import com.microcareer.match.data.local.entity.JobEntity;
import com.microcareer.match.data.repository.AppResult;
import com.microcareer.match.data.repository.JobRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.*;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016J\u0006\u0010\u0017\u001a\u00020\u0014J\u0006\u0010\u0018\u001a\u00020\u0014J\u000e\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016J\u0016\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\u001cR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\n0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u001d"}, d2 = {"Lcom/microcareer/match/presentation/job/JobViewModel;", "Landroidx/lifecycle/ViewModel;", "jobRepository", "Lcom/microcareer/match/data/repository/JobRepository;", "(Lcom/microcareer/match/data/repository/JobRepository;)V", "_jobDetailsUiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/microcareer/match/presentation/job/JobDetailsUiState;", "_uiEvents", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "Lcom/microcareer/match/presentation/job/JobUiEvent;", "jobDetailsUiState", "Lkotlinx/coroutines/flow/StateFlow;", "getJobDetailsUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "uiEvents", "Lkotlinx/coroutines/flow/SharedFlow;", "getUiEvents", "()Lkotlinx/coroutines/flow/SharedFlow;", "applyToJob", "", "jobId", "", "clearSelectedJob", "retryLoadJobs", "selectJob", "toggleSaveJobById", "currentlySaved", "", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class JobViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.data.repository.JobRepository jobRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.microcareer.match.presentation.job.JobDetailsUiState> _jobDetailsUiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.microcareer.match.presentation.job.JobDetailsUiState> jobDetailsUiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableSharedFlow<com.microcareer.match.presentation.job.JobUiEvent> _uiEvents = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.SharedFlow<com.microcareer.match.presentation.job.JobUiEvent> uiEvents = null;
    
    @javax.inject.Inject()
    public JobViewModel(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.repository.JobRepository jobRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.microcareer.match.presentation.job.JobDetailsUiState> getJobDetailsUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<com.microcareer.match.presentation.job.JobUiEvent> getUiEvents() {
        return null;
    }
    
    public final void selectJob(@org.jetbrains.annotations.NotNull()
    java.lang.String jobId) {
    }
    
    public final void clearSelectedJob() {
    }
    
    public final void retryLoadJobs() {
    }
    
    public final void applyToJob(@org.jetbrains.annotations.NotNull()
    java.lang.String jobId) {
    }
    
    public final void toggleSaveJobById(@org.jetbrains.annotations.NotNull()
    java.lang.String jobId, boolean currentlySaved) {
    }
}