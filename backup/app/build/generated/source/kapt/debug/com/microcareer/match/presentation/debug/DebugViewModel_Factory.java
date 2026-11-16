package com.microcareer.match.presentation.debug;

import com.microcareer.match.data.local.seeder.DatabaseSeeder;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class DebugViewModel_Factory implements Factory<DebugViewModel> {
  private final Provider<DatabaseSeeder> databaseSeederProvider;

  public DebugViewModel_Factory(Provider<DatabaseSeeder> databaseSeederProvider) {
    this.databaseSeederProvider = databaseSeederProvider;
  }

  @Override
  public DebugViewModel get() {
    return newInstance(databaseSeederProvider.get());
  }

  public static DebugViewModel_Factory create(Provider<DatabaseSeeder> databaseSeederProvider) {
    return new DebugViewModel_Factory(databaseSeederProvider);
  }

  public static DebugViewModel newInstance(DatabaseSeeder databaseSeeder) {
    return new DebugViewModel(databaseSeeder);
  }
}
