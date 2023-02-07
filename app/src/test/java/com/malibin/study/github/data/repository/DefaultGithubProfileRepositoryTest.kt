package com.malibin.study.github.data.repository

import com.google.common.truth.Truth.assertThat
import com.malibin.study.github.data.source.GithubProfileSource
import com.malibin.study.github.domain.profile.GithubProfile
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

internal class DefaultGithubProfileRepositoryTest {


    @Test
    fun `GithubProfile이 Local DB에 없으면, Remote의 getGithubProfile()과 Local의 saveGithubProfile이 호출된다`() {
        runBlocking {
            //given
            val fakeRemoteGithubProfileSource = mockk<GithubProfileSource>()
            val fakeLocalGithubProfileSource = mockk<GithubProfileSource>()
            val defaultGithubProfileRepository = DefaultGithubProfileRepository(
                fakeLocalGithubProfileSource, fakeRemoteGithubProfileSource
            )
            val gitHubProfile = GithubProfile(
                1L,
                "name1",
                "https://www.linkpicture.com/view.php?img=LPic63df5f35265bb1785508137",
                "name1",
                "Hello",
                1,
                1
            )
            //when
            coEvery { fakeLocalGithubProfileSource.getGithubProfile("name1") } returns Result.failure(
                IllegalArgumentException("cannot find githubProfile of userName(name1)")
            )
            coEvery { fakeRemoteGithubProfileSource.getGithubProfile("name1") } returns Result.success(
                gitHubProfile
            )
            coEvery { fakeLocalGithubProfileSource.saveGithubProfile(gitHubProfile) } returns Result.success(
                Unit
            )

            val actualResult = defaultGithubProfileRepository.getGithubProfile("name1")

            //then
            assertAll(
                { coVerify(exactly = 1) { fakeLocalGithubProfileSource.getGithubProfile("name1") } },
                { coVerify(exactly = 1) { fakeRemoteGithubProfileSource.getGithubProfile("name1") } },
                {
                    coVerify(exactly = 1) {
                        fakeLocalGithubProfileSource.saveGithubProfile(
                            gitHubProfile
                        )
                    }
                },
                { assertThat(actualResult).isEqualTo(Result.success(gitHubProfile)) },
            )
            confirmVerified(fakeLocalGithubProfileSource, fakeRemoteGithubProfileSource)
        }
    }


    @Test
    fun `userName이 Local DB에 존재하면 GithubProfile을 반환한다`() {
        runBlocking {
            //given
            val fakeRemoteGithubProfileSource = mockk<GithubProfileSource>()
            val fakeLocalGithubProfileSource = mockk<GithubProfileSource>()
            val defaultGithubProfileRepository = DefaultGithubProfileRepository(
                fakeLocalGithubProfileSource, fakeRemoteGithubProfileSource
            )
            val gitHubProfile = GithubProfile(
                1L,
                "name1",
                "https://www.linkpicture.com/view.php?img=LPic63df5f35265bb1785508137",
                "name1",
                "Hello",
                1,
                1
            )
            //when
            coEvery { fakeLocalGithubProfileSource.getGithubProfile("name1") } returns Result.success(
                gitHubProfile
            )
            val actualResult = defaultGithubProfileRepository.getGithubProfile("name1")
            //then
            assertAll(
                { assertThat(actualResult).isEqualTo(Result.success(gitHubProfile)) },
                {
                    coVerify(exactly = 1) {
                        fakeLocalGithubProfileSource.getGithubProfile(
                            "name1"
                        )
                    }
                }
            )
        }
    }

    @Test
    fun `GithubProfile을 LocalDB에 저장할 때 LocalGithubProfileSource의 saveGithubProfile 메소드가 호출된다`() {
        runBlocking {
            //given
            val fakeRemoteGithubProfileSource = mockk<GithubProfileSource>()
            val fakeLocalGithubProfileSource = mockk<GithubProfileSource>()
            val defaultGithubProfileRepository = DefaultGithubProfileRepository(
                fakeLocalGithubProfileSource, fakeRemoteGithubProfileSource
            )
            val gitHubProfile = GithubProfile(
                1L,
                "name1",
                "https://www.linkpicture.com/view.php?img=LPic63df5f35265bb1785508137",
                "name1",
                "Hello",
                1,
                1
            )
            //when
            coEvery { fakeLocalGithubProfileSource.saveGithubProfile(gitHubProfile) } returns Result.success(
                Unit
            )
            defaultGithubProfileRepository.saveGithubProfile(gitHubProfile)
            //then

            coVerify(exactly = 1) {
                fakeLocalGithubProfileSource.saveGithubProfile(
                    gitHubProfile
                )
            }
        }
    }

}




