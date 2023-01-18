package com.malibin.study.github.domain.profile


data class GithubProfile(
    val id: Long,
    val githubId: String,
    val avatarUrl: Long,
    val name: String,
    val bio: String,
    val followersCount: Int,
    val followingCount: Int,
)
