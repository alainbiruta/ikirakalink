package com.microcareer.match.presentation.employer;

import androidx.lifecycle.ViewModel;
import com.microcareer.match.data.repository.AppResult;
import com.microcareer.match.data.repository.JobRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0082\u0001\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u000f2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u000f0\u001a2\b\b\u0002\u0010\u001b\u001a\u00020\u001c2\b\b\u0002\u0010\u001d\u001a\u00020\u001e2\b\b\u0002\u0010\u001f\u001a\u00020\u001e2\n\b\u0002\u0010 \u001a\u0004\u0018\u00010\u000f2\n\b\u0002\u0010!\u001a\u0004\u0018\u00010\u000fJ0\u0010\"\u001a\u00020\u001c2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u000fH\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006#"}, d2 = {"Lcom/microcareer/match/presentation/employer/PostJobViewModel;", "Landroidx/lifecycle/ViewModel;", "jobRepository", "Lcom/microcareer/match/data/repository/JobRepository;", "(Lcom/microcareer/match/data/repository/JobRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/microcareer/match/presentation/employer/PostJobUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "loadJob", "", "jobId", "", "submitJob", "title", "description", "location", "payment", "", "currency", "duration", "category", "skills", "", "isUrgent", "", "requiredHours", "", "maxApplicants", "deadline", "editJobId", "validate", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class PostJobViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.data.repository.JobRepository jobRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.microcareer.match.presentation.employer.PostJobUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.microcareer.match.presentation.employer.PostJobUiState> uiState = null;
    
    @javax.inject.Inject()
    public PostJobViewModel(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.repository.JobRepository jobRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.microcareer.match.presentation.employer.PostJobUiState> getUiState() {
        return null;
    }
    
    public final void loadJob(@org.jetbrains.annotations.NotNull()
    java.lang.String jobId) {
    }
    
    public final void submitJob(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String description, @org.jetbrains.annotations.NotNull()
    java.lang.String location, double payment, @org.jetbrains.annotations.NotNull()
    java.lang.String currency, @org.jetbrains.annotations.NotNull()
    java.lang.String duration, @org.jetbrains.annotations.NotNull()
    java.lang.String category, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> skills, boolean isUrgent, int requiredHours, int maxApplicants, @org.jetbrains.annotations.Nullable()
    java.lang.String deadline, @org.jetbrains.annotations.Nullable()
    java.lang.String editJobId) {
    }
    
    private final boolean validate(java.lang.String title, java.lang.String description, java.lang.String location, double payment, java.lang.String category) {
        return false;
    }
}