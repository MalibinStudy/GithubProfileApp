package com.malibin.study.github.domain.profile


data class GithubProfile(
    val id: Long,
    val userName: String,
    val avatarUrl: String,
    val name: String,
    val bio: String,
    val followersCount: Int,
    val followingCount: Int,
)
