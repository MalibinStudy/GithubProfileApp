package com.malibin.study.github.data.remote.service

import com.malibin.study.github.data.remote.response.GithubProfileResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {

    @GET("/users/{userName}")
    suspend fun getUserProfile(
        @Path("userName") userName: String,
    ): Response<GithubProfileResponse>

    companion object {
        private const val BASE_URL = "https://api.github.com"

        private var instance: GithubService? = null

        fun getInstance(): GithubService = synchronized(this) {
            instance ?: synchronized(this) {
                newInstance().also { instance = it }
            }
        }

        private fun newInstance(): GithubService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create()
        }
    }
}
