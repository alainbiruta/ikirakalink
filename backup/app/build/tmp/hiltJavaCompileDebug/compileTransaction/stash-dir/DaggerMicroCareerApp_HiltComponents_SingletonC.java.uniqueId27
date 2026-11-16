package com.microcareer.match;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.hilt.work.HiltWorkerFactory;
import androidx.hilt.work.HiltWrapper_WorkerFactoryModule;
import androidx.hilt.work.WorkerAssistedFactory;
import androidx.hilt.work.WorkerFactoryModule_ProvideFactoryFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.microcareer.match.data.fcm.MicroCareerMessagingService;
import com.microcareer.match.data.fcm.MicroCareerMessagingService_MembersInjector;
import com.microcareer.match.data.local.FcmNotificationStore;
import com.microcareer.match.data.local.OfflineActionQueue;
import com.microcareer.match.data.local.TokenManager;
import com.microcareer.match.data.local.dao.JobDao;
import com.microcareer.match.data.local.dao.LearningModuleDao;
import com.microcareer.match.data.local.dao.OfflinePendingActionDao;
import com.microcareer.match.data.local.dao.SkillProfileDao;
import com.microcareer.match.data.local.dao.UserDao;
import com.microcareer.match.data.local.database.MicroCareerDatabase;
import com.microcareer.match.data.local.seeder.DatabaseSeeder;
import com.microcareer.match.data.remote.api.ApplicationsApiService;
import com.microcareer.match.data.remote.api.AuthApiService;
import com.microcareer.match.data.remote.api.JobsApiService;
import com.microcareer.match.data.remote.api.LearningApiService;
import com.microcareer.match.data.remote.api.MicroCareerApiService;
import com.microcareer.match.data.repository.AuthRepository;
import com.microcareer.match.data.repository.JobRepository;
import com.microcareer.match.data.repository.LearningRepositoryImpl;
import com.microcareer.match.data.repository.ProfileRepositoryImpl;
import com.microcareer.match.data.repository.UserRepository;
import com.microcareer.match.data.sync.SyncWorker;
import com.microcareer.match.data.sync.SyncWorker_AssistedFactory;
import com.microcareer.match.di.AppModule;
import com.microcareer.match.di.AppModule_ProvideFirebaseAuthFactory;
import com.microcareer.match.di.AppModule_ProvideGsonFactory;
import com.microcareer.match.di.DatabaseModule;
import com.microcareer.match.di.DatabaseModule_ProvideDatabaseFactory;
import com.microcareer.match.di.DatabaseModule_ProvideJobDaoFactory;
import com.microcareer.match.di.DatabaseModule_ProvideLearningModuleDaoFactory;
import com.microcareer.match.di.DatabaseModule_ProvideOfflinePendingActionDaoFactory;
import com.microcareer.match.di.DatabaseModule_ProvideSkillProfileDaoFactory;
import com.microcareer.match.di.DatabaseModule_ProvideUserDaoFactory;
import com.microcareer.match.di.NetworkModule;
import com.microcareer.match.di.NetworkModule_ProvideApplicationsApiServiceFactory;
import com.microcareer.match.di.NetworkModule_ProvideAuthApiServiceFactory;
import com.microcareer.match.di.NetworkModule_ProvideJobsApiServiceFactory;
import com.microcareer.match.di.NetworkModule_ProvideMicroCareerApiServiceFactory;
import com.microcareer.match.di.NetworkModule_ProvideNetworkMonitorFactory;
import com.microcareer.match.di.NetworkModule_ProvideOkHttpClientFactory;
import com.microcareer.match.di.NetworkModule_ProvideRetrofitFactory;
import com.microcareer.match.di.NetworkModule_ProvideTokenManagerFactory;
import com.microcareer.match.di.RepositoryModule_Companion_ProvideLearningApiServiceFactory;
import com.microcareer.match.presentation.MainActivity;
import com.microcareer.match.presentation.auth.AuthViewModel;
import com.microcareer.match.presentation.auth.AuthViewModel_HiltModules_KeyModule_ProvideFactory;
import com.microcareer.match.presentation.debug.DebugViewModel;
import com.microcareer.match.presentation.debug.DebugViewModel_HiltModules_KeyModule_ProvideFactory;
import com.microcareer.match.presentation.employer.EmployerHomeViewModel;
import com.microcareer.match.presentation.employer.EmployerHomeViewModel_HiltModules_KeyModule_ProvideFactory;
import com.microcareer.match.presentation.employer.PostJobViewModel;
import com.microcareer.match.presentation.employer.PostJobViewModel_HiltModules_KeyModule_ProvideFactory;
import com.microcareer.match.presentation.home.HomeViewModel;
import com.microcareer.match.presentation.home.HomeViewModel_HiltModules_KeyModule_ProvideFactory;
import com.microcareer.match.presentation.job.JobViewModel;
import com.microcareer.match.presentation.job.JobViewModel_HiltModules_KeyModule_ProvideFactory;
import com.microcareer.match.presentation.learning.LearningViewModel;
import com.microcareer.match.presentation.learning.LearningViewModel_HiltModules_KeyModule_ProvideFactory;
import com.microcareer.match.presentation.profile.ProfileViewModel;
import com.microcareer.match.presentation.profile.ProfileViewModel_HiltModules_KeyModule_ProvideFactory;
import com.microcareer.match.presentation.skill.SkillViewModel;
import com.microcareer.match.presentation.skill.SkillViewModel_HiltModules_KeyModule_ProvideFactory;
import com.microcareer.match.utils.NetworkMonitor;
import com.microcareer.match.worker.SyncScheduler;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.flags.HiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import dagger.internal.SingleCheck;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

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
public final class DaggerMicroCareerApp_HiltComponents_SingletonC {
  private DaggerMicroCareerApp_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder appModule(AppModule appModule) {
      Preconditions.checkNotNull(appModule);
      return this;
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder databaseModule(DatabaseModule databaseModule) {
      Preconditions.checkNotNull(databaseModule);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder hiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule(
        HiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule hiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule) {
      Preconditions.checkNotNull(hiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder hiltWrapper_WorkerFactoryModule(
        HiltWrapper_WorkerFactoryModule hiltWrapper_WorkerFactoryModule) {
      Preconditions.checkNotNull(hiltWrapper_WorkerFactoryModule);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder networkModule(NetworkModule networkModule) {
      Preconditions.checkNotNull(networkModule);
      return this;
    }

    public MicroCareerApp_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements MicroCareerApp_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public MicroCareerApp_HiltComponents.ActivityRetainedC build() {
      return new ActivityRetainedCImpl(singletonCImpl);
    }
  }

  private static final class ActivityCBuilder implements MicroCareerApp_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public MicroCareerApp_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements MicroCareerApp_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public MicroCareerApp_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements MicroCareerApp_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public MicroCareerApp_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements MicroCareerApp_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public MicroCareerApp_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements MicroCareerApp_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public MicroCareerApp_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements MicroCareerApp_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public MicroCareerApp_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends MicroCareerApp_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends MicroCareerApp_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends MicroCareerApp_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends MicroCareerApp_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Set<String> getViewModelKeys() {
      return ImmutableSet.<String>of(AuthViewModel_HiltModules_KeyModule_ProvideFactory.provide(), DebugViewModel_HiltModules_KeyModule_ProvideFactory.provide(), EmployerHomeViewModel_HiltModules_KeyModule_ProvideFactory.provide(), HomeViewModel_HiltModules_KeyModule_ProvideFactory.provide(), JobViewModel_HiltModules_KeyModule_ProvideFactory.provide(), LearningViewModel_HiltModules_KeyModule_ProvideFactory.provide(), PostJobViewModel_HiltModules_KeyModule_ProvideFactory.provide(), ProfileViewModel_HiltModules_KeyModule_ProvideFactory.provide(), SkillViewModel_HiltModules_KeyModule_ProvideFactory.provide());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }
  }

  private static final class ViewModelCImpl extends MicroCareerApp_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AuthViewModel> authViewModelProvider;

    private Provider<DebugViewModel> debugViewModelProvider;

    private Provider<EmployerHomeViewModel> employerHomeViewModelProvider;

    private Provider<HomeViewModel> homeViewModelProvider;

    private Provider<JobViewModel> jobViewModelProvider;

    private Provider<LearningViewModel> learningViewModelProvider;

    private Provider<PostJobViewModel> postJobViewModelProvider;

    private Provider<ProfileViewModel> profileViewModelProvider;

    private Provider<SkillViewModel> skillViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.authViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.debugViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.employerHomeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.homeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.jobViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.learningViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.postJobViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.profileViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
      this.skillViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 8);
    }

    @Override
    public Map<String, Provider<ViewModel>> getHiltViewModelMap() {
      return ImmutableMap.<String, Provider<ViewModel>>builderWithExpectedSize(9).put("com.microcareer.match.presentation.auth.AuthViewModel", ((Provider) authViewModelProvider)).put("com.microcareer.match.presentation.debug.DebugViewModel", ((Provider) debugViewModelProvider)).put("com.microcareer.match.presentation.employer.EmployerHomeViewModel", ((Provider) employerHomeViewModelProvider)).put("com.microcareer.match.presentation.home.HomeViewModel", ((Provider) homeViewModelProvider)).put("com.microcareer.match.presentation.job.JobViewModel", ((Provider) jobViewModelProvider)).put("com.microcareer.match.presentation.learning.LearningViewModel", ((Provider) learningViewModelProvider)).put("com.microcareer.match.presentation.employer.PostJobViewModel", ((Provider) postJobViewModelProvider)).put("com.microcareer.match.presentation.profile.ProfileViewModel", ((Provider) profileViewModelProvider)).put("com.microcareer.match.presentation.skill.SkillViewModel", ((Provider) skillViewModelProvider)).build();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.microcareer.match.presentation.auth.AuthViewModel 
          return (T) new AuthViewModel(singletonCImpl.authRepositoryProvider.get());

          case 1: // com.microcareer.match.presentation.debug.DebugViewModel 
          return (T) new DebugViewModel(singletonCImpl.databaseSeederProvider.get());

          case 2: // com.microcareer.match.presentation.employer.EmployerHomeViewModel 
          return (T) new EmployerHomeViewModel(singletonCImpl.jobRepositoryProvider.get(), singletonCImpl.provideTokenManagerProvider.get(), singletonCImpl.fcmNotificationStoreProvider.get());

          case 3: // com.microcareer.match.presentation.home.HomeViewModel 
          return (T) new HomeViewModel(singletonCImpl.jobRepositoryProvider.get(), singletonCImpl.provideNetworkMonitorProvider.get(), singletonCImpl.fcmNotificationStoreProvider.get());

          case 4: // com.microcareer.match.presentation.job.JobViewModel 
          return (T) new JobViewModel(singletonCImpl.jobRepositoryProvider.get());

          case 5: // com.microcareer.match.presentation.learning.LearningViewModel 
          return (T) new LearningViewModel(singletonCImpl.learningRepositoryImplProvider.get());

          case 6: // com.microcareer.match.presentation.employer.PostJobViewModel 
          return (T) new PostJobViewModel(singletonCImpl.jobRepositoryProvider.get());

          case 7: // com.microcareer.match.presentation.profile.ProfileViewModel 
          return (T) new ProfileViewModel(singletonCImpl.profileRepositoryImplProvider.get());

          case 8: // com.microcareer.match.presentation.skill.SkillViewModel 
          return (T) new SkillViewModel(singletonCImpl.profileRepositoryImplProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends MicroCareerApp_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;

      initialize();

    }

    @SuppressWarnings("unchecked")
    private void initialize() {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends MicroCareerApp_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }

    @Override
    public void injectMicroCareerMessagingService(
        MicroCareerMessagingService microCareerMessagingService) {
      injectMicroCareerMessagingService2(microCareerMessagingService);
    }

    @CanIgnoreReturnValue
    private MicroCareerMessagingService injectMicroCareerMessagingService2(
        MicroCareerMessagingService instance) {
      MicroCareerMessagingService_MembersInjector.injectTokenManager(instance, singletonCImpl.provideTokenManagerProvider.get());
      MicroCareerMessagingService_MembersInjector.injectAuthApiService(instance, singletonCImpl.provideAuthApiServiceProvider.get());
      MicroCareerMessagingService_MembersInjector.injectNotificationStore(instance, singletonCImpl.fcmNotificationStoreProvider.get());
      return instance;
    }
  }

  private static final class SingletonCImpl extends MicroCareerApp_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<TokenManager> provideTokenManagerProvider;

    private Provider<OkHttpClient> provideOkHttpClientProvider;

    private Provider<Retrofit> provideRetrofitProvider;

    private Provider<MicroCareerApiService> provideMicroCareerApiServiceProvider;

    private Provider<FirebaseAuth> provideFirebaseAuthProvider;

    private Provider<MicroCareerDatabase> provideDatabaseProvider;

    private Provider<JobDao> provideJobDaoProvider;

    private Provider<LearningModuleDao> provideLearningModuleDaoProvider;

    private Provider<SkillProfileDao> provideSkillProfileDaoProvider;

    private Provider<SyncWorker_AssistedFactory> syncWorker_AssistedFactoryProvider;

    private Provider<OfflinePendingActionDao> provideOfflinePendingActionDaoProvider;

    private Provider<OfflineActionQueue> offlineActionQueueProvider;

    private Provider<ApplicationsApiService> provideApplicationsApiServiceProvider;

    private Provider<AuthApiService> provideAuthApiServiceProvider;

    private Provider<UserDao> provideUserDaoProvider;

    private Provider<UserRepository> userRepositoryProvider;

    private Provider<com.microcareer.match.worker.SyncWorker_AssistedFactory> syncWorker_AssistedFactoryProvider2;

    private Provider<FcmNotificationStore> fcmNotificationStoreProvider;

    private Provider<AuthRepository> authRepositoryProvider;

    private Provider<DatabaseSeeder> databaseSeederProvider;

    private Provider<JobsApiService> provideJobsApiServiceProvider;

    private Provider<NetworkMonitor> provideNetworkMonitorProvider;

    private Provider<SyncScheduler> syncSchedulerProvider;

    private Provider<JobRepository> jobRepositoryProvider;

    private Provider<LearningApiService> provideLearningApiServiceProvider;

    private Provider<Gson> provideGsonProvider;

    private Provider<LearningRepositoryImpl> learningRepositoryImplProvider;

    private Provider<ProfileRepositoryImpl> profileRepositoryImplProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private Map<String, Provider<WorkerAssistedFactory<? extends ListenableWorker>>> mapOfStringAndProviderOfWorkerAssistedFactoryOf(
        ) {
      return ImmutableMap.<String, Provider<WorkerAssistedFactory<? extends ListenableWorker>>>of("com.microcareer.match.data.sync.SyncWorker", ((Provider) syncWorker_AssistedFactoryProvider), "com.microcareer.match.worker.SyncWorker", ((Provider) syncWorker_AssistedFactoryProvider2));
    }

    private HiltWorkerFactory hiltWorkerFactory() {
      return WorkerFactoryModule_ProvideFactoryFactory.provideFactory(mapOfStringAndProviderOfWorkerAssistedFactoryOf());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideTokenManagerProvider = DoubleCheck.provider(new SwitchingProvider<TokenManager>(singletonCImpl, 4));
      this.provideOkHttpClientProvider = DoubleCheck.provider(new SwitchingProvider<OkHttpClient>(singletonCImpl, 3));
      this.provideRetrofitProvider = DoubleCheck.provider(new SwitchingProvider<Retrofit>(singletonCImpl, 2));
      this.provideMicroCareerApiServiceProvider = DoubleCheck.provider(new SwitchingProvider<MicroCareerApiService>(singletonCImpl, 1));
      this.provideFirebaseAuthProvider = DoubleCheck.provider(new SwitchingProvider<FirebaseAuth>(singletonCImpl, 5));
      this.provideDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<MicroCareerDatabase>(singletonCImpl, 7));
      this.provideJobDaoProvider = DoubleCheck.provider(new SwitchingProvider<JobDao>(singletonCImpl, 6));
      this.provideLearningModuleDaoProvider = DoubleCheck.provider(new SwitchingProvider<LearningModuleDao>(singletonCImpl, 8));
      this.provideSkillProfileDaoProvider = DoubleCheck.provider(new SwitchingProvider<SkillProfileDao>(singletonCImpl, 9));
      this.syncWorker_AssistedFactoryProvider = SingleCheck.provider(new SwitchingProvider<SyncWorker_AssistedFactory>(singletonCImpl, 0));
      this.provideOfflinePendingActionDaoProvider = DoubleCheck.provider(new SwitchingProvider<OfflinePendingActionDao>(singletonCImpl, 12));
      this.offlineActionQueueProvider = DoubleCheck.provider(new SwitchingProvider<OfflineActionQueue>(singletonCImpl, 11));
      this.provideApplicationsApiServiceProvider = DoubleCheck.provider(new SwitchingProvider<ApplicationsApiService>(singletonCImpl, 13));
      this.provideAuthApiServiceProvider = DoubleCheck.provider(new SwitchingProvider<AuthApiService>(singletonCImpl, 15));
      this.provideUserDaoProvider = DoubleCheck.provider(new SwitchingProvider<UserDao>(singletonCImpl, 16));
      this.userRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<UserRepository>(singletonCImpl, 14));
      this.syncWorker_AssistedFactoryProvider2 = SingleCheck.provider(new SwitchingProvider<com.microcareer.match.worker.SyncWorker_AssistedFactory>(singletonCImpl, 10));
      this.fcmNotificationStoreProvider = DoubleCheck.provider(new SwitchingProvider<FcmNotificationStore>(singletonCImpl, 18));
      this.authRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<AuthRepository>(singletonCImpl, 17));
      this.databaseSeederProvider = DoubleCheck.provider(new SwitchingProvider<DatabaseSeeder>(singletonCImpl, 19));
      this.provideJobsApiServiceProvider = DoubleCheck.provider(new SwitchingProvider<JobsApiService>(singletonCImpl, 21));
      this.provideNetworkMonitorProvider = DoubleCheck.provider(new SwitchingProvider<NetworkMonitor>(singletonCImpl, 22));
      this.syncSchedulerProvider = DoubleCheck.provider(new SwitchingProvider<SyncScheduler>(singletonCImpl, 23));
      this.jobRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<JobRepository>(singletonCImpl, 20));
      this.provideLearningApiServiceProvider = DoubleCheck.provider(new SwitchingProvider<LearningApiService>(singletonCImpl, 25));
      this.provideGsonProvider = DoubleCheck.provider(new SwitchingProvider<Gson>(singletonCImpl, 26));
      this.learningRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<LearningRepositoryImpl>(singletonCImpl, 24));
      this.profileRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<ProfileRepositoryImpl>(singletonCImpl, 27));
    }

    @Override
    public void injectMicroCareerApp(MicroCareerApp microCareerApp) {
      injectMicroCareerApp2(microCareerApp);
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return ImmutableSet.<Boolean>of();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    @CanIgnoreReturnValue
    private MicroCareerApp injectMicroCareerApp2(MicroCareerApp instance) {
      MicroCareerApp_MembersInjector.injectWorkerFactory(instance, hiltWorkerFactory());
      return instance;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.microcareer.match.data.sync.SyncWorker_AssistedFactory 
          return (T) new SyncWorker_AssistedFactory() {
            @Override
            public SyncWorker create(Context context, WorkerParameters params) {
              return new SyncWorker(context, params, singletonCImpl.provideMicroCareerApiServiceProvider.get(), singletonCImpl.provideFirebaseAuthProvider.get(), singletonCImpl.provideJobDaoProvider.get(), singletonCImpl.provideLearningModuleDaoProvider.get(), singletonCImpl.provideSkillProfileDaoProvider.get());
            }
          };

          case 1: // com.microcareer.match.data.remote.api.MicroCareerApiService 
          return (T) NetworkModule_ProvideMicroCareerApiServiceFactory.provideMicroCareerApiService(singletonCImpl.provideRetrofitProvider.get());

          case 2: // retrofit2.Retrofit 
          return (T) NetworkModule_ProvideRetrofitFactory.provideRetrofit(singletonCImpl.provideOkHttpClientProvider.get());

          case 3: // okhttp3.OkHttpClient 
          return (T) NetworkModule_ProvideOkHttpClientFactory.provideOkHttpClient(singletonCImpl.provideTokenManagerProvider.get());

          case 4: // com.microcareer.match.data.local.TokenManager 
          return (T) NetworkModule_ProvideTokenManagerFactory.provideTokenManager(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 5: // com.google.firebase.auth.FirebaseAuth 
          return (T) AppModule_ProvideFirebaseAuthFactory.provideFirebaseAuth();

          case 6: // com.microcareer.match.data.local.dao.JobDao 
          return (T) DatabaseModule_ProvideJobDaoFactory.provideJobDao(singletonCImpl.provideDatabaseProvider.get());

          case 7: // com.microcareer.match.data.local.database.MicroCareerDatabase 
          return (T) DatabaseModule_ProvideDatabaseFactory.provideDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 8: // com.microcareer.match.data.local.dao.LearningModuleDao 
          return (T) DatabaseModule_ProvideLearningModuleDaoFactory.provideLearningModuleDao(singletonCImpl.provideDatabaseProvider.get());

          case 9: // com.microcareer.match.data.local.dao.SkillProfileDao 
          return (T) DatabaseModule_ProvideSkillProfileDaoFactory.provideSkillProfileDao(singletonCImpl.provideDatabaseProvider.get());

          case 10: // com.microcareer.match.worker.SyncWorker_AssistedFactory 
          return (T) new com.microcareer.match.worker.SyncWorker_AssistedFactory() {
            @Override
            public com.microcareer.match.worker.SyncWorker create(Context context2,
                WorkerParameters params2) {
              return new com.microcareer.match.worker.SyncWorker(context2, params2, singletonCImpl.offlineActionQueueProvider.get(), singletonCImpl.provideApplicationsApiServiceProvider.get(), singletonCImpl.provideJobDaoProvider.get(), singletonCImpl.provideTokenManagerProvider.get(), singletonCImpl.userRepositoryProvider.get());
            }
          };

          case 11: // com.microcareer.match.data.local.OfflineActionQueue 
          return (T) new OfflineActionQueue(singletonCImpl.provideOfflinePendingActionDaoProvider.get());

          case 12: // com.microcareer.match.data.local.dao.OfflinePendingActionDao 
          return (T) DatabaseModule_ProvideOfflinePendingActionDaoFactory.provideOfflinePendingActionDao(singletonCImpl.provideDatabaseProvider.get());

          case 13: // com.microcareer.match.data.remote.api.ApplicationsApiService 
          return (T) NetworkModule_ProvideApplicationsApiServiceFactory.provideApplicationsApiService(singletonCImpl.provideRetrofitProvider.get());

          case 14: // com.microcareer.match.data.repository.UserRepository 
          return (T) new UserRepository(singletonCImpl.provideAuthApiServiceProvider.get(), singletonCImpl.provideUserDaoProvider.get(), singletonCImpl.provideTokenManagerProvider.get());

          case 15: // com.microcareer.match.data.remote.api.AuthApiService 
          return (T) NetworkModule_ProvideAuthApiServiceFactory.provideAuthApiService(singletonCImpl.provideRetrofitProvider.get());

          case 16: // com.microcareer.match.data.local.dao.UserDao 
          return (T) DatabaseModule_ProvideUserDaoFactory.provideUserDao(singletonCImpl.provideDatabaseProvider.get());

          case 17: // com.microcareer.match.data.repository.AuthRepository 
          return (T) new AuthRepository(singletonCImpl.provideAuthApiServiceProvider.get(), singletonCImpl.provideUserDaoProvider.get(), singletonCImpl.provideTokenManagerProvider.get(), singletonCImpl.provideJobDaoProvider.get(), singletonCImpl.fcmNotificationStoreProvider.get());

          case 18: // com.microcareer.match.data.local.FcmNotificationStore 
          return (T) new FcmNotificationStore(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.provideTokenManagerProvider.get());

          case 19: // com.microcareer.match.data.local.seeder.DatabaseSeeder 
          return (T) new DatabaseSeeder(singletonCImpl.provideJobDaoProvider.get(), singletonCImpl.provideLearningModuleDaoProvider.get(), singletonCImpl.provideUserDaoProvider.get(), singletonCImpl.provideFirebaseAuthProvider.get());

          case 20: // com.microcareer.match.data.repository.JobRepository 
          return (T) new JobRepository(singletonCImpl.provideJobsApiServiceProvider.get(), singletonCImpl.provideApplicationsApiServiceProvider.get(), singletonCImpl.provideJobDaoProvider.get(), singletonCImpl.offlineActionQueueProvider.get(), singletonCImpl.provideNetworkMonitorProvider.get(), singletonCImpl.syncSchedulerProvider.get(), singletonCImpl.provideTokenManagerProvider.get());

          case 21: // com.microcareer.match.data.remote.api.JobsApiService 
          return (T) NetworkModule_ProvideJobsApiServiceFactory.provideJobsApiService(singletonCImpl.provideRetrofitProvider.get());

          case 22: // com.microcareer.match.utils.NetworkMonitor 
          return (T) NetworkModule_ProvideNetworkMonitorFactory.provideNetworkMonitor(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 23: // com.microcareer.match.worker.SyncScheduler 
          return (T) new SyncScheduler(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 24: // com.microcareer.match.data.repository.LearningRepositoryImpl 
          return (T) new LearningRepositoryImpl(singletonCImpl.provideLearningModuleDaoProvider.get(), singletonCImpl.provideLearningApiServiceProvider.get(), singletonCImpl.provideNetworkMonitorProvider.get(), singletonCImpl.provideGsonProvider.get());

          case 25: // com.microcareer.match.data.remote.api.LearningApiService 
          return (T) RepositoryModule_Companion_ProvideLearningApiServiceFactory.provideLearningApiService(singletonCImpl.provideRetrofitProvider.get());

          case 26: // com.google.gson.Gson 
          return (T) AppModule_ProvideGsonFactory.provideGson();

          case 27: // com.microcareer.match.data.repository.ProfileRepositoryImpl 
          return (T) new ProfileRepositoryImpl(singletonCImpl.provideUserDaoProvider.get(), singletonCImpl.provideTokenManagerProvider.get(), singletonCImpl.provideAuthApiServiceProvider.get(), singletonCImpl.provideNetworkMonitorProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
