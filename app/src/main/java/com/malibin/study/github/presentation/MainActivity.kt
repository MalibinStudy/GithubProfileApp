package com.malibin.study.github.presentation

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.malibin.study.github.R
import com.malibin.study.github.data.repository.injector.DefaultGithubProfileRepositoryInjector

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels { MainViewModelFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

class MainViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            MainViewModel::class.java -> {
                MainViewModel(
                    DefaultGithubProfileRepositoryInjector
                        .providesDefaultGithubProfileRepository(context)
                )
            }
            else -> throw IllegalArgumentException("cannot find viewModelClass of $modelClass")
        } as T
    }
}
