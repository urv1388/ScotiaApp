package com.urvish.dataprovider.domain

import com.urvish.dataprovider.data.network.model.GitHubUser
import com.urvish.dataprovider.data.network.repo.GitHubRepository
import com.urvish.dataprovider.utils.Result
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.instanceOf
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetGitHubUserUseCaseTest {

    lateinit var subject: GetGitHubUserUseCase

    private val repository: GitHubRepository = mockk()


    private val dummyUser = GitHubUser(name = "octocat")

    @Before
    fun setUp() {
        subject = GetGitHubUserUseCase(repository)
    }


    @Test
    fun `get github user success`() = runTest {
        coEvery { repository.getGitHubUser(any()) } returns Result.Success(dummyUser)
        val result = subject("")
        coVerify { repository.getGitHubUser(any()) }
        coVerify(exactly = 1) { repository.getGitHubUser(any()) }
        coVerify(exactly = 0) { repository.getUserRepo(any()) }
        result shouldBe instanceOf<Result<GitHubUser>>()
    }
}