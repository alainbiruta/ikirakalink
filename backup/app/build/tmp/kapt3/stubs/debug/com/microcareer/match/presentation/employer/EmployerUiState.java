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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0012\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BG\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\n\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J\u000f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\b0\u0005H\u00c6\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\nH\u00c6\u0003J\u000b\u0010\u0018\u001a\u0004\u0018\u00010\nH\u00c6\u0003JK\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00052\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\nH\u00c6\u0001J\u0013\u0010\u001a\u001a\u00020\u00032\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001c\u001a\u00020\u001dH\u00d6\u0001J\t\u0010\u001e\u001a\u00020\nH\u00d6\u0001R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\t\u001a\u0004\u0018\u00010\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0012R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000e\u00a8\u0006\u001f"}, d2 = {"Lcom/microcareer/match/presentation/employer/EmployerUiState;", "", "isLoading", "", "myJobs", "", "Lcom/microcareer/match/data/local/entity/JobEntity;", "applicants", "Lcom/microcareer/match/presentation/employer/ApplicantUiModel;", "companyName", "", "error", "(ZLjava/util/List;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;)V", "getApplicants", "()Ljava/util/List;", "getCompanyName", "()Ljava/lang/String;", "getError", "()Z", "getMyJobs", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class EmployerUiState {
    private final boolean isLoading = false;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.microcareer.match.data.local.entity.JobEntity> myJobs = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.microcareer.match.presentation.employer.ApplicantUiModel> applicants = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String companyName = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String error = null;
    
    public EmployerUiState(boolean isLoading, @org.jetbrains.annotations.NotNull()
    java.util.List<com.microcareer.match.data.local.entity.JobEntity> myJobs, @org.jetbrains.annotations.NotNull()
    java.util.List<com.microcareer.match.presentation.employer.ApplicantUiModel> applicants, @org.jetbrains.annotations.Nullable()
    java.lang.String companyName, @org.jetbrains.annotations.Nullable()
    java.lang.String error) {
        super();
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.microcareer.match.data.local.entity.JobEntity> getMyJobs() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.microcareer.match.presentation.employer.ApplicantUiModel> getApplicants() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getCompanyName() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getError() {
        return null;
    }
    
    public EmployerUiState() {
        super();
    }
    
    public final boolean component1() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.microcareer.match.data.local.entity.JobEntity> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.microcareer.match.presentation.employer.ApplicantUiModel> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.microcareer.match.presentation.employer.EmployerUiState copy(boolean isLoading, @org.jetbrains.annotations.NotNull()
    java.util.List<com.microcareer.match.data.local.entity.JobEntity> myJobs, @org.jetbrains.annotations.NotNull()
    java.util.List<com.microcareer.match.presentation.employer.ApplicantUiModel> applicants, @org.jetbrains.annotations.Nullable()
    java.lang.String companyName, @org.jetbrains.annotations.Nullable()
    java.lang.String error) {
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