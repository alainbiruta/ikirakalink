package com.microcareer.match.di

import com.microcareer.match.data.repository.LearningRepositoryImpl
import com.microcareer.match.data.repository.ProfileRepositoryImpl
import com.microcareer.match.data.repository.UserRepository
import com.microcareer.match.data.repository.LearningRepository
import com.microcareer.match.domain.repository.ProfileRepository
import com.microcareer.match.domain.repository.UserRepository as UserRepositoryInterface
import com.microcareer.match.data.remote.api.LearningApiService
import retrofit2.Retrofit
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProfileRepository(
        impl: ProfileRepositoryImpl
    ): ProfileRepository

    @Binds
    @Singleton
    abstract fun bindLearningRepository(
        impl: LearningRepositoryImpl
    ): LearningRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        impl: UserRepository
    ): UserRepositoryInterface

    // ── @Provides must live in a companion object inside
    //    an abstract module — Dagger requires this ──────────
    companion object {

        @Provides
        @Singleton
        fun provideLearningApiService(retrofit: Retrofit): LearningApiService =
            retrofit.create(LearningApiService::class.java)
    }
}