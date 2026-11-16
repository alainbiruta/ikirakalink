package com.microcareer.match.data.remote.models;

import com.google.gson.annotations.SerializedName;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B3\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\tJ\u000f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\u0006H\u00c6\u0003J\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\rJ\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\rJ@\u0010\u0016\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0006H\u00c6\u0001\u00a2\u0006\u0002\u0010\u0017J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001b\u001a\u00020\u0006H\u00d6\u0001J\t\u0010\u001c\u001a\u00020\u001dH\u00d6\u0001R\u001c\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\b\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u000e\u001a\u0004\b\f\u0010\rR\u001a\u0010\u0007\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u000e\u001a\u0004\b\u000f\u0010\rR\u0016\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u001e"}, d2 = {"Lcom/microcareer/match/data/remote/models/JobsData;", "", "jobs", "", "Lcom/microcareer/match/data/remote/models/RemoteJob;", "total", "", "skip", "limit", "(Ljava/util/List;ILjava/lang/Integer;Ljava/lang/Integer;)V", "getJobs", "()Ljava/util/List;", "getLimit", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getSkip", "getTotal", "()I", "component1", "component2", "component3", "component4", "copy", "(Ljava/util/List;ILjava/lang/Integer;Ljava/lang/Integer;)Lcom/microcareer/match/data/remote/models/JobsData;", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
public final class JobsData {
    @com.google.gson.annotations.SerializedName(value = "jobs")
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.microcareer.match.data.remote.models.RemoteJob> jobs = null;
    @com.google.gson.annotations.SerializedName(value = "total")
    private final int total = 0;
    @com.google.gson.annotations.SerializedName(value = "skip")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer skip = null;
    @com.google.gson.annotations.SerializedName(value = "limit")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer limit = null;
    
    public JobsData(@org.jetbrains.annotations.NotNull()
    java.util.List<com.microcareer.match.data.remote.models.RemoteJob> jobs, int total, @org.jetbrains.annotations.Nullable()
    java.lang.Integer skip, @org.jetbrains.annotations.Nullable()
    java.lang.Integer limit) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.microcareer.match.data.remote.models.RemoteJob> getJobs() {
        return null;
    }
    
    public final int getTotal() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getSkip() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getLimit() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.microcareer.match.data.remote.models.RemoteJob> component1() {
        return null;
    }
    
    public final int component2() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.microcareer.match.data.remote.models.JobsData copy(@org.jetbrains.annotations.NotNull()
    java.util.List<com.microcareer.match.data.remote.models.RemoteJob> jobs, int total, @org.jetbrains.annotations.Nullable()
    java.lang.Integer skip, @org.jetbrains.annotations.Nullable()
    java.lang.Integer limit) {
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