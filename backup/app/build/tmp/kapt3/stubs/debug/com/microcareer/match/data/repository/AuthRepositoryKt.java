package com.microcareer.match.data.repository;

import android.util.Log;
import com.google.firebase.messaging.FirebaseMessaging;
import com.microcareer.match.data.local.TokenManager;
import com.microcareer.match.data.local.dao.UserDao;
import com.microcareer.match.data.local.entity.UserEntity;
import com.microcareer.match.data.remote.api.AuthApiService;
import com.microcareer.match.data.remote.models.FcmTokenRequest;
import com.microcareer.match.data.remote.models.RegisterRequest;
import com.microcareer.match.data.remote.models.RemoteUser;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;
import javax.inject.Singleton;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\n\u0010\u0002\u001a\u00020\u0003*\u00020\u0004\u001a\u0014\u0010\u0005\u001a\u00020\u0003*\u00020\u00042\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"TAG", "", "toEntity", "Lcom/microcareer/match/data/local/entity/UserEntity;", "Lcom/microcareer/match/data/remote/models/RemoteUser;", "toEntityPreservingSkills", "existing", "app_debug"})
public final class AuthRepositoryKt {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "AuthRepository";
    
    /**
     * Skill resolution priority:
     * 1. Server skills if non-empty (synced from another device)
     * 2. Local Room skills if non-empty (saved offline, server lagging)
     * 3. Empty list (brand new user)
     */
    @org.jetbrains.annotations.NotNull()
    public static final com.microcareer.match.data.local.entity.UserEntity toEntityPreservingSkills(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.remote.models.RemoteUser $this$toEntityPreservingSkills, @org.jetbrains.annotations.Nullable()
    com.microcareer.match.data.local.entity.UserEntity existing) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final com.microcareer.match.data.local.entity.UserEntity toEntity(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.remote.models.RemoteUser $this$toEntity) {
        return null;
    }
}