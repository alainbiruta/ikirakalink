package com.microcareer.match.di;

import com.microcareer.match.data.local.dao.LearningModuleDao;
import com.microcareer.match.data.local.database.MicroCareerDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideLearningModuleDaoFactory implements Factory<LearningModuleDao> {
  private final Provider<MicroCareerDatabase> databaseProvider;

  public DatabaseModule_ProvideLearningModuleDaoFactory(
      Provider<MicroCareerDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public LearningModuleDao get() {
    return provideLearningModuleDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideLearningModuleDaoFactory create(
      Provider<MicroCareerDatabase> databaseProvider) {
    return new DatabaseModule_ProvideLearningModuleDaoFactory(databaseProvider);
  }

  public static LearningModuleDao provideLearningModuleDao(MicroCareerDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideLearningModuleDao(database));
  }
}
