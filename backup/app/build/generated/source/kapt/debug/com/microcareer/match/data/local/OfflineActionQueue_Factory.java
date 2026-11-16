package com.microcareer.match.data.local;

import com.microcareer.match.data.local.dao.OfflinePendingActionDao;
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
public final class OfflineActionQueue_Factory implements Factory<OfflineActionQueue> {
  private final Provider<OfflinePendingActionDao> daoProvider;

  public OfflineActionQueue_Factory(Provider<OfflinePendingActionDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public OfflineActionQueue get() {
    return newInstance(daoProvider.get());
  }

  public static OfflineActionQueue_Factory create(Provider<OfflinePendingActionDao> daoProvider) {
    return new OfflineActionQueue_Factory(daoProvider);
  }

  public static OfflineActionQueue newInstance(OfflinePendingActionDao dao) {
    return new OfflineActionQueue(dao);
  }
}
