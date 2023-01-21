package com.malibin.study.github.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.malibin.study.github.data.local.dao.GithubProfileDao
import com.malibin.study.github.data.local.entity.GithubProfileEntity

@Database(
    entities = [GithubProfileEntity::class],
    version = 1,
)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun getGithubProfileDao(): GithubProfileDao

    companion object {
        private var instance: LocalDatabase? = null

        fun getInstance(context: Context): LocalDatabase = synchronized(this) {
            instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    LocalDatabase::class.java,
                    "MalibinLocalDatabase",
                ).build()
            }.also { instance = it }
        }
    }
}
