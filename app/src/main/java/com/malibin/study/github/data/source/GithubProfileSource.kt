package com.malibin.study.github.data.source

import com.malibin.study.github.domain.profile.GithubProfile

interface GithubProfileSource {

    suspend fun getGithubProfile(userName: String): Result<GithubProfile>

    suspend fun saveGithubProfile(githubProfile: GithubProfile): Result<Unit>
}
