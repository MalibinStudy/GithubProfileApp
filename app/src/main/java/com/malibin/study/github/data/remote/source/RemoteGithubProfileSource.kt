package com.malibin.study.github.data.remote.source

import com.malibin.study.github.data.remote.mapper.toGithubProfile
import com.malibin.study.github.data.remote.service.GithubService
import com.malibin.study.github.data.source.GithubProfileSource
import com.malibin.study.github.domain.profile.GithubProfile

class RemoteGithubProfileSource(
    private val githubService: GithubService,
) : GithubProfileSource {

    override suspend fun getGithubProfile(userName: String): Result<GithubProfile> {
        val response = githubService.getUserProfile(userName)
        if (response.isSuccessful) {
            val githubProfileResponse = response.body() ?: error("response body cannot be null")
            return Result.success(githubProfileResponse.toGithubProfile())
        }
        return Result.failure(IllegalArgumentException(response.message()))
    }

    override suspend fun saveGithubProfile(githubProfile: GithubProfile): Result<Unit> {
        throw UnsupportedOperationException("cannot call saveGithubProfile() in remote")
    }
}
