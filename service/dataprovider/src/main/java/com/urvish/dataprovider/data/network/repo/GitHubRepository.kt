package com.urvish.dataprovider.data.network.repo

import com.urvish.dataprovider.data.network.model.GitHubUser
import com.urvish.dataprovider.data.network.model.GitHubUserRepo
import com.urvish.dataprovider.utils.Result

interface GitHubRepository {

    suspend fun getGitHubUser(username: String): Result<GitHubUser>

    suspend fun getUserRepo(username: String): Result<List<GitHubUserRepo>>
}