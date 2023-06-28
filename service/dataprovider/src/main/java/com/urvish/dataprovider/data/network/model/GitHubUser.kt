package com.urvish.dataprovider.data.network.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitHubUser(
    @SerialName("avatar_url")
    val avatarUrl: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("message")
    val message: String? = null
)