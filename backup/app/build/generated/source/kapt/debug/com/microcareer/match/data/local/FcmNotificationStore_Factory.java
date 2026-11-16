package com.microcareer.match.data.local;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class FcmNotificationStore_Factory implements Factory<FcmNotificationStore> {
  private final Provider<Context> contextProvider;

  private final Provider<TokenManager> tokenManagerProvider;

  public FcmNotificationStore_Factory(Provider<Context> contextProvider,
      Provider<TokenManager> tokenManagerProvider) {
    this.contextProvider = contextProvider;
    this.tokenManagerProvider = tokenManagerProvider;
  }

  @Override
  public FcmNotificationStore get() {
    return newInstance(contextProvider.get(), tokenManagerProvider.get());
  }

  public static FcmNotificationStore_Factory create(Provider<Context> contextProvider,
      Provider<TokenManager> tokenManagerProvider) {
    return new FcmNotificationStore_Factory(contextProvider, tokenManagerProvider);
  }

  public static FcmNotificationStore newInstance(Context context, TokenManager tokenManager) {
    return new FcmNotificationStore(context, tokenManager);
  }
}
