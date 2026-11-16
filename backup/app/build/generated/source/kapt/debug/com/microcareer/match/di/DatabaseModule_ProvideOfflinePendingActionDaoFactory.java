package com.microcareer.match.di;

import com.microcareer.match.data.local.dao.OfflinePendingActionDao;
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
public final class DatabaseModule_ProvideOfflinePendingActionDaoFactory implements Factory<OfflinePendingActionDao> {
  private final Provider<MicroCareerDatabase> databaseProvider;

  public DatabaseModule_ProvideOfflinePendingActionDaoFactory(
      Provider<MicroCareerDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public OfflinePendingActionDao get() {
    return provideOfflinePendingActionDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideOfflinePendingActionDaoFactory create(
      Provider<MicroCareerDatabase> databaseProvider) {
    return new DatabaseModule_ProvideOfflinePendingActionDaoFactory(databaseProvider);
  }

  public static OfflinePendingActionDao provideOfflinePendingActionDao(
      MicroCareerDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideOfflinePendingActionDao(database));
  }
}
