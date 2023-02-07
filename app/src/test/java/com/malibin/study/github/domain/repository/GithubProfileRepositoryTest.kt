package com.malibin.study.github.domain.repository

import com.google.common.truth.Truth.assertThat
import com.malibin.study.github.domain.profile.GithubProfile
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

internal class GithubProfileRepositoryTest {
    @Test
    fun `입력된 이름에 대한 Github Profile이 반환된다`() {
        runBlocking {
            //given
            val fakeGithubProfileRepository = mockk<GithubProfileRepository>()
            val gitHubProfile = GithubProfile(
                1L,
                "name1",
                "https://www.linkpicture.com/view.php?img=LPic63df5f35265bb1785508137",
                "name1",
                "Hello",
                1,
                1
            )
            coEvery { fakeGithubProfileRepository.getGithubProfile("name") } returns Result.success(
                gitHubProfile
            )

            //when
            val actualProfile = fakeGithubProfileRepository.getGithubProfile("name")
            //then
            assertThat(actualProfile).isEqualTo(Result.success(gitHubProfile))
        }
    }

    @Test
    fun `Github Profile을 Local DB에 저장한다`() {
        runBlocking {
            //given
            val fakeGithubProfileRepository = mockk<GithubProfileRepository>()
            val gitHubProfile = GithubProfile(
                1L,
                "name1",
                "https://www.linkpicture.com/view.php?img=LPic63df5f35265bb1785508137",
                "name1",
                "Hello",
                1,
                1
            )
            coEvery {
                fakeGithubProfileRepository.saveGithubProfile(
                    gitHubProfile
                )
            } returns Result.success(Unit)
            //when
            val actualResult =
                fakeGithubProfileRepository.saveGithubProfile(gitHubProfile)
            //then
            assertThat(actualResult).isEqualTo(Result.success(Unit))
        }
    }
}