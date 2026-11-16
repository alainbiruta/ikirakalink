package com.microcareer.match.data.local.seeder;

import com.google.firebase.auth.FirebaseAuth;
import com.microcareer.match.data.local.dao.JobDao;
import com.microcareer.match.data.local.dao.LearningModuleDao;
import com.microcareer.match.data.local.dao.UserDao;
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
public final class DatabaseSeeder_Factory implements Factory<DatabaseSeeder> {
  private final Provider<JobDao> jobDaoProvider;

  private final Provider<LearningModuleDao> learningModuleDaoProvider;

  private final Provider<UserDao> userDaoProvider;

  private final Provider<FirebaseAuth> firebaseAuthProvider;

  public DatabaseSeeder_Factory(Provider<JobDao> jobDaoProvider,
      Provider<LearningModuleDao> learningModuleDaoProvider, Provider<UserDao> userDaoProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    this.jobDaoProvider = jobDaoProvider;
    this.learningModuleDaoProvider = learningModuleDaoProvider;
    this.userDaoProvider = userDaoProvider;
    this.firebaseAuthProvider = firebaseAuthProvider;
  }

  @Override
  public DatabaseSeeder get() {
    return newInstance(jobDaoProvider.get(), learningModuleDaoProvider.get(), userDaoProvider.get(), firebaseAuthProvider.get());
  }

  public static DatabaseSeeder_Factory create(Provider<JobDao> jobDaoProvider,
      Provider<LearningModuleDao> learningModuleDaoProvider, Provider<UserDao> userDaoProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    return new DatabaseSeeder_Factory(jobDaoProvider, learningModuleDaoProvider, userDaoProvider, firebaseAuthProvider);
  }

  public static DatabaseSeeder newInstance(JobDao jobDao, LearningModuleDao learningModuleDao,
      UserDao userDao, FirebaseAuth firebaseAuth) {
    return new DatabaseSeeder(jobDao, learningModuleDao, userDao, firebaseAuth);
  }
}
