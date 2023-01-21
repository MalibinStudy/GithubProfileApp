package com.malibin.study.github.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.malibin.study.github.data.local.entity.GithubProfileEntity

@Dao
interface GithubProfileDao {

    @Query("SELECT * FROM GithubProfileEntity WHERE userName = :userName")
    suspend fun getGithubProfile(userName: String): GithubProfileEntity

    @Insert
    suspend fun saveGithubProfile(githubProfileEntity: GithubProfileEntity)
}
