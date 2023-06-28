package com.urvish.dataprovider.data.network.di

import com.urvish.dataprovider.data.network.repo.GitHubRepoImpl
import com.urvish.dataprovider.data.network.repo.GitHubRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideGitHubRepo(impl: GitHubRepoImpl): GitHubRepository

}