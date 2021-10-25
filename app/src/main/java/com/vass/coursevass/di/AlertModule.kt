package com.vass.coursevass.di

import android.content.Context
import com.vass.coursevass.network.AlertBuild
import com.vass.coursevass.network.service.AlertService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AlertModule {
    @Provides
    @Singleton
    fun provideAlert(@ApplicationContext context: Context): AlertService {
       return AlertBuild(context)
    }
}