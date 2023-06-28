package com.urvish.dataprovider.data.network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.urvish.dataprovider.data.network.api.GitHubAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }


    @Provides
    @Singleton
    fun okHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .apply { setLevel(HttpLoggingInterceptor.Level.BODY) },
        )
        .build()

    @Provides
    @Singleton
    fun provideGitHubAPI(okHttpClient: OkHttpClient, json: Json): GitHubAPI =
        Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType())).build().create(GitHubAPI::class.java)
}

const val BASE_URL = "https://api.github.com/"