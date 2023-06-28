package com.urvish.dataprovider.data.network.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitHubUserRepo(
    @SerialName("description")
    val description: String? = null,
    @SerialName("forks")
    val forks: Int = 0,
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String? = null,
    @SerialName("stargazers_count")
    val stargazersCount: Int = 0,
    @SerialName("updated_at")
    val updatedAt: String? = null,
)