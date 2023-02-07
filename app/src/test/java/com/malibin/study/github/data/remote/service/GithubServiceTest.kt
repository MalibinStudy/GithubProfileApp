package com.malibin.study.github.data.remote.service

import com.google.common.truth.Truth.assertThat
import com.malibin.study.github.data.remote.response.GithubProfileResponse
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.io.File

internal class GithubServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var githubService: GithubService

    @BeforeEach
    fun setUp() {
        mockWebServer = MockWebServer()
        githubService = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(mockWebServer.url(""))
            .build()
            .create()
    }

    @Test
    fun `Github 유저의 Profile를 검색할 수 있다`() = runBlocking {
        // given
        val successResponseJson = File("src/test/res/users_malibinYun_success_200.json").readText()
        val response = MockResponse().setBody(successResponseJson)
        mockWebServer.enqueue(response)

        val expectedGithubProfile = GithubProfileResponse(
            id = 44341119,
            login = "malibinYun",
            avatarUrl = "https://avatars.githubusercontent.com/u/44341119?v=4",
            name = "Yun Hyeok",
            bio = "천천히 내 것으로",
            followers = 115,
            following = 83,
        )

        // when
        val actualResponse = githubService.getUserProfile("malibinYun")

        // then
        assertAll(
            { assertThat(actualResponse.isSuccessful).isTrue() },
            { assertThat(actualResponse.code()).isEqualTo(200) },
            { assertThat(actualResponse.body()).isEqualTo(expectedGithubProfile) },
        )
    }

    @Test
    fun `가입되지 않은 유저 아이디로는 GithubProfile를 찾을 수 없다`() = runBlocking {
        // given
        val successResponseJson = File("src/test/res/users_malibinYun_failure_404.json").readText()
        val response = MockResponse()
            .setResponseCode(404)
            .setBody(successResponseJson)
        mockWebServer.enqueue(response)

        // when
        val actualResponse = githubService.getUserProfile("notMember")

        // then
        assertAll(
            { assertThat(actualResponse.isSuccessful).isFalse() },
            { assertThat(actualResponse.code()).isEqualTo(404) },
            { assertThat(actualResponse.body()).isNull() },
        )
    }
}
