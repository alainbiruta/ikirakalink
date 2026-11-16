package com.microcareer.match.data.repository;

import android.util.Log;
import com.microcareer.match.data.local.TokenManager;
import com.microcareer.match.data.local.dao.UserDao;
import com.microcareer.match.data.local.entity.UserEntity;
import com.microcareer.match.data.remote.api.AuthApiService;
import com.microcareer.match.data.remote.models.SkillsUpdateRequest;
import com.microcareer.match.domain.repository.UserRepository;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;
import javax.inject.Singleton;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u000e\n\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0002"}, d2 = {"TAG", "", "app_debug"})
public final class UserRepositoryKt {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "UserRepository";
}