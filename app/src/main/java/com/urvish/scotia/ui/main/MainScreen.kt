package com.urvish.scotia.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.urvish.dataprovider.data.network.model.GitHubUserRepo
import com.urvish.scotia.ui.common.ShowError
import com.urvish.scotia.ui.common.ShowLoading
import com.urvish.scotia.ui.common.R as CommonR

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {

    val gitHubUserUIState by viewModel.gitHubUserUIState.collectAsStateWithLifecycle()
    val userRepoUIState by viewModel.userRepoUIState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        GitUserSearchView(onSearch = viewModel::getGitHubUser)
        GitHubUserContent(gitHubUserUIState)
        Spacer(modifier = Modifier.padding(8.dp))
        GitHubUserRepoContent(userRepoUIState)
    }
}

@Composable
fun GitHubUserContent(userUIState: GitHubUserUIState) {
    when (userUIState) {
        is GitHubUserUIState.Error -> {
            userUIState.error?.message?.let { ShowError(it) }
        }

        GitHubUserUIState.IDLE -> {}
        GitHubUserUIState.Loading -> {
            ShowLoading()
        }

        is GitHubUserUIState.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = userUIState.data.avatarUrl,
                    contentDescription = null,
                    modifier = Modifier.testTag("UserImage")
                )
                Text(
                    text = userUIState.data.name
                        ?: stringResource(id = CommonR.string.error_no_name),
                    modifier = Modifier.testTag("UserName")
                )
            }
        }
    }
}

@Composable
fun GitHubUserRepoContent(repoUIState: UserRepoUIState) {
    when (repoUIState) {
        is UserRepoUIState.Error -> {
            repoUIState.error?.message?.let { ShowError(it) }
        }

        UserRepoUIState.IDLE -> {}
        UserRepoUIState.Loading -> {
            ShowLoading()
        }

        is UserRepoUIState.Success -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(repoUIState.data, key = { it.id }) {
                    RepoCard(it)
                    Spacer(modifier = Modifier.padding(10.dp))
                }
            }
        }
    }
}


@Composable
private fun RepoCard(userRepo: GitHubUserRepo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            userRepo.name?.let {
                Text(
                    text = it,
                    color = if (userRepo.forkMoreThen5000) {
                        Color.Red
                    } else {
                        Color.Unspecified
                    },
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Spacer(modifier = Modifier.padding(5.dp))
            userRepo.description?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GitUserSearchView(modifier: Modifier = Modifier, onSearch: (String) -> Unit) {
    val focusManager = LocalFocusManager.current
    var searchValue by rememberSaveable { mutableStateOf("") }
    val errorMessage = stringResource(id = CommonR.string.error_user_no_input)
    var isError by rememberSaveable { mutableStateOf(false) }
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        TextField(
            value = searchValue,
            onValueChange = {
                searchValue = it
                isError = false
            },
            modifier = Modifier
                .weight(1f)
                .testTag("SearchTextField"),
            label = { Text(text = stringResource(id = CommonR.string.text_field_label_github_user)) },
            keyboardActions = KeyboardActions(onSearch = {
                focusManager.clearFocus()
                if (searchValue.isEmpty()) isError = true else onSearch(searchValue)
            }),
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                imeAction = ImeAction.Search,
                keyboardType = KeyboardType.Text
            ),
            isError = isError,
            supportingText = {
                if (isError) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )
        Spacer(Modifier.size(8.dp))
        Button(
            onClick = {
                focusManager.clearFocus()
                if (searchValue.isEmpty()) isError = true else onSearch(searchValue)
            }, modifier = Modifier
                .wrapContentWidth()
                .testTag("SearchButton")
        ) {
            Text(text = "Search")
        }
    }
}
