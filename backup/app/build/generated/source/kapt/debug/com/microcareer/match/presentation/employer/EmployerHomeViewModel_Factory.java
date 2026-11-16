package com.microcareer.match.presentation.employer;

import com.microcareer.match.data.local.FcmNotificationStore;
import com.microcareer.match.data.local.TokenManager;
import com.microcareer.match.data.repository.JobRepository;
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
public final class EmployerHomeViewModel_Factory implements Factory<EmployerHomeViewModel> {
  private final Provider<JobRepository> jobRepositoryProvider;

  private final Provider<TokenManager> tokenManagerProvider;

  private final Provider<FcmNotificationStore> notificationStoreProvider;

  public EmployerHomeViewModel_Factory(Provider<JobRepository> jobRepositoryProvider,
      Provider<TokenManager> tokenManagerProvider,
      Provider<FcmNotificationStore> notificationStoreProvider) {
    this.jobRepositoryProvider = jobRepositoryProvider;
    this.tokenManagerProvider = tokenManagerProvider;
    this.notificationStoreProvider = notificationStoreProvider;
  }

  @Override
  public EmployerHomeViewModel get() {
    return newInstance(jobRepositoryProvider.get(), tokenManagerProvider.get(), notificationStoreProvider.get());
  }

  public static EmployerHomeViewModel_Factory create(Provider<JobRepository> jobRepositoryProvider,
      Provider<TokenManager> tokenManagerProvider,
      Provider<FcmNotificationStore> notificationStoreProvider) {
    return new EmployerHomeViewModel_Factory(jobRepositoryProvider, tokenManagerProvider, notificationStoreProvider);
  }

  public static EmployerHomeViewModel newInstance(JobRepository jobRepository,
      TokenManager tokenManager, FcmNotificationStore notificationStore) {
    return new EmployerHomeViewModel(jobRepository, tokenManager, notificationStore);
  }
}
