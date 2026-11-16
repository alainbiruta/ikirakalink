package com.microcareer.match.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.microcareer.match.data.local.OfflineActionQueue;
import com.microcareer.match.data.local.TokenManager;
import com.microcareer.match.data.local.dao.JobDao;
import com.microcareer.match.data.remote.api.ApplicationsApiService;
import com.microcareer.match.data.repository.UserRepository;
import dagger.internal.DaggerGenerated;
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
public final class SyncWorker_Factory {
  private final Provider<OfflineActionQueue> offlineActionQueueProvider;

  private final Provider<ApplicationsApiService> applicationsApiServiceProvider;

  private final Provider<JobDao> jobDaoProvider;

  private final Provider<TokenManager> tokenManagerProvider;

  private final Provider<UserRepository> userRepositoryProvider;

  public SyncWorker_Factory(Provider<OfflineActionQueue> offlineActionQueueProvider,
      Provider<ApplicationsApiService> applicationsApiServiceProvider,
      Provider<JobDao> jobDaoProvider, Provider<TokenManager> tokenManagerProvider,
      Provider<UserRepository> userRepositoryProvider) {
    this.offlineActionQueueProvider = offlineActionQueueProvider;
    this.applicationsApiServiceProvider = applicationsApiServiceProvider;
    this.jobDaoProvider = jobDaoProvider;
    this.tokenManagerProvider = tokenManagerProvider;
    this.userRepositoryProvider = userRepositoryProvider;
  }

  public SyncWorker get(Context context, WorkerParameters params) {
    return newInstance(context, params, offlineActionQueueProvider.get(), applicationsApiServiceProvider.get(), jobDaoProvider.get(), tokenManagerProvider.get(), userRepositoryProvider.get());
  }

  public static SyncWorker_Factory create(Provider<OfflineActionQueue> offlineActionQueueProvider,
      Provider<ApplicationsApiService> applicationsApiServiceProvider,
      Provider<JobDao> jobDaoProvider, Provider<TokenManager> tokenManagerProvider,
      Provider<UserRepository> userRepositoryProvider) {
    return new SyncWorker_Factory(offlineActionQueueProvider, applicationsApiServiceProvider, jobDaoProvider, tokenManagerProvider, userRepositoryProvider);
  }

  public static SyncWorker newInstance(Context context, WorkerParameters params,
      OfflineActionQueue offlineActionQueue, ApplicationsApiService applicationsApiService,
      JobDao jobDao, TokenManager tokenManager, UserRepository userRepository) {
    return new SyncWorker(context, params, offlineActionQueue, applicationsApiService, jobDao, tokenManager, userRepository);
  }
}
