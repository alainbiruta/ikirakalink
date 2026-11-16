package com.microcareer.match.di;

import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
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
public final class TokenProvider_Factory implements Factory<TokenProvider> {
  private final Provider<DataStore<Preferences>> dataStoreProvider;

  public TokenProvider_Factory(Provider<DataStore<Preferences>> dataStoreProvider) {
    this.dataStoreProvider = dataStoreProvider;
  }

  @Override
  public TokenProvider get() {
    return newInstance(dataStoreProvider.get());
  }

  public static TokenProvider_Factory create(Provider<DataStore<Preferences>> dataStoreProvider) {
    return new TokenProvider_Factory(dataStoreProvider);
  }

  public static TokenProvider newInstance(DataStore<Preferences> dataStore) {
    return new TokenProvider(dataStore);
  }
}
