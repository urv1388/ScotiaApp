package com.urvish.dataprovider.domain

import com.urvish.dataprovider.data.network.model.GitHubUser
import com.urvish.dataprovider.data.network.repo.GitHubRepository
import com.urvish.dataprovider.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGitHubUserUseCase @Inject constructor(private val repository: GitHubRepository) {

    suspend operator fun invoke(username: String): Result<GitHubUser> {
        return repository.getGitHubUser(username)
    }
}