package com.malibin.study.github.presentation

import com.malibin.study.github.utils.InstantTaskExecutorExtension
import com.malibin.study.github.utils.MainCoroutineExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.extension.RegisterExtension

@OptIn(ExperimentalCoroutinesApi::class)
internal class MainViewModelTest {

    companion object {
        @JvmField
        @RegisterExtension
        val instantTaskExecutorExtension = InstantTaskExecutorExtension()

        @JvmField
        @RegisterExtension
        val coroutineExtension = MainCoroutineExtension()
    }

}
