package com.microcareer.match.data.repository;

import com.google.gson.Gson;
import com.microcareer.match.data.local.dao.LearningModuleDao;
import com.microcareer.match.data.remote.api.LearningApiService;
import com.microcareer.match.utils.NetworkMonitor;
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
public final class LearningRepositoryImpl_Factory implements Factory<LearningRepositoryImpl> {
  private final Provider<LearningModuleDao> learningModuleDaoProvider;

  private final Provider<LearningApiService> learningApiServiceProvider;

  private final Provider<NetworkMonitor> networkMonitorProvider;

  private final Provider<Gson> gsonProvider;

  public LearningRepositoryImpl_Factory(Provider<LearningModuleDao> learningModuleDaoProvider,
      Provider<LearningApiService> learningApiServiceProvider,
      Provider<NetworkMonitor> networkMonitorProvider, Provider<Gson> gsonProvider) {
    this.learningModuleDaoProvider = learningModuleDaoProvider;
    this.learningApiServiceProvider = learningApiServiceProvider;
    this.networkMonitorProvider = networkMonitorProvider;
    this.gsonProvider = gsonProvider;
  }

  @Override
  public LearningRepositoryImpl get() {
    return newInstance(learningModuleDaoProvider.get(), learningApiServiceProvider.get(), networkMonitorProvider.get(), gsonProvider.get());
  }

  public static LearningRepositoryImpl_Factory create(
      Provider<LearningModuleDao> learningModuleDaoProvider,
      Provider<LearningApiService> learningApiServiceProvider,
      Provider<NetworkMonitor> networkMonitorProvider, Provider<Gson> gsonProvider) {
    return new LearningRepositoryImpl_Factory(learningModuleDaoProvider, learningApiServiceProvider, networkMonitorProvider, gsonProvider);
  }

  public static LearningRepositoryImpl newInstance(LearningModuleDao learningModuleDao,
      LearningApiService learningApiService, NetworkMonitor networkMonitor, Gson gson) {
    return new LearningRepositoryImpl(learningModuleDao, learningApiService, networkMonitor, gson);
  }
}
