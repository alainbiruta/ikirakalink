package com.microcareer.match.data.remote.models;

import com.google.gson.annotations.SerializedName;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0019\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001Be\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\u00a2\u0006\u0002\u0010\u000eJ\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0018J\u000b\u0010\u001f\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010 \u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010!\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010\"\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003J\u000b\u0010#\u001a\u0004\u0018\u00010\rH\u00c6\u0003Jn\u0010$\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\rH\u00c6\u0001\u00a2\u0006\u0002\u0010%J\u0013\u0010&\u001a\u00020\'2\b\u0010(\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010)\u001a\u00020*H\u00d6\u0001J\u0006\u0010+\u001a\u00020,J\t\u0010-\u001a\u00020\u0003H\u00d6\u0001R\u0018\u0010\u0007\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0018\u0010\t\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0018\u0010\n\u001a\u0004\u0018\u00010\u000b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0018\u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u0018\u0010\f\u001a\u0004\u0018\u00010\r8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0019\u001a\u0004\b\u0017\u0010\u0018R\u0018\u0010\b\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0010R\u0018\u0010\u0004\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0010\u00a8\u0006."}, d2 = {"Lcom/microcareer/match/data/remote/models/RemoteApplication;", "", "id", "", "status", "matchScore", "", "appliedAt", "message", "coverLetter", "employee", "Lcom/microcareer/match/data/remote/models/RemoteApplicationEmployee;", "job", "Lcom/microcareer/match/data/remote/models/RemoteApplicationJob;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Double;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/microcareer/match/data/remote/models/RemoteApplicationEmployee;Lcom/microcareer/match/data/remote/models/RemoteApplicationJob;)V", "getAppliedAt", "()Ljava/lang/String;", "getCoverLetter", "getEmployee", "()Lcom/microcareer/match/data/remote/models/RemoteApplicationEmployee;", "getId", "getJob", "()Lcom/microcareer/match/data/remote/models/RemoteApplicationJob;", "getMatchScore", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getMessage", "getStatus", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Double;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/microcareer/match/data/remote/models/RemoteApplicationEmployee;Lcom/microcareer/match/data/remote/models/RemoteApplicationJob;)Lcom/microcareer/match/data/remote/models/RemoteApplication;", "equals", "", "other", "hashCode", "", "toApplicantUiModel", "Lcom/microcareer/match/presentation/employer/ApplicantUiModel;", "toString", "app_debug"})
public final class RemoteApplication {
    @com.google.gson.annotations.SerializedName(value = "id")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String id = null;
    @com.google.gson.annotations.SerializedName(value = "status")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String status = null;
    @com.google.gson.annotations.SerializedName(value = "matchScore")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double matchScore = null;
    @com.google.gson.annotations.SerializedName(value = "appliedAt")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String appliedAt = null;
    @com.google.gson.annotations.SerializedName(value = "message")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String message = null;
    @com.google.gson.annotations.SerializedName(value = "coverLetter")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String coverLetter = null;
    @com.google.gson.annotations.SerializedName(value = "employee")
    @org.jetbrains.annotations.Nullable()
    private final com.microcareer.match.data.remote.models.RemoteApplicationEmployee employee = null;
    @com.google.gson.annotations.SerializedName(value = "job")
    @org.jetbrains.annotations.Nullable()
    private final com.microcareer.match.data.remote.models.RemoteApplicationJob job = null;
    
    public RemoteApplication(@org.jetbrains.annotations.Nullable()
    java.lang.String id, @org.jetbrains.annotations.Nullable()
    java.lang.String status, @org.jetbrains.annotations.Nullable()
    java.lang.Double matchScore, @org.jetbrains.annotations.Nullable()
    java.lang.String appliedAt, @org.jetbrains.annotations.Nullable()
    java.lang.String message, @org.jetbrains.annotations.Nullable()
    java.lang.String coverLetter, @org.jetbrains.annotations.Nullable()
    com.microcareer.match.data.remote.models.RemoteApplicationEmployee employee, @org.jetbrains.annotations.Nullable()
    com.microcareer.match.data.remote.models.RemoteApplicationJob job) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getMatchScore() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getAppliedAt() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getCoverLetter() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.microcareer.match.data.remote.models.RemoteApplicationEmployee getEmployee() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.microcareer.match.data.remote.models.RemoteApplicationJob getJob() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.microcareer.match.presentation.employer.ApplicantUiModel toApplicantUiModel() {
        return null;
    }
    
    public RemoteApplication() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component3() {
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
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.microcareer.match.data.remote.models.RemoteApplicationEmployee component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.microcareer.match.data.remote.models.RemoteApplicationJob component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.microcareer.match.data.remote.models.RemoteApplication copy(@org.jetbrains.annotations.Nullable()
    java.lang.String id, @org.jetbrains.annotations.Nullable()
    java.lang.String status, @org.jetbrains.annotations.Nullable()
    java.lang.Double matchScore, @org.jetbrains.annotations.Nullable()
    java.lang.String appliedAt, @org.jetbrains.annotations.Nullable()
    java.lang.String message, @org.jetbrains.annotations.Nullable()
    java.lang.String coverLetter, @org.jetbrains.annotations.Nullable()
    com.microcareer.match.data.remote.models.RemoteApplicationEmployee employee, @org.jetbrains.annotations.Nullable()
    com.microcareer.match.data.remote.models.RemoteApplicationJob job) {
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