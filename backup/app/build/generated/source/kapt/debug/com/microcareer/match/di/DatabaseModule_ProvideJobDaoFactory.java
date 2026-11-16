package com.microcareer.match.di;

import com.microcareer.match.data.local.dao.JobDao;
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
public final class DatabaseModule_ProvideJobDaoFactory implements Factory<JobDao> {
  private final Provider<MicroCareerDatabase> databaseProvider;

  public DatabaseModule_ProvideJobDaoFactory(Provider<MicroCareerDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public JobDao get() {
    return provideJobDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideJobDaoFactory create(
      Provider<MicroCareerDatabase> databaseProvider) {
    return new DatabaseModule_ProvideJobDaoFactory(databaseProvider);
  }

  public static JobDao provideJobDao(MicroCareerDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideJobDao(database));
  }
}
