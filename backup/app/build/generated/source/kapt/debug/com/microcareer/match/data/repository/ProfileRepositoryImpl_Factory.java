package com.microcareer.match.data.repository;

import com.microcareer.match.data.local.TokenManager;
import com.microcareer.match.data.local.dao.UserDao;
import com.microcareer.match.data.remote.api.AuthApiService;
import com.microcareer.match.utils.NetworkMonitor;
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
public final class ProfileRepositoryImpl_Factory implements Factory<ProfileRepositoryImpl> {
  private final Provider<UserDao> userDaoProvider;

  private final Provider<TokenManager> tokenManagerProvider;

  private final Provider<AuthApiService> authApiServiceProvider;

  private final Provider<NetworkMonitor> networkMonitorProvider;

  public ProfileRepositoryImpl_Factory(Provider<UserDao> userDaoProvider,
      Provider<TokenManager> tokenManagerProvider, Provider<AuthApiService> authApiServiceProvider,
      Provider<NetworkMonitor> networkMonitorProvider) {
    this.userDaoProvider = userDaoProvider;
    this.tokenManagerProvider = tokenManagerProvider;
    this.authApiServiceProvider = authApiServiceProvider;
    this.networkMonitorProvider = networkMonitorProvider;
  }

  @Override
  public ProfileRepositoryImpl get() {
    return newInstance(userDaoProvider.get(), tokenManagerProvider.get(), authApiServiceProvider.get(), networkMonitorProvider.get());
  }

  public static ProfileRepositoryImpl_Factory create(Provider<UserDao> userDaoProvider,
      Provider<TokenManager> tokenManagerProvider, Provider<AuthApiService> authApiServiceProvider,
      Provider<NetworkMonitor> networkMonitorProvider) {
    return new ProfileRepositoryImpl_Factory(userDaoProvider, tokenManagerProvider, authApiServiceProvider, networkMonitorProvider);
  }

  public static ProfileRepositoryImpl newInstance(UserDao userDao, TokenManager tokenManager,
      AuthApiService authApiService, NetworkMonitor networkMonitor) {
    return new ProfileRepositoryImpl(userDao, tokenManager, authApiService, networkMonitor);
  }
}
