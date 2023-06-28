package com.urvish.dataprovider.model

import com.urvish.dataprovider.data.network.model.GitHubUser
import com.urvish.dataprovider.data.network.model.GitHubUserRepo

data class GitHubUserData(var gitUser: GitHubUser, var repos: List<GitHubUserRepo>)
