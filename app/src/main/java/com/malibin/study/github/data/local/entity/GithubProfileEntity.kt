package com.malibin.study.github.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GithubProfileEntity(
    @PrimaryKey val id: Long,
    val userName: String,
    val avatarUrl: String,
    val name: String,
    val bio: String,
    val followersCount: Int,
    val followingCount: Int,
)
