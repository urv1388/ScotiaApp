package com.urvish.dataprovider.data.network.repo

import com.urvish.dataprovider.data.network.model.GitHubUser
import com.urvish.dataprovider.data.network.api.GitHubAPI
import com.urvish.dataprovider.data.network.model.GitHubUserRepo
import com.urvish.dataprovider.utils.Result
import com.urvish.dataprovider.utils.toResult
import javax.inject.Inject

class GitHubRepoImpl @Inject constructor(private val api: GitHubAPI) : GitHubRepository {
    override suspend fun getGitHubUser(username: String): Result<GitHubUser> {
        return api.getGitHubUser(username).toResult()
    }

    override suspend fun getUserRepo(username: String): Result<List<GitHubUserRepo>> {
        return api.getGitHubUserRepos(username).toResult()
    }
}