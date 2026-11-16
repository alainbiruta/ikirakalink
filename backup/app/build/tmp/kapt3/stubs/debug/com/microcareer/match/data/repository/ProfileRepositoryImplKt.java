package com.microcareer.match.data.repository;

import android.util.Log;
import com.microcareer.match.data.local.TokenManager;
import com.microcareer.match.data.local.dao.UserDao;
import com.microcareer.match.data.local.entity.UserEntity;
import com.microcareer.match.data.remote.api.AuthApiService;
import com.microcareer.match.data.remote.models.SkillsUpdateRequest;
import com.microcareer.match.domain.model.UserProfile;
import com.microcareer.match.domain.repository.ProfileRepository;
import com.microcareer.match.data.remote.models.UpdateProfileRequest;
import com.microcareer.match.utils.NetworkMonitor;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;
import javax.inject.Singleton;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0002\u001a\u00020\u0003*\u00020\u0004H\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"TAG", "", "toUserProfile", "Lcom/microcareer/match/domain/model/UserProfile;", "Lcom/microcareer/match/data/local/entity/UserEntity;", "app_debug"})
public final class ProfileRepositoryImplKt {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "ProfileRepository";
    
    private static final com.microcareer.match.domain.model.UserProfile toUserProfile(com.microcareer.match.data.local.entity.UserEntity $this$toUserProfile) {
        return null;
    }
}