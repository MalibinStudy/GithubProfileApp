package com.malibin.study.github.data.local.mapper

import com.malibin.study.github.data.local.entity.GithubProfileEntity
import com.malibin.study.github.domain.profile.GithubProfile

fun GithubProfileEntity.toGithubProfile(): GithubProfile = GithubProfile(
    id = this.id,
    userName = this.userName,
    avatarUrl = this.avatarUrl,
    name = this.name,
    bio = this.bio,
    followersCount = this.followersCount,
    followingCount = this.followingCount,
)

fun GithubProfile.toGithubProfileEntity(): GithubProfileEntity = GithubProfileEntity(
    id = this.id,
    userName = this.userName,
    avatarUrl = this.avatarUrl,
    name = this.name,
    bio = this.bio,
    followersCount = this.followersCount,
    followingCount = this.followingCount,
)
