package com.microcareer.match.presentation.job;

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
public final class JobViewModel_Factory implements Factory<JobViewModel> {
  private final Provider<JobRepository> jobRepositoryProvider;

  public JobViewModel_Factory(Provider<JobRepository> jobRepositoryProvider) {
    this.jobRepositoryProvider = jobRepositoryProvider;
  }

  @Override
  public JobViewModel get() {
    return newInstance(jobRepositoryProvider.get());
  }

  public static JobViewModel_Factory create(Provider<JobRepository> jobRepositoryProvider) {
    return new JobViewModel_Factory(jobRepositoryProvider);
  }

  public static JobViewModel newInstance(JobRepository jobRepository) {
    return new JobViewModel(jobRepository);
  }
}
