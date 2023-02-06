package com.malibin.study.github.presentation.sample

import com.google.common.truth.Truth.assertThat
import com.malibin.study.github.utils.InstantTaskExecutorExtension
import com.malibin.study.github.utils.MainCoroutineExtension
import com.malibin.study.github.utils.getOrAwaitValue
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.RegisterExtension

internal class TestViewModelTest {

    companion object {
        @JvmStatic
        @RegisterExtension
        val instantTaskExecutorExtension = InstantTaskExecutorExtension()
    }

    private lateinit var countMemory: CountMemory
    private lateinit var testViewModel: TestViewModel

    @BeforeEach
    fun setUp() {
        countMemory = mockk<CountMemory>(relaxed = true)
        testViewModel = TestViewModel(countMemory)
    }

    @Test
    fun test1() {
        // when
        testViewModel.increase()

        // then
        assertAll(
            { assertThat(testViewModel.count.value).isEqualTo(1) },
            { verify(exactly = 1) { countMemory.save("increase") } } // increase 가 한 번 호출되었다
        )

    }


}

@OptIn(ExperimentalCoroutinesApi::class)
internal class TestViewModelWithCoroutineTest {

    companion object {
        @JvmStatic
        @RegisterExtension
        val instantTaskExecutorExtension = InstantTaskExecutorExtension()

        @JvmStatic
        @RegisterExtension
        val mce = MainCoroutineExtension()
    }

    private lateinit var countMemory: CountMemoryWithCoroutine
    private lateinit var testViewModel: TestViewModelWithCoroutine

    @BeforeEach
    fun setUp() {
        countMemory = mockk<CountMemoryWithCoroutine>(relaxed = true)
        testViewModel = TestViewModelWithCoroutine(countMemory)
    }

    @Test
    fun test1() = runBlocking {
        // when
        testViewModel.increase()

        // then
        assertAll(
            { assertThat(testViewModel.count.getOrAwaitValue()).isEqualTo(1) },
            { coVerify(exactly = 1) { countMemory.save("increase") } } // increase 가 한 번 호출되었다
        )

    }


}