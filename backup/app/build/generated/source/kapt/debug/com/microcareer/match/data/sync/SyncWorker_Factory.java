package com.microcareer.match.data.sync;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.google.firebase.auth.FirebaseAuth;
import com.microcareer.match.data.local.dao.JobDao;
import com.microcareer.match.data.local.dao.LearningModuleDao;
import com.microcareer.match.data.local.dao.SkillProfileDao;
import com.microcareer.match.data.remote.api.MicroCareerApiService;
import dagger.internal.DaggerGenerated;
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
public final class SyncWorker_Factory {
  private final Provider<MicroCareerApiService> apiServiceProvider;

  private final Provider<FirebaseAuth> firebaseAuthProvider;

  private final Provider<JobDao> jobDaoProvider;

  private final Provider<LearningModuleDao> learningModuleDaoProvider;

  private final Provider<SkillProfileDao> skillProfileDaoProvider;

  public SyncWorker_Factory(Provider<MicroCareerApiService> apiServiceProvider,
      Provider<FirebaseAuth> firebaseAuthProvider, Provider<JobDao> jobDaoProvider,
      Provider<LearningModuleDao> learningModuleDaoProvider,
      Provider<SkillProfileDao> skillProfileDaoProvider) {
    this.apiServiceProvider = apiServiceProvider;
    this.firebaseAuthProvider = firebaseAuthProvider;
    this.jobDaoProvider = jobDaoProvider;
    this.learningModuleDaoProvider = learningModuleDaoProvider;
    this.skillProfileDaoProvider = skillProfileDaoProvider;
  }

  public SyncWorker get(Context context, WorkerParameters params) {
    return newInstance(context, params, apiServiceProvider.get(), firebaseAuthProvider.get(), jobDaoProvider.get(), learningModuleDaoProvider.get(), skillProfileDaoProvider.get());
  }

  public static SyncWorker_Factory create(Provider<MicroCareerApiService> apiServiceProvider,
      Provider<FirebaseAuth> firebaseAuthProvider, Provider<JobDao> jobDaoProvider,
      Provider<LearningModuleDao> learningModuleDaoProvider,
      Provider<SkillProfileDao> skillProfileDaoProvider) {
    return new SyncWorker_Factory(apiServiceProvider, firebaseAuthProvider, jobDaoProvider, learningModuleDaoProvider, skillProfileDaoProvider);
  }

  public static SyncWorker newInstance(Context context, WorkerParameters params,
      MicroCareerApiService apiService, FirebaseAuth firebaseAuth, JobDao jobDao,
      LearningModuleDao learningModuleDao, SkillProfileDao skillProfileDao) {
    return new SyncWorker(context, params, apiService, firebaseAuth, jobDao, learningModuleDao, skillProfileDao);
  }
}
