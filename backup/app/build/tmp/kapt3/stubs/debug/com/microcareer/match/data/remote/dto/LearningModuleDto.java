package com.microcareer.match.data.remote.dto;

import com.google.gson.annotations.SerializedName;
import com.microcareer.match.data.local.entity.LearningModuleEntity;

/**
 * Remote DTO — mirrors LearningModule.to_dict() from the backend.
 *
 * Field names use @SerializedName to map camelCase JSON keys
 * (returned by the Python backend) to Kotlin properties.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b0\b\u0086\b\u0018\u00002\u00020\u0001B\u0095\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00030\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0011\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0013\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0015\u001a\u00020\b\u0012\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0011\u00a2\u0006\u0002\u0010\u0017J\t\u0010.\u001a\u00020\u0003H\u00c6\u0003J\t\u0010/\u001a\u00020\u000fH\u00c6\u0003J\t\u00100\u001a\u00020\u0011H\u00c6\u0003J\t\u00101\u001a\u00020\u0013H\u00c6\u0003J\t\u00102\u001a\u00020\u0003H\u00c6\u0003J\t\u00103\u001a\u00020\bH\u00c6\u0003J\u0010\u00104\u001a\u0004\u0018\u00010\u0011H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0019J\t\u00105\u001a\u00020\u0003H\u00c6\u0003J\t\u00106\u001a\u00020\u0003H\u00c6\u0003J\t\u00107\u001a\u00020\u0003H\u00c6\u0003J\t\u00108\u001a\u00020\bH\u00c6\u0003J\u000b\u00109\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010:\u001a\u00020\u0003H\u00c6\u0003J\t\u0010;\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010<\u001a\b\u0012\u0004\u0012\u00020\u00030\rH\u00c6\u0003J\u00ae\u0001\u0010=\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00030\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u00032\b\b\u0002\u0010\u0015\u001a\u00020\b2\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0011H\u00c6\u0001\u00a2\u0006\u0002\u0010>J\u0013\u0010?\u001a\u00020\u00132\b\u0010@\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010A\u001a\u00020\bH\u00d6\u0001J\t\u0010B\u001a\u00020\u0003H\u00d6\u0001R\u001a\u0010\u0016\u001a\u0004\u0018\u00010\u00118\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\u0018\u0010\u0019R\u0016\u0010\n\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0018\u0010\t\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001cR\u0016\u0010\u0010\u001a\u00020\u00118\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0016\u0010\u0005\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001cR\u0016\u0010\u000b\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001cR\u0016\u0010\u0007\u001a\u00020\b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001cR\u0016\u0010\u0012\u001a\u00020\u00138\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010%R\u001c\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00030\r8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\'R\u0016\u0010\u0015\u001a\u00020\b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010#R\u0016\u0010\u000e\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u0016\u0010\u0006\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\u001cR\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010\u001cR\u0016\u0010\u0014\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010\u001c\u00a8\u0006C"}, d2 = {"Lcom/microcareer/match/data/remote/dto/LearningModuleDto;", "", "id", "", "title", "description", "skillName", "duration", "", "contentUrl", "contentType", "difficulty", "prerequisites", "", "recommendationScore", "", "createdAt", "", "isSynced", "", "userStatus", "progressPercent", "completedAt", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;FJZLjava/lang/String;ILjava/lang/Long;)V", "getCompletedAt", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getContentType", "()Ljava/lang/String;", "getContentUrl", "getCreatedAt", "()J", "getDescription", "getDifficulty", "getDuration", "()I", "getId", "()Z", "getPrerequisites", "()Ljava/util/List;", "getProgressPercent", "getRecommendationScore", "()F", "getSkillName", "getTitle", "getUserStatus", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;FJZLjava/lang/String;ILjava/lang/Long;)Lcom/microcareer/match/data/remote/dto/LearningModuleDto;", "equals", "other", "hashCode", "toString", "app_debug"})
public final class LearningModuleDto {
    @com.google.gson.annotations.SerializedName(value = "id")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String id = null;
    @com.google.gson.annotations.SerializedName(value = "title")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String title = null;
    @com.google.gson.annotations.SerializedName(value = "description")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String description = null;
    @com.google.gson.annotations.SerializedName(value = "skillName")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String skillName = null;
    @com.google.gson.annotations.SerializedName(value = "duration")
    private final int duration = 0;
    @com.google.gson.annotations.SerializedName(value = "contentUrl")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String contentUrl = null;
    @com.google.gson.annotations.SerializedName(value = "contentType")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String contentType = null;
    @com.google.gson.annotations.SerializedName(value = "difficulty")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String difficulty = null;
    @com.google.gson.annotations.SerializedName(value = "prerequisites")
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> prerequisites = null;
    @com.google.gson.annotations.SerializedName(value = "recommendationScore")
    private final float recommendationScore = 0.0F;
    @com.google.gson.annotations.SerializedName(value = "createdAt")
    private final long createdAt = 0L;
    @com.google.gson.annotations.SerializedName(value = "isSynced")
    private final boolean isSynced = false;
    @com.google.gson.annotations.SerializedName(value = "userStatus")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String userStatus = null;
    @com.google.gson.annotations.SerializedName(value = "progressPercent")
    private final int progressPercent = 0;
    @com.google.gson.annotations.SerializedName(value = "completedAt")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long completedAt = null;
    
    public LearningModuleDto(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String description, @org.jetbrains.annotations.NotNull()
    java.lang.String skillName, int duration, @org.jetbrains.annotations.Nullable()
    java.lang.String contentUrl, @org.jetbrains.annotations.NotNull()
    java.lang.String contentType, @org.jetbrains.annotations.NotNull()
    java.lang.String difficulty, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> prerequisites, float recommendationScore, long createdAt, boolean isSynced, @org.jetbrains.annotations.NotNull()
    java.lang.String userStatus, int progressPercent, @org.jetbrains.annotations.Nullable()
    java.lang.Long completedAt) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDescription() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSkillName() {
        return null;
    }
    
    public final int getDuration() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getContentUrl() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getContentType() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDifficulty() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getPrerequisites() {
        return null;
    }
    
    public final float getRecommendationScore() {
        return 0.0F;
    }
    
    public final long getCreatedAt() {
        return 0L;
    }
    
    public final boolean isSynced() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUserStatus() {
        return null;
    }
    
    public final int getProgressPercent() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getCompletedAt() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    public final float component10() {
        return 0.0F;
    }
    
    public final long component11() {
        return 0L;
    }
    
    public final boolean component12() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component13() {
        return null;
    }
    
    public final int component14() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component15() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    public final int component5() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.microcareer.match.data.remote.dto.LearningModuleDto copy(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String description, @org.jetbrains.annotations.NotNull()
    java.lang.String skillName, int duration, @org.jetbrains.annotations.Nullable()
    java.lang.String contentUrl, @org.jetbrains.annotations.NotNull()
    java.lang.String contentType, @org.jetbrains.annotations.NotNull()
    java.lang.String difficulty, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> prerequisites, float recommendationScore, long createdAt, boolean isSynced, @org.jetbrains.annotations.NotNull()
    java.lang.String userStatus, int progressPercent, @org.jetbrains.annotations.Nullable()
    java.lang.Long completedAt) {
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