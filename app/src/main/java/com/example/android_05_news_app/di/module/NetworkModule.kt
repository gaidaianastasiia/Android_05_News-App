package com.example.android_05_news_app.di.module

import com.example.android_05_news_app.BuildConfig
import com.example.android_05_news_app.data.network.ArticlesApiService
import com.example.android_05_news_app.data.network.interceptor.QueryInterceptor
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideArticlesApiService(okHttpClient: OkHttpClient): ArticlesApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.NEWS_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(okHttpClient)
            .build()
            .create(ArticlesApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(queryInterceptor: QueryInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(queryInterceptor).build()
    }
}