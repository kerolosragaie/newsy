package com.likander.newsy.di

import com.likander.newsy.core.utils.Constants
import com.likander.newsy.features.headline.data.remote.api.HeadlineApi
import com.likander.newsy.features.headline.data.remote.data_source.HeadlineRemoteDataSource
import com.likander.newsy.features.headline.data.remote.data_source.HeadlineRemoteDataSourceImpl
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
object HeadlineRemoteModule {

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient =
        OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).connectTimeout(20, TimeUnit.SECONDS).build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideHeadlineApi(retrofit: Retrofit): HeadlineApi = retrofit
        .create(HeadlineApi::class.java)

    @Provides
    @Singleton
    fun provideHeadlineRemoteDataSource(
        headlineApi: HeadlineApi
    ): HeadlineRemoteDataSource =
        HeadlineRemoteDataSourceImpl(headlineApi)

}