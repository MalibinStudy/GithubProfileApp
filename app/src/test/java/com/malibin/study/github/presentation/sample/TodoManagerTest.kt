package com.malibin.study.github.presentation.sample

import com.google.common.truth.Truth.assertThat
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * 안에 있는 구현들을 전부 다 아는 테스트 -> 화이트 박스 테스트
 */
internal class TodoManagerTest {

    @Test
    fun ddd() {
        // given
        // 가짜 객체를 만든다 -> mock
        // 그래서 라이브러리 이름도 mockk : 순수 코틀린으로 이루어짐
        val todoMemory = mockk<TodoMemory>()

        // 모든 todoMemory는 ~을 반환한다
        every { todoMemory.getHistory() } returns listOf("todo1", "todo2")


        // 어떤 기능을 했는지 내부적으로 구현도 할 수 있다
        //

        /*val fakeTodoMemory = object : TodoMemory {

            private val list = mutableListOf<String>()

            override fun getHistory(): List<String> {
                return list
            }

            override fun create(todo: String) {
                this.list.add(todo)
            }

            override fun finish(todo: String) {

            }
        }*/

        //val todoManager = TodoManager(fakeTodoMemory)
        //todoManager.createTodo("todo1")
        //todoManager.createTodo("todo2")

        val todoManager = TodoManager(todoMemory)

        // when
        val actualHistories = todoManager.getTodoHistories()

        // then
        assertThat(actualHistories.size).isEqualTo(2)
        assertThat(actualHistories) // 똑같은 값들이 들어 있음을 확인
            .containsExactlyElementsIn(listOf("todo1", "todo2"))
            .inOrder()

    }

    /** createTodo 할 때 어떤 동작을 해야하는지 mockk에게 알려주어야 한다
     * 하지만 relaxed = true 키워드를 쓰면 안해도 됨!
     * */
    @Test
    fun ddd3() {
        // given
        val todoMemory = mockk<TodoMemory>(relaxed = true)

        val todoManager = TodoManager(todoMemory)

//        every { todoMemory.create("2") } throws java.lang.IllegalArgumentException("")
//        every { todoMemory.create("1") } just Runs

        todoManager.createTodo("1")
        todoManager.createTodo("2") // 여기서 throw

        // when
        val actualTodos = todoManager.getTodos()

        // then
        assertAll(
            { assertThat(actualTodos).isEqualTo(listOf("1", "2")) },
            { verify(exactly = 1) { todoMemory.create("1") } }, // 1을 넣은 것이 한 번 발생했다
            { verify(exactly = 1) { todoMemory.create(any()) } } // 무적 any !!
        )
    }


    /** 코루틴을 쓴다고 가정해보자
     * suspend 는 코루틴 scope 내에서 실행을 해줘야한다
     * every는 코루틴 scope가 아니기 때문에 못쓴다 ㅠㅠ
     * 그러기 때문에 co를 붙이자! coEvery
     * */
    @Test
    fun ddd5() = runBlocking {
        // given
        val todoMemory = mockk<TodoMemory>()

        coEvery { todoMemory.getHistory() } returns listOf("todo1", "todo2")

        val todoManager = TodoManager(todoMemory)

        // when
        val actualHistories = todoManager.getTodoHistories()

        // then
        assertThat(actualHistories.size).isEqualTo(2)
        assertThat(actualHistories) // 똑같은 값들이 들어 있음을 확인
            .containsExactlyElementsIn(listOf("todo1", "todo2"))
            .inOrder()

    }
}