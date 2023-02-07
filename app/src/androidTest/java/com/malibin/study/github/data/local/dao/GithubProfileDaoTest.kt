package com.malibin.study.github.data.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.malibin.study.github.data.local.database.LocalDatabase
import com.malibin.study.github.data.local.entity.GithubProfileEntity
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GithubProfileDaoTest {

    private lateinit var githubProfileDao: GithubProfileDao

    @BeforeEach
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        githubProfileDao = Room.inMemoryDatabaseBuilder(context, LocalDatabase::class.java)
            .build()
            .getGithubProfileDao()
    }

    @Test
    fun `저장한_githubProfile을_다시_꺼내올_수_있다`() = runBlocking {
        // given
        val githubProfileEntity = GithubProfileEntity(
            id = 0,
            userName = "malibin",
            avatarUrl = "url",
            name = "name",
            bio = "bio",
            followersCount = 1,
            followingCount = 2,
        )
        githubProfileDao.saveGithubProfile(githubProfileEntity)

        // when
        val retrieveGithubProfileEntity = githubProfileDao.getGithubProfile("malibin")

        // then
        assertThat(retrieveGithubProfileEntity).isEqualTo(githubProfileEntity)
    }

    @Test
    fun `저장되지_않은_유저이름으로_githubProfileEntity를_조회할_수_없다`() = runBlocking {
        // when
        val retrieveGithubProfileEntity = githubProfileDao.getGithubProfile("malibin")

        // then
        assertThat(retrieveGithubProfileEntity).isNull()
    }
}
