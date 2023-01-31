package com.malibin.study.github.data.repository

import com.malibin.study.github.data.source.GithubProfileSource
import com.malibin.study.github.domain.profile.GithubProfile
import com.malibin.study.github.domain.repository.GithubProfileRepository

class DefaultGithubProfileRepository(
    private val localGithubProfileSource: GithubProfileSource,
    private val remoteGithubProfileSource: GithubProfileSource,
) : GithubProfileRepository {

    override suspend fun getGithubProfile(userName: String): Result<GithubProfile> {
        val localProfile = localGithubProfileSource.getGithubProfile(userName)

        return when (localProfile.isSuccess) {
            true -> localProfile
            false -> remoteGithubProfileSource.getGithubProfile(userName)
                .onSuccess { localGithubProfileSource.saveGithubProfile(it) }
        }
    }

    override suspend fun saveGithubProfile(githubProfile: GithubProfile): Result<Unit> {
        return localGithubProfileSource.saveGithubProfile(githubProfile)
    }
}
