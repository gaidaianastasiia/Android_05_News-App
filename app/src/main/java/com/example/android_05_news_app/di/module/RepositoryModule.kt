package com.example.android_05_news_app.di.module

import com.example.android_05_news_app.data.repository.ArticleRepository
import com.example.android_05_news_app.data.repository.ArticleRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Singleton
    @Binds
    fun bindArticleRepository(articleRepositoryImpl: ArticleRepositoryImpl): ArticleRepository
}