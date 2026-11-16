package com.microcareer.match.presentation.employer;

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
public final class PostJobViewModel_Factory implements Factory<PostJobViewModel> {
  private final Provider<JobRepository> jobRepositoryProvider;

  public PostJobViewModel_Factory(Provider<JobRepository> jobRepositoryProvider) {
    this.jobRepositoryProvider = jobRepositoryProvider;
  }

  @Override
  public PostJobViewModel get() {
    return newInstance(jobRepositoryProvider.get());
  }

  public static PostJobViewModel_Factory create(Provider<JobRepository> jobRepositoryProvider) {
    return new PostJobViewModel_Factory(jobRepositoryProvider);
  }

  public static PostJobViewModel newInstance(JobRepository jobRepository) {
    return new PostJobViewModel(jobRepository);
  }
}
