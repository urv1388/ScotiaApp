package com.urvish.dataprovider.data.network.api

import com.urvish.dataprovider.data.network.model.GitHubUser
import com.urvish.dataprovider.data.network.model.GitHubUserRepo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubAPI {

    @GET("users/{githubName}")
    suspend fun getGitHubUser(@Path("githubName") username: String): Response<GitHubUser>

    @GET("users/{githubName}/repos")
    suspend fun getGitHubUserRepos(@Path("githubName") username: String): Response<List<GitHubUserRepo>>

}