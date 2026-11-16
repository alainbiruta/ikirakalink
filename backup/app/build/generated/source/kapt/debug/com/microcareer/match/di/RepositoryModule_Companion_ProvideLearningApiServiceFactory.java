package com.microcareer.match.di;

import com.microcareer.match.data.remote.api.LearningApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

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
public final class RepositoryModule_Companion_ProvideLearningApiServiceFactory implements Factory<LearningApiService> {
  private final Provider<Retrofit> retrofitProvider;

  public RepositoryModule_Companion_ProvideLearningApiServiceFactory(
      Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public LearningApiService get() {
    return provideLearningApiService(retrofitProvider.get());
  }

  public static RepositoryModule_Companion_ProvideLearningApiServiceFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new RepositoryModule_Companion_ProvideLearningApiServiceFactory(retrofitProvider);
  }

  public static LearningApiService provideLearningApiService(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(RepositoryModule.Companion.provideLearningApiService(retrofit));
  }
}
