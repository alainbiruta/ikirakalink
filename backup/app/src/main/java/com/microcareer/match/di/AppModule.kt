package com.microcareer.match.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.microcareer.match.BuildConfig
import com.microcareer.match.data.local.dao.*
import com.microcareer.match.data.local.database.MicroCareerDatabase
import com.microcareer.match.data.remote.api.MicroCareerApiService
import com.microcareer.match.data.repository.AuthRepository
import com.microcareer.match.data.local.dao.OfflinePendingActionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "app_preferences")

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseMessaging(): FirebaseMessaging = FirebaseMessaging.getInstance()

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
        .setLenient()
        .create()
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MicroCareerDatabase {
        return Room.databaseBuilder(
            context,
            MicroCareerDatabase::class.java,
            MicroCareerDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: MicroCareerDatabase): UserDao = database.userDao()

    @Provides
    @Singleton
    fun provideJobDao(database: MicroCareerDatabase): JobDao = database.jobDao()

    @Provides
    @Singleton
    fun provideLearningModuleDao(database: MicroCareerDatabase): LearningModuleDao =
        database.learningModuleDao()

    @Provides
    @Singleton
    fun provideSkillProfileDao(database: MicroCareerDatabase): SkillProfileDao =
        database.skillProfileDao()
    @Provides
    @Singleton
    fun provideOfflinePendingActionDao(database: MicroCareerDatabase): OfflinePendingActionDao =
        database.offlinePendingActionDao()
}



