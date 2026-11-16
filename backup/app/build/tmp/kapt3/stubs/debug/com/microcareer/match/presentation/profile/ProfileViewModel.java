package com.microcareer.match.presentation.profile;

import android.net.Uri;
import androidx.lifecycle.ViewModel;
import com.microcareer.match.domain.model.UserProfile;
import com.microcareer.match.domain.repository.ProfileRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import com.microcareer.match.data.remote.models.UpdateProfileRequest;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;
import com.microcareer.match.utils.LocaleHelper;
import android.content.Context;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0013\u001a\u00020\u0014J\u000e\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0017J\u0006\u0010\u0018\u001a\u00020\u0014J\b\u0010\u0019\u001a\u00020\u0014H\u0002J\u0016\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u001b\u001a\u00020\u001cJ\u000e\u0010\u001d\u001a\u00020\u00142\u0006\u0010\u001e\u001a\u00020\u001fJ\u000e\u0010 \u001a\u00020\u00142\u0006\u0010!\u001a\u00020\u001cJ\u000e\u0010\"\u001a\u00020\u00142\u0006\u0010#\u001a\u00020\u001cJ\u0016\u0010$\u001a\u00020\u00142\u0006\u0010%\u001a\u00020\u001c2\u0006\u0010&\u001a\u00020\u001cJ\u001e\u0010\'\u001a\u00020\u00142\u0006\u0010#\u001a\u00020\u001c2\u0006\u0010(\u001a\u00020\u001c2\u0006\u0010)\u001a\u00020\u001cJ\u0014\u0010*\u001a\u00020\u00142\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u001c0,J\u000e\u0010-\u001a\u00020\u00142\u0006\u0010.\u001a\u00020/R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\n0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\u00a8\u00060"}, d2 = {"Lcom/microcareer/match/presentation/profile/ProfileViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/microcareer/match/domain/repository/ProfileRepository;", "(Lcom/microcareer/match/domain/repository/ProfileRepository;)V", "_events", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "Lcom/microcareer/match/presentation/profile/ProfileEvent;", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/microcareer/match/presentation/profile/ProfileUiState;", "events", "Lkotlinx/coroutines/flow/SharedFlow;", "getEvents", "()Lkotlinx/coroutines/flow/SharedFlow;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "clearError", "", "loadSavedLanguage", "context", "Landroid/content/Context;", "logout", "observeProfile", "onLanguageSelected", "languageTag", "", "onNotificationsChanged", "enabled", "", "updateBio", "bio", "updateFullName", "name", "updatePassword", "currentPassword", "newPassword", "updatePersonalInfo", "phone", "location", "updateSkills", "skills", "", "uploadProfilePhoto", "uri", "Landroid/net/Uri;", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ProfileViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.domain.repository.ProfileRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.microcareer.match.presentation.profile.ProfileUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.microcareer.match.presentation.profile.ProfileUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableSharedFlow<com.microcareer.match.presentation.profile.ProfileEvent> _events = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.SharedFlow<com.microcareer.match.presentation.profile.ProfileEvent> events = null;
    
    @javax.inject.Inject()
    public ProfileViewModel(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.domain.repository.ProfileRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.microcareer.match.presentation.profile.ProfileUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<com.microcareer.match.presentation.profile.ProfileEvent> getEvents() {
        return null;
    }
    
    private final void observeProfile() {
    }
    
    public final void uploadProfilePhoto(@org.jetbrains.annotations.NotNull()
    android.net.Uri uri) {
    }
    
    public final void updatePersonalInfo(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String phone, @org.jetbrains.annotations.NotNull()
    java.lang.String location) {
    }
    
    public final void updateFullName(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
    }
    
    public final void updateBio(@org.jetbrains.annotations.NotNull()
    java.lang.String bio) {
    }
    
    public final void updateSkills(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> skills) {
    }
    
    public final void updatePassword(@org.jetbrains.annotations.NotNull()
    java.lang.String currentPassword, @org.jetbrains.annotations.NotNull()
    java.lang.String newPassword) {
    }
    
    public final void onNotificationsChanged(boolean enabled) {
    }
    
    public final void onLanguageSelected(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String languageTag) {
    }
    
    public final void loadSavedLanguage(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    public final void clearError() {
    }
    
    public final void logout() {
    }
}