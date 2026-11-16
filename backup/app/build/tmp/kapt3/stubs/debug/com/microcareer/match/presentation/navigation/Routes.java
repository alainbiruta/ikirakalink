package com.microcareer.match.presentation.navigation;

import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.navigation.NavHostController;
import androidx.navigation.NavType;
import com.microcareer.match.presentation.auth.AuthViewModel;
import com.microcareer.match.presentation.home.HomeViewModel;
import com.microcareer.match.presentation.skill.SkillViewModel;
import com.microcareer.match.presentation.learning.LearningViewModel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0012\n\u0002\u0010\b\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00042\b\b\u0002\u0010\u0016\u001a\u00020\u0017J\u000e\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u0004J\u000e\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u0004J\u000e\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u001c\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2 = {"Lcom/microcareer/match/presentation/navigation/Routes;", "", "()V", "CV_BUILDER", "", "EDIT_JOB", "EMPLOYEE_GRAPH", "EMPLOYEE_HOME", "EMPLOYEE_PROFILE", "EMPLOYER_HOME", "EMPLOYER_PROFILE", "JOB_DETAIL", "LEARNING", "LEARNING_DETAIL", "LOGIN", "POST_JOB", "REGISTER", "SAVED_JOBS", "SKILLS", "SKILL_ADD", "SPLASH", "cvBuilder", "startTab", "", "editJob", "jobId", "jobDetail", "learningDetail", "moduleId", "app_debug"})
public final class Routes {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String LOGIN = "login";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String REGISTER = "register";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SPLASH = "splash";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EMPLOYER_HOME = "employer/home";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EMPLOYER_PROFILE = "employer/profile";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String POST_JOB = "employer/post-job";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EDIT_JOB = "employer/edit-job/{jobId}";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EMPLOYEE_GRAPH = "employee_graph";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EMPLOYEE_HOME = "employee/home";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EMPLOYEE_PROFILE = "employee/profile";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String JOB_DETAIL = "job/{jobId}";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SKILLS = "employee/skills";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SKILL_ADD = "employee/skills/add";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CV_BUILDER = "employee/cv-builder/{startTab}";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String LEARNING = "employee/learning";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String LEARNING_DETAIL = "employee/learning/{moduleId}";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SAVED_JOBS = "employee/saved";
    @org.jetbrains.annotations.NotNull()
    public static final com.microcareer.match.presentation.navigation.Routes INSTANCE = null;
    
    private Routes() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String editJob(@org.jetbrains.annotations.NotNull()
    java.lang.String jobId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String jobDetail(@org.jetbrains.annotations.NotNull()
    java.lang.String jobId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String cvBuilder(int startTab) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String learningDetail(@org.jetbrains.annotations.NotNull()
    java.lang.String moduleId) {
        return null;
    }
}