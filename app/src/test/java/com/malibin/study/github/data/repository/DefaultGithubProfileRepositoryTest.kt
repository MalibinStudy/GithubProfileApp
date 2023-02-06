package com.malibin.study.github.data.repository

import com.google.common.truth.Truth.assertThat
import com.malibin.study.github.data.source.GithubProfileSource
import com.malibin.study.github.domain.profile.GithubProfile
import com.malibin.study.github.utils.InstantTaskExecutorExtension
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

internal class DefaultGithubProfileRepositoryTest {

    private lateinit var fakeLocalSource: GithubProfileSource
    private lateinit var fakeRemoteSource: GithubProfileSource

    companion object {
        @JvmStatic
        @RegisterExtension
        val instantTaskExecutorExtension = InstantTaskExecutorExtension()
    }

    @BeforeEach
    fun setUp() {
        fakeLocalSource = mockk<GithubProfileSource>(relaxed = true)
        fakeRemoteSource = mockk<GithubProfileSource>(relaxed = true)
    }

    @Test
    fun `로컬에 유저 정보가 있음을 확인할 수 있다`() = runBlocking {
        // given
        val expectedSearch = fakeLocalSource.getGithubProfile("stopkite").isSuccess

        coEvery {
            fakeLocalSource.getGithubProfile("stopkite").isSuccess
        } returns true

        // when
        val actualSearch = DefaultGithubProfileRepository(
            fakeLocalSource,
            fakeRemoteSource
        ).getGithubProfile("stopkite").isSuccess

        // then
        assertThat(actualSearch).isEqualTo(expectedSearch)
    }

    @Test
    fun `로컬에 유저 정보가 이미 존재할 때 유저 정보를 반환할 수 있다`() = runBlocking {
        // given
        val githubProfile =
            GithubProfile(
                0,
                "stopkite",
                "https://avatars.githubusercontent.com/u/62979643?v=4",
                "Ji-Yeon",
                "1",
                10,
                100
            )

        coEvery { fakeLocalSource.getGithubProfile("stopkite") } returns runCatching { githubProfile }

        // when
        val actualLocalProfile = fakeLocalSource.getGithubProfile("stopkite").getOrThrow()

        // then
        assertThat(actualLocalProfile).isEqualTo(githubProfile)
    }
}