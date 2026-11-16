package com.microcareer.match.presentation.navigation;

/**
 * Sealed class representing where a notification tap should land.
 * MainActivity resolves the incoming Intent extras into one of these,
 * and AppNavigation consumes it once after the NavHost is ready.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0005\u0003\u0004\u0005\u0006\u0007B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0005\b\t\n\u000b\f\u00a8\u0006\r"}, d2 = {"Lcom/microcareer/match/presentation/navigation/NotificationDestination;", "", "()V", "EmployeeHome", "JobDetail", "Learning", "LearningDetail", "SavedJobs", "Lcom/microcareer/match/presentation/navigation/NotificationDestination$EmployeeHome;", "Lcom/microcareer/match/presentation/navigation/NotificationDestination$JobDetail;", "Lcom/microcareer/match/presentation/navigation/NotificationDestination$Learning;", "Lcom/microcareer/match/presentation/navigation/NotificationDestination$LearningDetail;", "Lcom/microcareer/match/presentation/navigation/NotificationDestination$SavedJobs;", "app_debug"})
public abstract class NotificationDestination {
    
    private NotificationDestination() {
        super();
    }
    
    /**
     * Fallback — land on employee home
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/microcareer/match/presentation/navigation/NotificationDestination$EmployeeHome;", "Lcom/microcareer/match/presentation/navigation/NotificationDestination;", "()V", "app_debug"})
    public static final class EmployeeHome extends com.microcareer.match.presentation.navigation.NotificationDestination {
        @org.jetbrains.annotations.NotNull()
        public static final com.microcareer.match.presentation.navigation.NotificationDestination.EmployeeHome INSTANCE = null;
        
        private EmployeeHome() {
        }
    }
    
    /**
     * Employee tapped a job_matched notification
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lcom/microcareer/match/presentation/navigation/NotificationDestination$JobDetail;", "Lcom/microcareer/match/presentation/navigation/NotificationDestination;", "jobId", "", "(Ljava/lang/String;)V", "getJobId", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class JobDetail extends com.microcareer.match.presentation.navigation.NotificationDestination {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String jobId = null;
        
        public JobDetail(@org.jetbrains.annotations.NotNull()
        java.lang.String jobId) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getJobId() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.microcareer.match.presentation.navigation.NotificationDestination.JobDetail copy(@org.jetbrains.annotations.NotNull()
        java.lang.String jobId) {
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
    
    /**
     * Employee tapped a skill_gap notification without a module id
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/microcareer/match/presentation/navigation/NotificationDestination$Learning;", "Lcom/microcareer/match/presentation/navigation/NotificationDestination;", "()V", "app_debug"})
    public static final class Learning extends com.microcareer.match.presentation.navigation.NotificationDestination {
        @org.jetbrains.annotations.NotNull()
        public static final com.microcareer.match.presentation.navigation.NotificationDestination.Learning INSTANCE = null;
        
        private Learning() {
        }
    }
    
    /**
     * Employee tapped a skill_gap notification with a specific module
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lcom/microcareer/match/presentation/navigation/NotificationDestination$LearningDetail;", "Lcom/microcareer/match/presentation/navigation/NotificationDestination;", "moduleId", "", "(Ljava/lang/String;)V", "getModuleId", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class LearningDetail extends com.microcareer.match.presentation.navigation.NotificationDestination {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String moduleId = null;
        
        public LearningDetail(@org.jetbrains.annotations.NotNull()
        java.lang.String moduleId) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getModuleId() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.microcareer.match.presentation.navigation.NotificationDestination.LearningDetail copy(@org.jetbrains.annotations.NotNull()
        java.lang.String moduleId) {
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
    
    /**
     * Employee tapped application_accepted / application_rejected
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/microcareer/match/presentation/navigation/NotificationDestination$SavedJobs;", "Lcom/microcareer/match/presentation/navigation/NotificationDestination;", "()V", "app_debug"})
    public static final class SavedJobs extends com.microcareer.match.presentation.navigation.NotificationDestination {
        @org.jetbrains.annotations.NotNull()
        public static final com.microcareer.match.presentation.navigation.NotificationDestination.SavedJobs INSTANCE = null;
        
        private SavedJobs() {
        }
    }
}