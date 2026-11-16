package com.microcareer.match.presentation.home;

import com.microcareer.match.data.local.FcmNotificationStore;
import com.microcareer.match.data.repository.JobRepository;
import com.microcareer.match.utils.NetworkMonitor;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<JobRepository> jobRepositoryProvider;

  private final Provider<NetworkMonitor> networkMonitorProvider;

  private final Provider<FcmNotificationStore> notificationStoreProvider;

  public HomeViewModel_Factory(Provider<JobRepository> jobRepositoryProvider,
      Provider<NetworkMonitor> networkMonitorProvider,
      Provider<FcmNotificationStore> notificationStoreProvider) {
    this.jobRepositoryProvider = jobRepositoryProvider;
    this.networkMonitorProvider = networkMonitorProvider;
    this.notificationStoreProvider = notificationStoreProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(jobRepositoryProvider.get(), networkMonitorProvider.get(), notificationStoreProvider.get());
  }

  public static HomeViewModel_Factory create(Provider<JobRepository> jobRepositoryProvider,
      Provider<NetworkMonitor> networkMonitorProvider,
      Provider<FcmNotificationStore> notificationStoreProvider) {
    return new HomeViewModel_Factory(jobRepositoryProvider, networkMonitorProvider, notificationStoreProvider);
  }

  public static HomeViewModel newInstance(JobRepository jobRepository,
      NetworkMonitor networkMonitor, FcmNotificationStore notificationStore) {
    return new HomeViewModel(jobRepository, networkMonitor, notificationStore);
  }
}
