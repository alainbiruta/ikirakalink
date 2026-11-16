package com.microcareer.match.data.repository;

import com.microcareer.match.data.local.TokenManager;
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
public final class UserRepository_Factory implements Factory<UserRepository> {
  private final Provider<AuthApiService> authApiServiceProvider;

  private final Provider<UserDao> userDaoProvider;

  private final Provider<TokenManager> tokenManagerProvider;

  public UserRepository_Factory(Provider<AuthApiService> authApiServiceProvider,
      Provider<UserDao> userDaoProvider, Provider<TokenManager> tokenManagerProvider) {
    this.authApiServiceProvider = authApiServiceProvider;
    this.userDaoProvider = userDaoProvider;
    this.tokenManagerProvider = tokenManagerProvider;
  }

  @Override
  public UserRepository get() {
    return newInstance(authApiServiceProvider.get(), userDaoProvider.get(), tokenManagerProvider.get());
  }

  public static UserRepository_Factory create(Provider<AuthApiService> authApiServiceProvider,
      Provider<UserDao> userDaoProvider, Provider<TokenManager> tokenManagerProvider) {
    return new UserRepository_Factory(authApiServiceProvider, userDaoProvider, tokenManagerProvider);
  }

  public static UserRepository newInstance(AuthApiService authApiService, UserDao userDao,
      TokenManager tokenManager) {
    return new UserRepository(authApiService, userDao, tokenManager);
  }
}
