package com.malibin.study.github

import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleInstrumentedTest {
    @Test
    fun `application_context로_패키지_이름을_반환할_수_있다`() {
        // given
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val expectedPackageName = "com.malibin.study.github"
        // when
        val actualPackageName = appContext.packageName
        // then
        assertThat(actualPackageName).isEqualTo(expectedPackageName)
    }
}
