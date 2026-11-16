package com.microcareer.match.presentation.profile;

import com.microcareer.match.utils.LocaleHelper;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b,\b\u0086\b\u0018\u00002\u00020\u0001B\u00af\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\b\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0010\u0012\u000e\b\u0002\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\b0\u0013\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0015\u001a\u00020\b\u0012\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\b\u00a2\u0006\u0002\u0010\u0017J\t\u0010+\u001a\u00020\u0003H\u00c6\u0003J\t\u0010,\u001a\u00020\u0010H\u00c6\u0003J\t\u0010-\u001a\u00020\u0010H\u00c6\u0003J\u000f\u0010.\u001a\b\u0012\u0004\u0012\u00020\b0\u0013H\u00c6\u0003J\t\u0010/\u001a\u00020\u0003H\u00c6\u0003J\t\u00100\u001a\u00020\bH\u00c6\u0003J\u000b\u00101\u001a\u0004\u0018\u00010\bH\u00c6\u0003J\t\u00102\u001a\u00020\u0003H\u00c6\u0003J\u000b\u00103\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\u000b\u00104\u001a\u0004\u0018\u00010\bH\u00c6\u0003J\u000b\u00105\u001a\u0004\u0018\u00010\bH\u00c6\u0003J\u000b\u00106\u001a\u0004\u0018\u00010\bH\u00c6\u0003J\u000b\u00107\u001a\u0004\u0018\u00010\bH\u00c6\u0003J\u000b\u00108\u001a\u0004\u0018\u00010\bH\u00c6\u0003J\t\u00109\u001a\u00020\u000eH\u00c6\u0003J\u00b3\u0001\u0010:\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u00102\u000e\b\u0002\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\b0\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u00032\b\b\u0002\u0010\u0015\u001a\u00020\b2\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\bH\u00c6\u0001J\u0013\u0010;\u001a\u00020\u00032\b\u0010<\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010=\u001a\u00020\u0010H\u00d6\u0001J\t\u0010>\u001a\u00020\bH\u00d6\u0001R\u0013\u0010\t\u001a\u0004\u0018\u00010\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0013\u0010\u0016\u001a\u0004\u0018\u00010\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0019R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0019R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u001eR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u001eR\u0013\u0010\f\u001a\u0004\u0018\u00010\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0019R\u0011\u0010\u0014\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001eR\u0013\u0010\u000b\u001a\u0004\u0018\u00010\b\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0019R\u0013\u0010\n\u001a\u0004\u0018\u00010\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0019R\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0011\u0010\u0015\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0019R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\b0\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\'R\u0011\u0010\u0011\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u001bR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010*\u00a8\u0006?"}, d2 = {"Lcom/microcareer/match/presentation/profile/ProfileUiState;", "", "isLoading", "", "isUploadingPhoto", "user", "Lcom/microcareer/match/presentation/profile/UserUi;", "fullName", "", "bio", "profilePhotoUrl", "phoneNumber", "location", "rating", "", "completedJobs", "", "totalReviews", "skills", "", "notificationsEnabled", "selectedLanguage", "error", "(ZZLcom/microcareer/match/presentation/profile/UserUi;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;FIILjava/util/List;ZLjava/lang/String;Ljava/lang/String;)V", "getBio", "()Ljava/lang/String;", "getCompletedJobs", "()I", "getError", "getFullName", "()Z", "getLocation", "getNotificationsEnabled", "getPhoneNumber", "getProfilePhotoUrl", "getRating", "()F", "getSelectedLanguage", "getSkills", "()Ljava/util/List;", "getTotalReviews", "getUser", "()Lcom/microcareer/match/presentation/profile/UserUi;", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class ProfileUiState {
    private final boolean isLoading = false;
    private final boolean isUploadingPhoto = false;
    @org.jetbrains.annotations.Nullable()
    private final com.microcareer.match.presentation.profile.UserUi user = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String fullName = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String bio = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String profilePhotoUrl = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String phoneNumber = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String location = null;
    private final float rating = 0.0F;
    private final int completedJobs = 0;
    private final int totalReviews = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> skills = null;
    private final boolean notificationsEnabled = false;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String selectedLanguage = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String error = null;
    
    public ProfileUiState(boolean isLoading, boolean isUploadingPhoto, @org.jetbrains.annotations.Nullable()
    com.microcareer.match.presentation.profile.UserUi user, @org.jetbrains.annotations.Nullable()
    java.lang.String fullName, @org.jetbrains.annotations.Nullable()
    java.lang.String bio, @org.jetbrains.annotations.Nullable()
    java.lang.String profilePhotoUrl, @org.jetbrains.annotations.Nullable()
    java.lang.String phoneNumber, @org.jetbrains.annotations.Nullable()
    java.lang.String location, float rating, int completedJobs, int totalReviews, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> skills, boolean notificationsEnabled, @org.jetbrains.annotations.NotNull()
    java.lang.String selectedLanguage, @org.jetbrains.annotations.Nullable()
    java.lang.String error) {
        super();
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    public final boolean isUploadingPhoto() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.microcareer.match.presentation.profile.UserUi getUser() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getFullName() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getBio() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getProfilePhotoUrl() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPhoneNumber() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getLocation() {
        return null;
    }
    
    public final float getRating() {
        return 0.0F;
    }
    
    public final int getCompletedJobs() {
        return 0;
    }
    
    public final int getTotalReviews() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getSkills() {
        return null;
    }
    
    public final boolean getNotificationsEnabled() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSelectedLanguage() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getError() {
        return null;
    }
    
    public ProfileUiState() {
        super();
    }
    
    public final boolean component1() {
        return false;
    }
    
    public final int component10() {
        return 0;
    }
    
    public final int component11() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component12() {
        return null;
    }
    
    public final boolean component13() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component14() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component15() {
        return null;
    }
    
    public final boolean component2() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.microcareer.match.presentation.profile.UserUi component3() {
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
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component8() {
        return null;
    }
    
    public final float component9() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.microcareer.match.presentation.profile.ProfileUiState copy(boolean isLoading, boolean isUploadingPhoto, @org.jetbrains.annotations.Nullable()
    com.microcareer.match.presentation.profile.UserUi user, @org.jetbrains.annotations.Nullable()
    java.lang.String fullName, @org.jetbrains.annotations.Nullable()
    java.lang.String bio, @org.jetbrains.annotations.Nullable()
    java.lang.String profilePhotoUrl, @org.jetbrains.annotations.Nullable()
    java.lang.String phoneNumber, @org.jetbrains.annotations.Nullable()
    java.lang.String location, float rating, int completedJobs, int totalReviews, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> skills, boolean notificationsEnabled, @org.jetbrains.annotations.NotNull()
    java.lang.String selectedLanguage, @org.jetbrains.annotations.Nullable()
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