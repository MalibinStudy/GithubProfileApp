package com.malibin.study.github.presentation.sample

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class TodoManagerTest {
    @RelaxedMockK
    private lateinit var todoMemory: TodoMemory

    @Test
    fun `직접 가짜 객체를 만들어서 테스트`() {
        // TodoMemory의 구현체가 어떤 일을 하는지 중요하지 않으므로 Context 객체가 필요X
        // 따라서, 가짜 객체를 넣어주고 Unit Test 하자
        val fakeTodoMemory = object : TodoMemory {
            private val list = mutableListOf<String>()
            override fun getHistory(): List<String> {
                return list
            }

            override fun create(todo: String) {
                list.add(todo)
            }

            override fun finish(todo: String) {
            }
        }
        val todoManager = TodoManager(fakeTodoMemory)
        // when
        todoManager.run {
            createTodo("todo1")
            createTodo("todo2")
        }
        val actualHistories = todoManager.getTodoHistories()
        // then
        assertAll(
            { assertThat(actualHistories.size).isEqualTo(2) },
            { assertThat(actualHistories).containsExactlyElementsIn(listOf("todo1", "todo2")) },
            { assertThat(actualHistories).isEqualTo(listOf("todo1", "todo2")) }
        )
    }

    @Test
    fun `mockk으로 가짜 객체를 만들어 테스트`() {
        // given
        // 실제로 직접 가짜 객체를 만들 수도 있지만 mockk 라이브러리를 활용하여 만들 수 있다
        // mockk은 순수 코틀린으로 구성된 mock 라이브러 리 -> 멀티 플랫폼을 지원한다
        // 1) mockk는 mock 객체의 함수가 몇 번 호출됐는지도 알 수 있음
        // 2) mock 객체의 함수의 반환값을 지정할 수 있음
        //      every { (mock 객체의 함수)} returns (원하는 반환값)
        every { todoMemory.getHistory() } returns listOf("todo1", "todo2")
        val todoManager = TodoManager(todoMemory)

        // when
        val actualHistories = todoManager.getTodoHistories()

        // then
        assertAll(
            { assertThat(actualHistories.size).isEqualTo(2) },
            { assertThat(actualHistories).containsExactlyElementsIn(listOf("todo1", "todo2")) },
            { assertThat(actualHistories).isEqualTo(listOf("todo1", "todo2")) }
        )
    }

    @Test
    fun `createTodo()가 호출될 때 todoMemory의 create도 호출 된다`() {
        // mockk는 화이트 박스 테스트를 지원해준다.
        // 화이트 박스 테스트 : 테스트할 녀석의 내부 구현을 모두 아는 상태로 하는 테스트

        // given
        val todoManager = TodoManager(todoMemory)

        // when
        todoManager.run {
            createTodo("t1")
            createTodo("t2")
        }
        val actualHistories = todoManager.getTodos()

        // then
        assertAll(
            { assertThat(actualHistories).isEqualTo(listOf("t1", "t2")) },
            // mockMemory.create("t1") 이 한 번 호출되었는지 검사!!
            { verify(exactly = 1) { todoMemory.create("t1") } },
            { verify(exactly = 2) { todoMemory.create(any()) } }, // 무엇을 넣던지 총 2번 호출했는지 검증
            { verify(exactly = 0) { todoMemory.create("t3") } } // 한 번도 호출되지 않았음을 검증
        )
    }

    @Test
    fun `Coroutine을 활용한 Test`() = runBlocking {
        // given
        val todoMemory = mockk<TodoMemory2>()
        coEvery { todoMemory.getHistory() } returns listOf("todo1", "todo2")
        val todoManager = TodoManager2(todoMemory)

        // when
        val actualHistories = todoManager.getTodoHistories()

        // then
        assertAll(
            { assertThat(actualHistories.size).isEqualTo(2) },
            { assertThat(actualHistories).containsExactlyElementsIn(listOf("todo1", "todo2")) },
            { assertThat(actualHistories).isEqualTo(listOf("todo1", "todo2")) }
        )
    }
}
