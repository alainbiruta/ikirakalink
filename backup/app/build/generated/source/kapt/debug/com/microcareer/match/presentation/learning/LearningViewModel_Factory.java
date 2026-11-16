package com.microcareer.match.presentation.learning;

import com.microcareer.match.data.repository.LearningRepository;
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
public final class LearningViewModel_Factory implements Factory<LearningViewModel> {
  private final Provider<LearningRepository> learningRepositoryProvider;

  public LearningViewModel_Factory(Provider<LearningRepository> learningRepositoryProvider) {
    this.learningRepositoryProvider = learningRepositoryProvider;
  }

  @Override
  public LearningViewModel get() {
    return newInstance(learningRepositoryProvider.get());
  }

  public static LearningViewModel_Factory create(
      Provider<LearningRepository> learningRepositoryProvider) {
    return new LearningViewModel_Factory(learningRepositoryProvider);
  }

  public static LearningViewModel newInstance(LearningRepository learningRepository) {
    return new LearningViewModel(learningRepository);
  }
}
