package com.nacho.spaceflight.challenge.di

import com.nacho.spaceflight.challenge.data.remote.SpaceFlightApi
import com.nacho.spaceflight.challenge.domain.repository.ArticleRepository
import com.nacho.spaceflight.challenge.domain.repository.ArticleRepositoryImpl
import com.nacho.spaceflight.challenge.domain.usecase.GetArticlesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ArticlesModule {

    @Provides
    @Singleton
    fun provideSpaceFlightApi(retrofit: Retrofit): SpaceFlightApi =
        retrofit.create(SpaceFlightApi::class.java)

    @Provides
    @Singleton
    fun provideArticleRepository(api: SpaceFlightApi): ArticleRepository =
        ArticleRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideGetArticlesUseCase(repository: ArticleRepository): GetArticlesUseCase =
        GetArticlesUseCase(repository)
}