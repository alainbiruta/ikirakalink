package com.microcareer.match.di;

import com.microcareer.match.data.remote.api.MicroCareerApiService;
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
public final class NetworkModule_ProvideMicroCareerApiServiceFactory implements Factory<MicroCareerApiService> {
  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_ProvideMicroCareerApiServiceFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public MicroCareerApiService get() {
    return provideMicroCareerApiService(retrofitProvider.get());
  }

  public static NetworkModule_ProvideMicroCareerApiServiceFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvideMicroCareerApiServiceFactory(retrofitProvider);
  }

  public static MicroCareerApiService provideMicroCareerApiService(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideMicroCareerApiService(retrofit));
  }
}
