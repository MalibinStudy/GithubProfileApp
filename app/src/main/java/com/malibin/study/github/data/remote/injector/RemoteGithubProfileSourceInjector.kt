package com.malibin.study.github.data.remote.injector

import com.malibin.study.github.data.remote.service.GithubService
import com.malibin.study.github.data.remote.source.RemoteGithubProfileSource

object RemoteGithubProfileSourceInjector {
    private var instance: RemoteGithubProfileSource? = null

    fun providesRemoteGithubProfileSource(): RemoteGithubProfileSource = synchronized(this) {
        instance ?: synchronized(this) { RemoteGithubProfileSource(GithubService.getInstance()) }
            .also { instance = it }
    }
}
