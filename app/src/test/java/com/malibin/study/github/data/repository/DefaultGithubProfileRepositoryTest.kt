package com.malibin.study.github.data.repository

import com.google.common.truth.Truth.assertThat
import com.malibin.study.github.data.source.GithubProfileSource
import com.malibin.study.github.domain.profile.GithubProfile
import com.malibin.study.github.domain.repository.GithubProfileRepository
import com.malibin.study.github.utils.InstantTaskExecutorExtension
import com.malibin.study.github.utils.MainCoroutineExtension
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
internal class DefaultGithubProfileRepositoryTest {

    @RelaxedMockK
    lateinit var localGithubProfileSource: GithubProfileSource

    @RelaxedMockK
    lateinit var remoteGithubProfileSource: GithubProfileSource
    private lateinit var githubProfileRepository: GithubProfileRepository

    @BeforeEach
    fun setUp() {
        githubProfileRepository = DefaultGithubProfileRepository(
            localGithubProfileSource,
            remoteGithubProfileSource
        )
    }

    @Test
    fun `githubProfile을 저장할 떄 내부 저장소에 저장된다`() = runTest {
        // given
        val profile = GithubProfile(
            id = 1,
            userName = "June",
            avatarUrl = "",
            name = "",
            bio = "",
            followersCount = 11,
            followingCount = 12
        )
        // when
        val actualResult = githubProfileRepository.saveGithubProfile(profile)
        // then
        assertAll(
            { coVerify(exactly = 1) { localGithubProfileSource.saveGithubProfile(profile) } },
            { assertThat(actualResult.isSuccess).isTrue() }
        )
    }

    @Test
    fun `local저장소에 userName에 해당하는 profile이 있으면 가져온다`() = runTest {
        // given
        val userName = "June"
        val expectedProfile = GithubProfile(
            id = 1,
            userName = "June",
            avatarUrl = "",
            name = "",
            bio = "",
            followersCount = 11,
            followingCount = 12
        )
        coEvery {
            githubProfileRepository.getGithubProfile(userName)
        } returns Result.success(
            expectedProfile
        )
        // when
        val actualResult: Result<GithubProfile> = githubProfileRepository.getGithubProfile(userName)
        // then
        assertAll(
            { coVerify(exactly = 1) { localGithubProfileSource.getGithubProfile(userName) } },
            { coVerify(exactly = 0) { remoteGithubProfileSource.getGithubProfile(any()) } },
            { assertThat(actualResult.getOrNull()).isEqualTo(expectedProfile) }
        )
    }

    @Test
    fun `local저장소에 userName에 해당하는 profile이 없으면 remote에서 가져온다`() = runTest {
        // given
        val userName = "June"
        val expectedException = IllegalArgumentException("$userName 이름에 해당하는 이름 없음")
        val expectedProfile = GithubProfile(
            id = 1,
            userName = "June",
            avatarUrl = "",
            name = "",
            bio = "",
            followersCount = 11,
            followingCount = 12
        )
        coEvery {
            localGithubProfileSource.getGithubProfile(userName)
        } returns Result.failure(
            expectedException
        )
        coEvery {
            remoteGithubProfileSource.getGithubProfile(userName)
        } returns Result.success(
            expectedProfile
        )
        coEvery {
            localGithubProfileSource.saveGithubProfile(expectedProfile)
        } answers {
            Result.success(
                Unit
            )
        }
        val actualResult = githubProfileRepository.getGithubProfile(userName)
        // then
        assertAll(
            { coVerify(exactly = 1) { localGithubProfileSource.getGithubProfile(userName) } },
            { coVerify(exactly = 1) { remoteGithubProfileSource.getGithubProfile(userName) } },
            { coVerify(exactly = 1) { localGithubProfileSource.saveGithubProfile(expectedProfile) } },
            {
                coVerifySequence {
                    localGithubProfileSource.getGithubProfile(userName)
                    remoteGithubProfileSource.getGithubProfile(userName)
                    localGithubProfileSource.saveGithubProfile(expectedProfile)
                }
            },
            { assertThat(actualResult.getOrNull()).isEqualTo(expectedProfile) },
            { assertThat(actualResult.isSuccess).isTrue() }
        )
    }

    companion object {
        @JvmField
        @RegisterExtension
        val instantTaskExecutorExtension = InstantTaskExecutorExtension()

        @JvmField
        @RegisterExtension
        val coroutineExtension = MainCoroutineExtension()
    }
}
