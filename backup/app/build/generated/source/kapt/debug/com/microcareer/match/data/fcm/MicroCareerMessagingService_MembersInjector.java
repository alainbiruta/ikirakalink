package com.microcareer.match.data.fcm;

import com.microcareer.match.data.local.FcmNotificationStore;
import com.microcareer.match.data.local.TokenManager;
import com.microcareer.match.data.remote.api.AuthApiService;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class MicroCareerMessagingService_MembersInjector implements MembersInjector<MicroCareerMessagingService> {
  private final Provider<TokenManager> tokenManagerProvider;

  private final Provider<AuthApiService> authApiServiceProvider;

  private final Provider<FcmNotificationStore> notificationStoreProvider;

  public MicroCareerMessagingService_MembersInjector(Provider<TokenManager> tokenManagerProvider,
      Provider<AuthApiService> authApiServiceProvider,
      Provider<FcmNotificationStore> notificationStoreProvider) {
    this.tokenManagerProvider = tokenManagerProvider;
    this.authApiServiceProvider = authApiServiceProvider;
    this.notificationStoreProvider = notificationStoreProvider;
  }

  public static MembersInjector<MicroCareerMessagingService> create(
      Provider<TokenManager> tokenManagerProvider, Provider<AuthApiService> authApiServiceProvider,
      Provider<FcmNotificationStore> notificationStoreProvider) {
    return new MicroCareerMessagingService_MembersInjector(tokenManagerProvider, authApiServiceProvider, notificationStoreProvider);
  }

  @Override
  public void injectMembers(MicroCareerMessagingService instance) {
    injectTokenManager(instance, tokenManagerProvider.get());
    injectAuthApiService(instance, authApiServiceProvider.get());
    injectNotificationStore(instance, notificationStoreProvider.get());
  }

  @InjectedFieldSignature("com.microcareer.match.data.fcm.MicroCareerMessagingService.tokenManager")
  public static void injectTokenManager(MicroCareerMessagingService instance,
      TokenManager tokenManager) {
    instance.tokenManager = tokenManager;
  }

  @InjectedFieldSignature("com.microcareer.match.data.fcm.MicroCareerMessagingService.authApiService")
  public static void injectAuthApiService(MicroCareerMessagingService instance,
      AuthApiService authApiService) {
    instance.authApiService = authApiService;
  }

  @InjectedFieldSignature("com.microcareer.match.data.fcm.MicroCareerMessagingService.notificationStore")
  public static void injectNotificationStore(MicroCareerMessagingService instance,
      FcmNotificationStore notificationStore) {
    instance.notificationStore = notificationStore;
  }
}
