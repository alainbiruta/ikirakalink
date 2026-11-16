package com.microcareer.match.di;

import com.microcareer.match.data.remote.api.JobsApiService;
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
public final class NetworkModule_ProvideJobsApiServiceFactory implements Factory<JobsApiService> {
  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_ProvideJobsApiServiceFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public JobsApiService get() {
    return provideJobsApiService(retrofitProvider.get());
  }

  public static NetworkModule_ProvideJobsApiServiceFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvideJobsApiServiceFactory(retrofitProvider);
  }

  public static JobsApiService provideJobsApiService(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideJobsApiService(retrofit));
  }
}
