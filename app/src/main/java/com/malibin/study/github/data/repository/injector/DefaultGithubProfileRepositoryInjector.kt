package com.malibin.study.github.data.repository.injector

import android.content.Context
import com.malibin.study.github.data.local.injector.LocalGithubProfileSourceInjector
import com.malibin.study.github.data.remote.injector.RemoteGithubProfileSourceInjector
import com.malibin.study.github.data.repository.DefaultGithubProfileRepository

object DefaultGithubProfileRepositoryInjector {
    private var instance: DefaultGithubProfileRepository? = null

    fun providesDefaultGithubProfileRepository(context: Context): DefaultGithubProfileRepository =
        synchronized(this) {
            instance ?: synchronized(this) {
                DefaultGithubProfileRepository(
                    LocalGithubProfileSourceInjector.providesLocalGithubProfileSource(context),
                    RemoteGithubProfileSourceInjector.providesRemoteGithubProfileSource(),
                )
            }.also { instance = it }
        }
}
