package com.malibin.study.github

import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class ExampleInstrumentedTest {
    @Test
    fun `올바른_패키지_명을_반환한다`() {
        //when
        val actualResult = InstrumentationRegistry.getInstrumentation().targetContext
        //then
        assertEquals("com.malibin.study.github", actualResult.packageName)
    }
}
