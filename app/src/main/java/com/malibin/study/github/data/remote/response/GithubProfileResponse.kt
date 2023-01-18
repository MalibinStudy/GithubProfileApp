package com.malibin.study.github.data.remote.response

import com.google.gson.annotations.SerializedName

data class GithubProfileResponse(
    @SerializedName("login") val login: String,
    @SerializedName("id") val id: Long,
    @SerializedName("avatar_url") val avatarUrl: Long,
    @SerializedName("name") val name: String?,
    @SerializedName("bio") val bio: String?,
    @SerializedName("followers") val followers: Int,
    @SerializedName("following") val following: Int,
)
