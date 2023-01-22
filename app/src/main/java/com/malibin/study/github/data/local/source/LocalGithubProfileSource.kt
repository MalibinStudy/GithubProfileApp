package com.malibin.study.github.data.local.source

import com.malibin.study.github.data.local.dao.GithubProfileDao
import com.malibin.study.github.data.local.mapper.toGithubProfile
import com.malibin.study.github.data.local.mapper.toGithubProfileEntity
import com.malibin.study.github.data.source.GithubProfileSource
import com.malibin.study.github.domain.profile.GithubProfile

class LocalGithubProfileSource(
    private val githubProfileDao: GithubProfileDao,
) : GithubProfileSource {

    override suspend fun getGithubProfile(userName: String): Result<GithubProfile> {
        val localGithubProfile = githubProfileDao.getGithubProfile(userName)
            ?: return Result.failure(IllegalArgumentException("cannot find githubProfile of userName($userName)"))
        return Result.success(localGithubProfile.toGithubProfile())
    }

    override suspend fun saveGithubProfile(githubProfile: GithubProfile): Result<Unit> {
        return runCatching { githubProfileDao.saveGithubProfile(githubProfile.toGithubProfileEntity()) }
    }
}
