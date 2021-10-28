package com.vass.coursevass.network





import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.vass.coursevass.BuildConfig
import com.vass.coursevass.utils.storage.Storage
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitGenere {
    companion object{
        fun getRetrofit(storage: Storage): Retrofit {
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
    }
}