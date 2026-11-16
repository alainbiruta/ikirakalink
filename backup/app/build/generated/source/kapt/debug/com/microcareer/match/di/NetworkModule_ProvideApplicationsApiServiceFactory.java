package com.microcareer.match.di;

import com.microcareer.match.data.remote.api.ApplicationsApiService;
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
public final class NetworkModule_ProvideApplicationsApiServiceFactory implements Factory<ApplicationsApiService> {
  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_ProvideApplicationsApiServiceFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public ApplicationsApiService get() {
    return provideApplicationsApiService(retrofitProvider.get());
  }

  public static NetworkModule_ProvideApplicationsApiServiceFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvideApplicationsApiServiceFactory(retrofitProvider);
  }

  public static ApplicationsApiService provideApplicationsApiService(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideApplicationsApiService(retrofit));
  }
}
