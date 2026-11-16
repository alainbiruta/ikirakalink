package com.microcareer.match.data.local;

import android.content.Context;
import android.content.SharedPreferences;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u000f\u0018\u0000 $2\u00020\u0001:\u0001$B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\bJ\u0006\u0010\t\u001a\u00020\bJ\b\u0010\n\u001a\u0004\u0018\u00010\u000bJ\b\u0010\f\u001a\u0004\u0018\u00010\u000bJ\b\u0010\r\u001a\u0004\u0018\u00010\u000bJ\b\u0010\u000e\u001a\u0004\u0018\u00010\u000bJ\b\u0010\u000f\u001a\u0004\u0018\u00010\u000bJ\b\u0010\u0010\u001a\u0004\u0018\u00010\u000bJ\u0006\u0010\u0011\u001a\u00020\u000bJ\b\u0010\u0012\u001a\u0004\u0018\u00010\u000bJ\b\u0010\u0013\u001a\u0004\u0018\u00010\u000bJ\b\u0010\u0014\u001a\u0004\u0018\u00010\u000bJ\u0006\u0010\u0015\u001a\u00020\u0016J\u0006\u0010\u0017\u001a\u00020\u0016J\u0006\u0010\u0018\u001a\u00020\u0016J\u0018\u0010\u0019\u001a\u00020\b2\u0006\u0010\u001a\u001a\u00020\u000b2\b\b\u0002\u0010\u001b\u001a\u00020\u000bJ\u000e\u0010\u001c\u001a\u00020\b2\u0006\u0010\u001a\u001a\u00020\u000bJ>\u0010\u001d\u001a\u00020\b2\u0006\u0010\u001e\u001a\u00020\u000b2\u0006\u0010\u001f\u001a\u00020\u000b2\u0006\u0010 \u001a\u00020\u000b2\u0006\u0010!\u001a\u00020\u000b2\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010#\u001a\u0004\u0018\u00010\u000bR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006%"}, d2 = {"Lcom/microcareer/match/data/local/TokenManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "prefs", "Landroid/content/SharedPreferences;", "clearAuth", "", "clearFcmToken", "getAccessToken", "", "getAuthorizationHeader", "getCompanyName", "getFcmToken", "getFullName", "getPhoneNumber", "getTokenType", "getUserEmail", "getUserId", "getUserRole", "isEmployee", "", "isEmployer", "isLoggedIn", "saveAccessToken", "token", "tokenType", "saveFcmToken", "saveUserInfo", "userId", "fullName", "email", "role", "phoneNumber", "companyName", "Companion", "app_debug"})
public final class TokenManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREFS_NAME = "microcareer_auth_prefs";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_FCM_TOKEN = "fcm_token";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_ACCESS_TOKEN = "access_token";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_TOKEN_TYPE = "token_type";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_USER_ID = "user_id";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_FULL_NAME = "full_name";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_USER_EMAIL = "user_email";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_USER_ROLE = "user_role";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_PHONE_NUMBER = "phone_number";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_COMPANY_NAME = "company_name";
    @org.jetbrains.annotations.NotNull()
    public static final com.microcareer.match.data.local.TokenManager.Companion Companion = null;
    
    public TokenManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    public final void saveAccessToken(@org.jetbrains.annotations.NotNull()
    java.lang.String token, @org.jetbrains.annotations.NotNull()
    java.lang.String tokenType) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getAccessToken() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTokenType() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getAuthorizationHeader() {
        return null;
    }
    
    public final boolean isLoggedIn() {
        return false;
    }
    
    public final void saveUserInfo(@org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    java.lang.String fullName, @org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String role, @org.jetbrains.annotations.Nullable()
    java.lang.String phoneNumber, @org.jetbrains.annotations.Nullable()
    java.lang.String companyName) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getUserId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getFullName() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getUserEmail() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getUserRole() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPhoneNumber() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getCompanyName() {
        return null;
    }
    
    public final boolean isEmployee() {
        return false;
    }
    
    public final boolean isEmployer() {
        return false;
    }
    
    public final void clearAuth() {
    }
    
    public final void saveFcmToken(@org.jetbrains.annotations.NotNull()
    java.lang.String token) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getFcmToken() {
        return null;
    }
    
    public final void clearFcmToken() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\n\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lcom/microcareer/match/data/local/TokenManager$Companion;", "", "()V", "KEY_ACCESS_TOKEN", "", "KEY_COMPANY_NAME", "KEY_FCM_TOKEN", "KEY_FULL_NAME", "KEY_PHONE_NUMBER", "KEY_TOKEN_TYPE", "KEY_USER_EMAIL", "KEY_USER_ID", "KEY_USER_ROLE", "PREFS_NAME", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}