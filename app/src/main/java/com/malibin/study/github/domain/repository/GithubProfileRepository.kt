package com.malibin.study.github.domain.repository

import com.malibin.study.github.domain.profile.GithubProfile

interface GithubProfileRepository {

    suspend fun getGithubProfile(userName: String): Result<GithubProfile>
}
