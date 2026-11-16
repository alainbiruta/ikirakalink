package com.microcareer.match.presentation.skill;

import com.microcareer.match.domain.repository.ProfileRepository;
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
public final class SkillViewModel_Factory implements Factory<SkillViewModel> {
  private final Provider<ProfileRepository> repositoryProvider;

  public SkillViewModel_Factory(Provider<ProfileRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SkillViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static SkillViewModel_Factory create(Provider<ProfileRepository> repositoryProvider) {
    return new SkillViewModel_Factory(repositoryProvider);
  }

  public static SkillViewModel newInstance(ProfileRepository repository) {
    return new SkillViewModel(repository);
  }
}
