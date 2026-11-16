package com.microcareer.match.data.network;

import com.microcareer.match.BuildConfig;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001\u0010B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\u0006R\u000e\u0010\t\u001a\u00020\nX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\nX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\nX\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/microcareer/match/data/network/ApiConfig;", "", "()V", "BASE_URL", "", "getBASE_URL", "()Ljava/lang/String;", "BASE_URL_EMULATOR", "getBASE_URL_EMULATOR", "CONNECT_TIMEOUT", "", "DEFAULT_PAGE_SIZE", "", "MAX_PAGE_SIZE", "READ_TIMEOUT", "WRITE_TIMEOUT", "Endpoints", "app_debug"})
public final class ApiConfig {
    
    /**
     * Request timeouts
     */
    public static final long CONNECT_TIMEOUT = 30L;
    public static final long READ_TIMEOUT = 30L;
    public static final long WRITE_TIMEOUT = 30L;
    
    /**
     * Pagination
     */
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int MAX_PAGE_SIZE = 50;
    @org.jetbrains.annotations.NotNull()
    public static final com.microcareer.match.data.network.ApiConfig INSTANCE = null;
    
    private ApiConfig() {
        super();
    }
    
    private final java.lang.String getBASE_URL_EMULATOR() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getBASE_URL() {
        return null;
    }
    
    /**
     * API Endpoints
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0010\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/microcareer/match/data/network/ApiConfig$Endpoints;", "", "()V", "APPLICATIONS", "", "APPLICATION_DETAIL", "JOBS", "JOB_DETAIL", "LOGIN", "MATCH_SCORE", "ME", "MY_APPLICATIONS", "MY_JOBS", "MY_REVIEWS", "RECOMMENDATIONS", "REFRESH_TOKEN", "REGISTER", "REVIEWS", "SEARCH_JOBS", "UPDATE_PROFILE", "app_debug"})
    public static final class Endpoints {
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String LOGIN = "api/v1/auth/login";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String REGISTER = "api/v1/auth/register";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String REFRESH_TOKEN = "api/v1/auth/refresh";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String ME = "api/v1/users/me";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String UPDATE_PROFILE = "api/v1/users/profile";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String JOBS = "api/v1/jobs";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String JOB_DETAIL = "api/v1/jobs/{id}";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String MY_JOBS = "api/v1/jobs/my-jobs";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String SEARCH_JOBS = "api/v1/jobs/search";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String APPLICATIONS = "api/v1/applications";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String MY_APPLICATIONS = "api/v1/applications/my-applications";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String APPLICATION_DETAIL = "api/v1/applications/{id}";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String REVIEWS = "api/v1/reviews";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String MY_REVIEWS = "api/v1/reviews/my-reviews";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String RECOMMENDATIONS = "api/v1/matching/recommendations/advanced";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String MATCH_SCORE = "api/v1/matching/match-score/{id}";
        @org.jetbrains.annotations.NotNull()
        public static final com.microcareer.match.data.network.ApiConfig.Endpoints INSTANCE = null;
        
        private Endpoints() {
            super();
        }
    }
}