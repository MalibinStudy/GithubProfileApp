package com.malibin.study.github.presentation.sample

import com.google.common.truth.Truth.assertThat
import com.malibin.study.github.utils.InstantTaskExecutorExtension
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension

@ExtendWith(MockKExtension::class)
internal class TestViewModelTest {

    @RelaxedMockK
    private lateinit var countMemory: CountMemory
    private lateinit var viewModel: TestViewModel

    @BeforeEach
    fun setUp() {
        viewModel = TestViewModel(countMemory)
    }

    companion object {
        @JvmField
        @RegisterExtension
        val instantTaskExecutorExtension = InstantTaskExecutorExtension()
    }

    @Test
    fun `viewModel Test`() {
        // when
        viewModel.increase()
        val actualCnt = viewModel.count.value
        // then
        assertAll(
            { assertThat(actualCnt).isEqualTo(1) },
            { verify(exactly = 1) { countMemory.save("increase") } }
        )
    }
}
