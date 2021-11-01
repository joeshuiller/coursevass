package com.vass.coursevass.di



import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.vass.coursevass.BuildConfig
import com.vass.coursevass.network.AuthInterceptor
import com.vass.coursevass.network.service.AuthServices
import com.vass.coursevass.network.service.TaskService
import com.vass.coursevass.network.service.UserService
import com.vass.coursevass.utils.storage.Storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun providerRetrofit(storage: Storage): Retrofit {
        val builder = Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
        val okHttpClient = OkHttpClient.Builder()
        if (BuildConfig.DEBUG){
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClient.addInterceptor(loggingInterceptor)
        }
        okHttpClient.addInterceptor(AuthInterceptor(storage))
        okHttpClient.writeTimeout(0, TimeUnit.MILLISECONDS)
            .readTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES).build()
        return builder.client(okHttpClient.build()).build()
    }
    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthServices{
        return retrofit.create(AuthServices::class.java)
    }
    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }
    @Provides
    @Singleton
    fun provideSaveTaskService(retrofit: Retrofit): TaskService {
        return retrofit.create(TaskService::class.java)
    }
}