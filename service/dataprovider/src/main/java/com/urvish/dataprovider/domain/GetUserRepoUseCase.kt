package com.urvish.dataprovider.domain

import com.urvish.dataprovider.data.network.model.GitHubUserRepo
import com.urvish.dataprovider.data.network.repo.GitHubRepository
import com.urvish.dataprovider.utils.Result
import javax.inject.Inject

class GetUserRepoUseCase @Inject constructor(private val repository: GitHubRepository) {

    suspend operator fun invoke(username: String): Result<List<GitHubUserRepo>> {
        return repository.getUserRepo(username)
    }
}