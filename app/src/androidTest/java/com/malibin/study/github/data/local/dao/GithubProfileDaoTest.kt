package com.malibin.study.github.data.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.malibin.study.github.data.local.database.LocalDatabase
import com.malibin.study.github.data.local.entity.GithubProfileEntity
import com.malibin.study.github.utils.MainCoroutineExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@OptIn(ExperimentalCoroutinesApi::class)
internal class GithubProfileDaoTest {
    private lateinit var localDatabase: LocalDatabase
    private lateinit var githubProfileDao: GithubProfileDao

    @BeforeEach
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        localDatabase = Room.inMemoryDatabaseBuilder(context, LocalDatabase::class.java).build()
        githubProfileDao = localDatabase.getGithubProfileDao()
    }

    @AfterEach
    fun tearDown() {
        localDatabase.close()
    }

    @Test
    fun `저장한_GithubProfileEntity를_꺼내올_수_있다`() = runTest {
        // given

        val expectedGithubProfileEntity = GithubProfileEntity(
            id = 0,
            name = "Malibin",
            userName = "Malibin",
            avatarUrl = "https://avatars.githubusercontent.com/u/46064193?v=4",
            bio = "안녕하세요. Malibin입니다.",
            followersCount = 1,
            followingCount = 2
        )
        // when
        githubProfileDao.saveGithubProfile(expectedGithubProfileEntity)
        val actualGithubProfileEntity = githubProfileDao.getGithubProfile("Malibin")

        // then
        assertThat(actualGithubProfileEntity).isEqualTo(expectedGithubProfileEntity)
    }

    companion object {
        @JvmField
        @RegisterExtension
        val coroutineExtension = MainCoroutineExtension()
    }
}
