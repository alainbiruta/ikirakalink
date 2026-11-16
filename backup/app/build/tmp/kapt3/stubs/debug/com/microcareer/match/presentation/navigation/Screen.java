package com.microcareer.match.presentation.navigation;

import androidx.navigation.NavType;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u000b\u0007\b\t\n\u000b\f\r\u000e\u000f\u0010\u0011B\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u000b\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u00a8\u0006\u001d"}, d2 = {"Lcom/microcareer/match/presentation/navigation/Screen;", "", "route", "", "(Ljava/lang/String;)V", "getRoute", "()Ljava/lang/String;", "AuthGraph", "CvBuilder", "Home", "JobDetails", "Learn", "LearningModuleDetail", "Login", "MainGraph", "Profile", "Register", "SkillInput", "Lcom/microcareer/match/presentation/navigation/Screen$AuthGraph;", "Lcom/microcareer/match/presentation/navigation/Screen$CvBuilder;", "Lcom/microcareer/match/presentation/navigation/Screen$Home;", "Lcom/microcareer/match/presentation/navigation/Screen$JobDetails;", "Lcom/microcareer/match/presentation/navigation/Screen$Learn;", "Lcom/microcareer/match/presentation/navigation/Screen$LearningModuleDetail;", "Lcom/microcareer/match/presentation/navigation/Screen$Login;", "Lcom/microcareer/match/presentation/navigation/Screen$MainGraph;", "Lcom/microcareer/match/presentation/navigation/Screen$Profile;", "Lcom/microcareer/match/presentation/navigation/Screen$Register;", "Lcom/microcareer/match/presentation/navigation/Screen$SkillInput;", "app_debug"})
public abstract class Screen {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String route = null;
    
    private Screen(java.lang.String route) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRoute() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/microcareer/match/presentation/navigation/Screen$AuthGraph;", "Lcom/microcareer/match/presentation/navigation/Screen;", "()V", "app_debug"})
    public static final class AuthGraph extends com.microcareer.match.presentation.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.microcareer.match.presentation.navigation.Screen.AuthGraph INSTANCE = null;
        
        private AuthGraph() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000f\u001a\u00020\u00042\b\b\u0002\u0010\u0010\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0011"}, d2 = {"Lcom/microcareer/match/presentation/navigation/Screen$CvBuilder;", "Lcom/microcareer/match/presentation/navigation/Screen;", "()V", "ARG_START_TAB", "", "TAB_EDUCATION", "", "TAB_EXPERIENCE", "TAB_PROFILE", "TAB_SKILLS", "arguments", "", "Landroidx/navigation/NamedNavArgument;", "getArguments", "()Ljava/util/List;", "createRoute", "startTab", "app_debug"})
    public static final class CvBuilder extends com.microcareer.match.presentation.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String ARG_START_TAB = "startTab";
        public static final int TAB_PROFILE = 0;
        public static final int TAB_SKILLS = 1;
        public static final int TAB_EXPERIENCE = 2;
        public static final int TAB_EDUCATION = 3;
        @org.jetbrains.annotations.NotNull()
        private static final java.util.List<androidx.navigation.NamedNavArgument> arguments = null;
        @org.jetbrains.annotations.NotNull()
        public static final com.microcareer.match.presentation.navigation.Screen.CvBuilder INSTANCE = null;
        
        private CvBuilder() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<androidx.navigation.NamedNavArgument> getArguments() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String createRoute(int startTab) {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/microcareer/match/presentation/navigation/Screen$Home;", "Lcom/microcareer/match/presentation/navigation/Screen;", "()V", "app_debug"})
    public static final class Home extends com.microcareer.match.presentation.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.microcareer.match.presentation.navigation.Screen.Home INSTANCE = null;
        
        private Home() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u00a8\u0006\f"}, d2 = {"Lcom/microcareer/match/presentation/navigation/Screen$JobDetails;", "Lcom/microcareer/match/presentation/navigation/Screen;", "()V", "ARG_JOB_ID", "", "arguments", "", "Landroidx/navigation/NamedNavArgument;", "getArguments", "()Ljava/util/List;", "createRoute", "jobId", "app_debug"})
    public static final class JobDetails extends com.microcareer.match.presentation.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String ARG_JOB_ID = "jobId";
        @org.jetbrains.annotations.NotNull()
        private static final java.util.List<androidx.navigation.NamedNavArgument> arguments = null;
        @org.jetbrains.annotations.NotNull()
        public static final com.microcareer.match.presentation.navigation.Screen.JobDetails INSTANCE = null;
        
        private JobDetails() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String createRoute(@org.jetbrains.annotations.NotNull()
        java.lang.String jobId) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<androidx.navigation.NamedNavArgument> getArguments() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/microcareer/match/presentation/navigation/Screen$Learn;", "Lcom/microcareer/match/presentation/navigation/Screen;", "()V", "app_debug"})
    public static final class Learn extends com.microcareer.match.presentation.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.microcareer.match.presentation.navigation.Screen.Learn INSTANCE = null;
        
        private Learn() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004\u00a8\u0006\u0006"}, d2 = {"Lcom/microcareer/match/presentation/navigation/Screen$LearningModuleDetail;", "Lcom/microcareer/match/presentation/navigation/Screen;", "()V", "createRoute", "", "moduleId", "app_debug"})
    public static final class LearningModuleDetail extends com.microcareer.match.presentation.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.microcareer.match.presentation.navigation.Screen.LearningModuleDetail INSTANCE = null;
        
        private LearningModuleDetail() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String createRoute(@org.jetbrains.annotations.NotNull()
        java.lang.String moduleId) {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/microcareer/match/presentation/navigation/Screen$Login;", "Lcom/microcareer/match/presentation/navigation/Screen;", "()V", "app_debug"})
    public static final class Login extends com.microcareer.match.presentation.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.microcareer.match.presentation.navigation.Screen.Login INSTANCE = null;
        
        private Login() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/microcareer/match/presentation/navigation/Screen$MainGraph;", "Lcom/microcareer/match/presentation/navigation/Screen;", "()V", "app_debug"})
    public static final class MainGraph extends com.microcareer.match.presentation.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.microcareer.match.presentation.navigation.Screen.MainGraph INSTANCE = null;
        
        private MainGraph() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/microcareer/match/presentation/navigation/Screen$Profile;", "Lcom/microcareer/match/presentation/navigation/Screen;", "()V", "app_debug"})
    public static final class Profile extends com.microcareer.match.presentation.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.microcareer.match.presentation.navigation.Screen.Profile INSTANCE = null;
        
        private Profile() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/microcareer/match/presentation/navigation/Screen$Register;", "Lcom/microcareer/match/presentation/navigation/Screen;", "()V", "app_debug"})
    public static final class Register extends com.microcareer.match.presentation.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.microcareer.match.presentation.navigation.Screen.Register INSTANCE = null;
        
        private Register() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/microcareer/match/presentation/navigation/Screen$SkillInput;", "Lcom/microcareer/match/presentation/navigation/Screen;", "()V", "app_debug"})
    public static final class SkillInput extends com.microcareer.match.presentation.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.microcareer.match.presentation.navigation.Screen.SkillInput INSTANCE = null;
        
        private SkillInput() {
        }
    }
}