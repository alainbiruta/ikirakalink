package com.microcareer.match.data.repository;

import com.microcareer.match.data.local.FcmNotificationStore;
import com.microcareer.match.data.local.TokenManager;
import com.microcareer.match.data.local.dao.JobDao;
import com.microcareer.match.data.local.dao.UserDao;
import com.microcareer.match.data.remote.api.AuthApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class AuthRepository_Factory implements Factory<AuthRepository> {
  private final Provider<AuthApiService> authApiServiceProvider;

  private final Provider<UserDao> userDaoProvider;

  private final Provider<TokenManager> tokenManagerProvider;

  private final Provider<JobDao> jobDaoProvider;

  private final Provider<FcmNotificationStore> fcmNotificationStoreProvider;

  public AuthRepository_Factory(Provider<AuthApiService> authApiServiceProvider,
      Provider<UserDao> userDaoProvider, Provider<TokenManager> tokenManagerProvider,
      Provider<JobDao> jobDaoProvider,
      Provider<FcmNotificationStore> fcmNotificationStoreProvider) {
    this.authApiServiceProvider = authApiServiceProvider;
    this.userDaoProvider = userDaoProvider;
    this.tokenManagerProvider = tokenManagerProvider;
    this.jobDaoProvider = jobDaoProvider;
    this.fcmNotificationStoreProvider = fcmNotificationStoreProvider;
  }

  @Override
  public AuthRepository get() {
    return newInstance(authApiServiceProvider.get(), userDaoProvider.get(), tokenManagerProvider.get(), jobDaoProvider.get(), fcmNotificationStoreProvider.get());
  }

  public static AuthRepository_Factory create(Provider<AuthApiService> authApiServiceProvider,
      Provider<UserDao> userDaoProvider, Provider<TokenManager> tokenManagerProvider,
      Provider<JobDao> jobDaoProvider,
      Provider<FcmNotificationStore> fcmNotificationStoreProvider) {
    return new AuthRepository_Factory(authApiServiceProvider, userDaoProvider, tokenManagerProvider, jobDaoProvider, fcmNotificationStoreProvider);
  }

  public static AuthRepository newInstance(AuthApiService authApiService, UserDao userDao,
      TokenManager tokenManager, JobDao jobDao, FcmNotificationStore fcmNotificationStore) {
    return new AuthRepository(authApiService, userDao, tokenManager, jobDao, fcmNotificationStore);
  }
}
