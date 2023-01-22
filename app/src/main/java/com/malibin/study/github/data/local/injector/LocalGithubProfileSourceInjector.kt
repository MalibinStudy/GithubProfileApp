package com.malibin.study.github.data.local.injector

import android.content.Context
import com.malibin.study.github.data.local.database.LocalDatabase
import com.malibin.study.github.data.local.source.LocalGithubProfileSource

object LocalGithubProfileSourceInjector {
    private var instance: LocalGithubProfileSource? = null

    fun providesLocalGithubProfileSource(
        context: Context
    ): LocalGithubProfileSource = synchronized(this) {
        instance ?: synchronized(this) {
            LocalGithubProfileSource(
                LocalDatabase.getInstance(context).getGithubProfileDao()
            )
        }.also { instance = it }
    }
}
