package com.microcareer.match.data.remote.models;

import com.google.gson.annotations.SerializedName;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0016\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B=\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bJ\u000f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0006H\u00c6\u0003J\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0011J\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0011J\t\u0010\u001a\u001a\u00020\nH\u00c6\u0003JJ\u0010\u001b\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\t\u001a\u00020\nH\u00c6\u0001\u00a2\u0006\u0002\u0010\u001cJ\u0013\u0010\u001d\u001a\u00020\n2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001f\u001a\u00020\u0006H\u00d6\u0001J\t\u0010 \u001a\u00020!H\u00d6\u0001R\u001c\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0016\u0010\t\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0007\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\b\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0013\u0010\u0011R\u0016\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006\""}, d2 = {"Lcom/microcareer/match/data/remote/models/ReceivedApplicationsData;", "", "applications", "", "Lcom/microcareer/match/data/remote/models/RemoteApplication;", "total", "", "page", "pageSize", "hasMore", "", "(Ljava/util/List;ILjava/lang/Integer;Ljava/lang/Integer;Z)V", "getApplications", "()Ljava/util/List;", "getHasMore", "()Z", "getPage", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getPageSize", "getTotal", "()I", "component1", "component2", "component3", "component4", "component5", "copy", "(Ljava/util/List;ILjava/lang/Integer;Ljava/lang/Integer;Z)Lcom/microcareer/match/data/remote/models/ReceivedApplicationsData;", "equals", "other", "hashCode", "toString", "", "app_debug"})
public final class ReceivedApplicationsData {
    @com.google.gson.annotations.SerializedName(value = "applications")
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.microcareer.match.data.remote.models.RemoteApplication> applications = null;
    @com.google.gson.annotations.SerializedName(value = "total")
    private final int total = 0;
    @com.google.gson.annotations.SerializedName(value = "page")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer page = null;
    @com.google.gson.annotations.SerializedName(value = "page_size")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer pageSize = null;
    @com.google.gson.annotations.SerializedName(value = "has_more")
    private final boolean hasMore = false;
    
    public ReceivedApplicationsData(@org.jetbrains.annotations.NotNull()
    java.util.List<com.microcareer.match.data.remote.models.RemoteApplication> applications, int total, @org.jetbrains.annotations.Nullable()
    java.lang.Integer page, @org.jetbrains.annotations.Nullable()
    java.lang.Integer pageSize, boolean hasMore) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.microcareer.match.data.remote.models.RemoteApplication> getApplications() {
        return null;
    }
    
    public final int getTotal() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getPage() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getPageSize() {
        return null;
    }
    
    public final boolean getHasMore() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.microcareer.match.data.remote.models.RemoteApplication> component1() {
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
    
    public final boolean component5() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.microcareer.match.data.remote.models.ReceivedApplicationsData copy(@org.jetbrains.annotations.NotNull()
    java.util.List<com.microcareer.match.data.remote.models.RemoteApplication> applications, int total, @org.jetbrains.annotations.Nullable()
    java.lang.Integer page, @org.jetbrains.annotations.Nullable()
    java.lang.Integer pageSize, boolean hasMore) {
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