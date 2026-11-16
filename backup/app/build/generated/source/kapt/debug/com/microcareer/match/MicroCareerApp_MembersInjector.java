package com.microcareer.match;

import androidx.hilt.work.HiltWorkerFactory;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class MicroCareerApp_MembersInjector implements MembersInjector<MicroCareerApp> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  public MicroCareerApp_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
  }

  public static MembersInjector<MicroCareerApp> create(
      Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new MicroCareerApp_MembersInjector(workerFactoryProvider);
  }

  @Override
  public void injectMembers(MicroCareerApp instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
  }

  @InjectedFieldSignature("com.microcareer.match.MicroCareerApp.workerFactory")
  public static void injectWorkerFactory(MicroCareerApp instance, HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }
}
