package com.malibin.study.github.presentation

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.malibin.study.github.data.repository.injector.DefaultGithubProfileRepositoryInjector
import com.malibin.study.github.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels { MainViewModelFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
            .apply {
                viewModel = mainViewModel
                lifecycleOwner = this@MainActivity
            }
            .also { this.binding = it }
        setContentView(binding.root)
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
