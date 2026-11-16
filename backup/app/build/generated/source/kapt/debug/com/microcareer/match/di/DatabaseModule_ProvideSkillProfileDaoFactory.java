package com.microcareer.match.di;

import com.microcareer.match.data.local.dao.SkillProfileDao;
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
public final class DatabaseModule_ProvideSkillProfileDaoFactory implements Factory<SkillProfileDao> {
  private final Provider<MicroCareerDatabase> databaseProvider;

  public DatabaseModule_ProvideSkillProfileDaoFactory(
      Provider<MicroCareerDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public SkillProfileDao get() {
    return provideSkillProfileDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideSkillProfileDaoFactory create(
      Provider<MicroCareerDatabase> databaseProvider) {
    return new DatabaseModule_ProvideSkillProfileDaoFactory(databaseProvider);
  }

  public static SkillProfileDao provideSkillProfileDao(MicroCareerDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideSkillProfileDao(database));
  }
}
