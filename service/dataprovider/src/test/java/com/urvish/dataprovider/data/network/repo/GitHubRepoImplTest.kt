package com.urvish.dataprovider.data.network.repo

import com.urvish.dataprovider.data.network.api.GitHubAPI
import com.urvish.dataprovider.data.network.model.GitHubUser
import com.urvish.dataprovider.data.network.model.GitHubUserRepo
import com.urvish.dataprovider.utils.Result
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.instanceOf
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class GitHubRepoImplTest {

    private lateinit var subject: GitHubRepoImpl

    private val api: GitHubAPI = mockk()

    @Before
    fun setUp() {
        subject = GitHubRepoImpl(api)
    }


    @Test
    fun `get github user api success`() = runTest {
        coEvery { api.getGitHubUser(any()) } returns Response.success(GitHubUser(name = "Urvish"))
        val result = subject.getGitHubUser("octocat")
        coVerify { api.getGitHubUser(any()) }
        coVerify(exactly = 1) { api.getGitHubUser(any()) }
        coVerify(exactly = 0) { api.getGitHubUserRepos(any()) }
        result shouldBe instanceOf<Result<GitHubUser>>()
    }

    @Test
    fun `get github user repo api success`() = runTest {
        coEvery { api.getGitHubUserRepos(any()) } returns Response.success(arrayListOf())
        val result = subject.getUserRepo("octocat")
        coVerify { api.getGitHubUserRepos(any()) }
        coVerify(exactly = 1) { api.getGitHubUserRepos(any()) }
        coVerify(exactly = 0) { api.getGitHubUser(any()) }
        result shouldBe instanceOf<Result<List<GitHubUserRepo>>>()
    }


    @Test
    fun `get github user api fail`() = runTest {
        coEvery { api.getGitHubUser(any()) } returns Response.error(
            500, "{}".toResponseBody()
        )
        val result = subject.getGitHubUser("octocat")
        result shouldBe instanceOf<Result.Error>()
    }
}