package com.microcareer.match.data.repository;

import com.microcareer.match.data.local.OfflineActionQueue;
import com.microcareer.match.data.local.TokenManager;
import com.microcareer.match.data.local.dao.JobDao;
import com.microcareer.match.data.remote.api.ApplicationsApiService;
import com.microcareer.match.data.remote.api.JobsApiService;
import com.microcareer.match.utils.NetworkMonitor;
import com.microcareer.match.worker.SyncScheduler;
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
public final class JobRepository_Factory implements Factory<JobRepository> {
  private final Provider<JobsApiService> jobsApiServiceProvider;

  private final Provider<ApplicationsApiService> applicationsApiServiceProvider;

  private final Provider<JobDao> jobDaoProvider;

  private final Provider<OfflineActionQueue> offlineActionQueueProvider;

  private final Provider<NetworkMonitor> networkMonitorProvider;

  private final Provider<SyncScheduler> syncSchedulerProvider;

  private final Provider<TokenManager> tokenManagerProvider;

  public JobRepository_Factory(Provider<JobsApiService> jobsApiServiceProvider,
      Provider<ApplicationsApiService> applicationsApiServiceProvider,
      Provider<JobDao> jobDaoProvider, Provider<OfflineActionQueue> offlineActionQueueProvider,
      Provider<NetworkMonitor> networkMonitorProvider,
      Provider<SyncScheduler> syncSchedulerProvider, Provider<TokenManager> tokenManagerProvider) {
    this.jobsApiServiceProvider = jobsApiServiceProvider;
    this.applicationsApiServiceProvider = applicationsApiServiceProvider;
    this.jobDaoProvider = jobDaoProvider;
    this.offlineActionQueueProvider = offlineActionQueueProvider;
    this.networkMonitorProvider = networkMonitorProvider;
    this.syncSchedulerProvider = syncSchedulerProvider;
    this.tokenManagerProvider = tokenManagerProvider;
  }

  @Override
  public JobRepository get() {
    return newInstance(jobsApiServiceProvider.get(), applicationsApiServiceProvider.get(), jobDaoProvider.get(), offlineActionQueueProvider.get(), networkMonitorProvider.get(), syncSchedulerProvider.get(), tokenManagerProvider.get());
  }

  public static JobRepository_Factory create(Provider<JobsApiService> jobsApiServiceProvider,
      Provider<ApplicationsApiService> applicationsApiServiceProvider,
      Provider<JobDao> jobDaoProvider, Provider<OfflineActionQueue> offlineActionQueueProvider,
      Provider<NetworkMonitor> networkMonitorProvider,
      Provider<SyncScheduler> syncSchedulerProvider, Provider<TokenManager> tokenManagerProvider) {
    return new JobRepository_Factory(jobsApiServiceProvider, applicationsApiServiceProvider, jobDaoProvider, offlineActionQueueProvider, networkMonitorProvider, syncSchedulerProvider, tokenManagerProvider);
  }

  public static JobRepository newInstance(JobsApiService jobsApiService,
      ApplicationsApiService applicationsApiService, JobDao jobDao,
      OfflineActionQueue offlineActionQueue, NetworkMonitor networkMonitor,
      SyncScheduler syncScheduler, TokenManager tokenManager) {
    return new JobRepository(jobsApiService, applicationsApiService, jobDao, offlineActionQueue, networkMonitor, syncScheduler, tokenManager);
  }
}
