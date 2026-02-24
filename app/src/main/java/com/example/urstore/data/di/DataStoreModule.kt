package com.example.urstore.data.di

import android.content.Context
import com.example.urstore.util.DataStoreRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStoreRepo(@ApplicationContext context: Context): DataStoreRepo {
        return DataStoreRepo(context)
    }
}