package com.malibin.study.github.data.remote.mapper

import com.malibin.study.github.data.remote.response.GithubProfileResponse
import com.malibin.study.github.domain.profile.GithubProfile

fun GithubProfileResponse.toGithubProfile(): GithubProfile = GithubProfile(
    id = this.id,
    githubId = this.login,
    avatarUrl = this.avatarUrl,
    name = this.name.orEmpty(),
    bio = this.bio.orEmpty(),
    followersCount = this.followers,
    followingCount = this.following,
)
