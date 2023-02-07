package com.malibin.study.github.presentation.sample

import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.malibin.study.github.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(AndroidJUnit4::class)
internal class SampleActivityTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(SampleActivity::class.java)

    @Test
    fun 버튼을_연달아_누르면_기존_문자열에_문자가_덧붙는다() {
        // given: "1"
        onView(withId(R.id.button1)).perform(click())

        // when
        onView(withId(R.id.button2)).perform(click())

        // then
        onView(withId(R.id.textView)).check(matches(withText("12")))
    }
}

@RunWith(Parameterized::class)
class SampleActivityParameterizedTest(
    @IdRes private val buttonResourceId: Int,
    private val expectedString: String,
) {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(SampleActivity::class.java)

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "버튼 {1} 일때")
        fun provideButtonResourceIdsAndExpectedStrings(): Collection<Array<Any>> {
            return listOf(
                arrayOf(R.id.button1, "1"),
                arrayOf(R.id.button2, "2"),
                arrayOf(R.id.button3, "3"),
                arrayOf(R.id.button4, "4"),
            )
        }
    }

    @Test
    fun 버튼을_누르면_버튼에_적힌_숫자가_텍스트뷰에_보인다(){
        // given, when
        onView(withId(buttonResourceId)).perform(click())

        // then
        onView(withId(R.id.textView)).check(matches(withText(expectedString)))
    }
}
