package com.malibin.study.github.presentation

import com.google.common.truth.Truth.assertThat
import com.malibin.study.github.domain.profile.GithubProfile
import com.malibin.study.github.domain.repository.GithubProfileRepository
import com.malibin.study.github.utils.InstantTaskExecutorExtension
import com.malibin.study.github.utils.MainCoroutineExtension
import com.malibin.study.github.utils.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
internal class MainViewModelTest {

    @RelaxedMockK
    private lateinit var profileRepository: GithubProfileRepository
    private lateinit var mainViewModel: MainViewModel

    @BeforeEach
    fun setUp() {
        mainViewModel = MainViewModel(profileRepository)
    }

    @Test
    fun `github 프로필을 가져올 수 있다`() = runTest {
        // given
        val expectedGithubProfile = GithubProfile(
            id = 1,
            userName = "June",
            avatarUrl = "",
            name = "",
            bio = "",
            followersCount = 11,
            followingCount = 12
        )
        coEvery {
            profileRepository.getGithubProfile(any())
        } returns Result.success(expectedGithubProfile)
        // when
        mainViewModel.loadGithubProfile()
        val actualGithubProfile = mainViewModel.githubProfile.getOrAwaitValue()
        // then
        assertAll(
            { coVerify(exactly = 1) { profileRepository.getGithubProfile(any()) } },
            { assertThat(actualGithubProfile).isEqualTo(expectedGithubProfile) }
        )
    }

    @Test
    fun `github 프로필을 가져오지 못하면 Error가 발생한다`() = runTest {
        // given
        coEvery {
            profileRepository.getGithubProfile(any())
        } returns Result.failure(Exception("토큰이 만료됨"))
        // when
        mainViewModel.loadGithubProfile()
        val actualGithubProfile = mainViewModel.githubProfile.getOrAwaitValue()
        val actualIsError = mainViewModel.isError.getOrAwaitValue()
        // then
        assertAll(
            { coVerify(exactly = 1) { profileRepository.getGithubProfile(any()) } },
            { assertThat(actualGithubProfile).isNull() },
            { assertThat(actualIsError).isTrue() }
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
