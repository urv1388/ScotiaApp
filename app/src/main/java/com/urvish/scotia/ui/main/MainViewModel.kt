package com.urvish.scotia.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.urvish.dataprovider.data.network.model.GitHubUser
import com.urvish.dataprovider.data.network.model.GitHubUserRepo
import com.urvish.dataprovider.domain.GetGitHubUserUseCase
import com.urvish.dataprovider.domain.GetUserRepoUseCase
import com.urvish.dataprovider.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val gitHubUserUseCase: GetGitHubUserUseCase,
    val repoUseCase: GetUserRepoUseCase,
) : ViewModel() {

    private val _gitHubUserUIState = MutableStateFlow<GitHubUserUIState>(GitHubUserUIState.IDLE)
    val gitHubUserUIState: StateFlow<GitHubUserUIState> = _gitHubUserUIState

    private val _userRepoUIState =
        MutableStateFlow<UserRepoUIState>(UserRepoUIState.IDLE)
    val userRepoUIState: StateFlow<UserRepoUIState> = _userRepoUIState

    fun getGitHubUser(username: String) {
        viewModelScope.launch {
            _gitHubUserUIState.update { GitHubUserUIState.Loading }
            _userRepoUIState.update { UserRepoUIState.IDLE }
            when (val result = gitHubUserUseCase(username)) {
                is Result.Error -> {
                    _gitHubUserUIState.update { GitHubUserUIState.Error(result.exception) }
                }

                is Result.Success -> {
                    _gitHubUserUIState.update {
                        GitHubUserUIState.Success(result.data)
                    }

                    _userRepoUIState.update { UserRepoUIState.Loading }
                    when (val resultRepo = repoUseCase(username)) {
                        is Result.Error -> {
                            _userRepoUIState.update { UserRepoUIState.Error(resultRepo.exception) }
                        }

                        is Result.Success -> {
                            _userRepoUIState.update {
                                UserRepoUIState.Success(resultRepo.data)
                            }
                        }
                    }
                }
            }
        }

    }

}

sealed interface GitHubUserUIState {
    object IDLE : GitHubUserUIState
    object Loading : GitHubUserUIState
    data class Success(val data: GitHubUser) : GitHubUserUIState
    data class Error(val error: Throwable?) : GitHubUserUIState
}

sealed interface UserRepoUIState {
    object IDLE : UserRepoUIState
    object Loading : UserRepoUIState
    data class Success(val data: List<GitHubUserRepo>) : UserRepoUIState
    data class Error(val error: Throwable?) : UserRepoUIState
}