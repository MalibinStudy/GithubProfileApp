package com.malibin.study.github.presentation.sample

import com.google.common.truth.Truth.assertThat
import com.malibin.study.github.utils.InstantTaskExecutorExtension
import com.malibin.study.github.utils.MainCoroutineExtension
import com.malibin.study.github.utils.getOrAwaitValue
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
internal class TestViewModelWithCoroutineTest {
    @RelaxedMockK
    private lateinit var countMemory: CountMemoryWithCoroutine
    private lateinit var viewModel: TestViewModelWithCoroutine

    @BeforeEach
    fun setUpForEachTest() {
        viewModel = TestViewModelWithCoroutine(countMemory)
    }

    @Test
    fun `CoroutineViewModel Test`() = runTest {
        // when
        viewModel.increase()
        val actualCnt = viewModel.count.getOrAwaitValue()
        // then
        assertAll(
            { assertThat(actualCnt).isEqualTo(1) },
            { coVerify(exactly = 1) { countMemory.save("increase") } }
        )
    }

    companion object {
        @JvmField
        @RegisterExtension
        val instantTaskExecutorExtension = InstantTaskExecutorExtension()

        @JvmField
        @RegisterExtension
        val coroutineExtension = MainCoroutineExtension()
    }
}
