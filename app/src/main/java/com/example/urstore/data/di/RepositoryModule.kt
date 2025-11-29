package com.example.urstore.data.di

import com.example.urstore.data.repository.CartRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCartRepo(): CartRepo {
        return CartRepo()
    }
}