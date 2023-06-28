package com.urvish.scotia.ui.main

import com.urvish.dataprovider.data.network.model.GitHubUser
import com.urvish.dataprovider.domain.GetGitHubUserUseCase
import com.urvish.dataprovider.domain.GetUserRepoUseCase
import com.urvish.dataprovider.utils.Result
import com.urvish.scotia.MainDispatcherRule
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.instanceOf
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    val coroutineRule = MainDispatcherRule()

    lateinit var subject: MainViewModel

    val userUseCase: GetGitHubUserUseCase = mockk()
    val userRepoUseCase: GetUserRepoUseCase = mockk()

    private val dummyUser = GitHubUser(name = "octocat")


    @Before
    fun setUp() {
        subject = MainViewModel(userUseCase, userRepoUseCase)
    }

    @Test
    fun `verify initial values`() {
        subject.gitHubUserUIState.value shouldBe GitHubUserUIState.IDLE
        subject.userRepoUIState.value shouldBe UserRepoUIState.IDLE
    }


    @Test
    fun `get github user with success`() {
        coEvery { userUseCase(any()) } returns Result.Success(dummyUser)
        coEvery { userRepoUseCase(any()) } returns Result.Success(arrayListOf())

        subject.getGitHubUser("oct")

        subject.gitHubUserUIState.value shouldBe instanceOf<GitHubUserUIState.Success>()
        subject.userRepoUIState.value shouldBe instanceOf<UserRepoUIState.Success>()
    }


    @Test
    fun `get github user with success user and fail repos`() {
        coEvery { userUseCase(any()) } returns Result.Success(dummyUser)
        coEvery { userRepoUseCase(any()) } returns Result.Error()

        subject.getGitHubUser("oct")

        subject.gitHubUserUIState.value shouldBe instanceOf<GitHubUserUIState.Success>()
        subject.userRepoUIState.value shouldBe instanceOf<UserRepoUIState.Error>()
    }
}