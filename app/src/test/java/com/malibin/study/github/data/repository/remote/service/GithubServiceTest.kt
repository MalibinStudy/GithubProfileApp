package com.malibin.study.github.data.repository.remote.service

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.malibin.study.github.data.remote.response.ErrorResponse
import com.malibin.study.github.data.remote.response.GithubProfileResponse
import com.malibin.study.github.data.remote.service.GithubService
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.io.File

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
internal class GithubServiceTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var githubService: GithubService

    @BeforeAll
    fun setUp() {
        mockWebServer = MockWebServer()
        githubService = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(mockWebServer.url(""))
            .build()
            .create()
    }

    @Test
    fun `userName에 해당하는 사람의 깃허브 프로필 정보를 불러올 수 있다`() = runTest {
        // given
        val profileJson = File("src/test/res/profile.json").readText()
        val fakeResponse = MockResponse().setBody(profileJson).setResponseCode(200)
        mockWebServer.enqueue(fakeResponse)
        val expectedResponse = GithubProfileResponse(
            id = 87055456,
            login = "murjune",
            name = "JUNWON LEE",
            avatarUrl = "https://avatars.githubusercontent.com/u/87055456?v=4",
            bio = "하나하나 배워나가고 있습니다 :D",
            followers = 33,
            following = 37
        )
        // when
        val actualResponse = githubService.getUserProfile("murjune")
        // then
        assertAll(
            { assertThat(actualResponse.isSuccessful).isTrue() },
            { assertThat(actualResponse.code()).isEqualTo(200) },
            { assertThat(actualResponse.body()).isEqualTo(expectedResponse) }
        )
    }

    @Test
    fun `userName에 해당하는 사람이 없으면 404 Error가 뜬다`() = runTest {
        // given
        val errorJson = """
            {
            "message" : "해당 ID에 해당되는 유저가 없습니다",
            "status" : 404
            }
        """.trimIndent()
        val fakeResponse = MockResponse().setBody(errorJson).setResponseCode(404)
        mockWebServer.enqueue(fakeResponse)
        val expectedErrorResponse = ErrorResponse(
            message = "해당 ID에 해당되는 유저가 없습니다",
            status = 404
        )
        // when
        val actualResponse = githubService.getUserProfile("murjune")
        val actualErrorResponse =
            Gson().fromJson(actualResponse.errorBody()?.string(), ErrorResponse::class.java)
        println(actualErrorResponse)
        // then
        assertAll(
            { assertThat(actualResponse.isSuccessful).isFalse() },
            { assertThat(actualResponse.code()).isEqualTo(404) },
            { assertThat(actualErrorResponse).isEqualTo(expectedErrorResponse) }
        )
    }

    @Test
    fun `token 만료 시 401 Error가 뜬다`() = runTest {
        // given
        val errorJson = File("src/test/res/token_expiration.json").readText()
        val fakeResponse = MockResponse().setBody(errorJson).setResponseCode(401)
        mockWebServer.enqueue(fakeResponse)
        val expectedErrorResponse = ErrorResponse(
            message = "유효하지 않은 토큰입니다.",
            status = 401
        )
        // when
        val actualResponse = githubService.getUserProfile("murjune")
        val actualErrorResponse =
            Gson().fromJson(actualResponse.errorBody()?.string(), ErrorResponse::class.java)
        // then
        assertAll(
            { assertThat(actualResponse.isSuccessful).isFalse() },
            { assertThat(actualResponse.code()).isEqualTo(401) },
            { assertThat(actualErrorResponse).isEqualTo(expectedErrorResponse) }
        )
    }
}
