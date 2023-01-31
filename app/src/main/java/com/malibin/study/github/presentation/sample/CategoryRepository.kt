package com.malibin.study.github.presentation.sample

import android.content.Context

class TodoManager(
    private val todoMemory: TodoMemory,
) {
    private val currentTodos = mutableListOf<String>()

    fun getTodos(): List<String> {
        return currentTodos.toList()
    }

    fun getTodoHistories(): List<String> {
        return todoMemory.getHistory()
    }

    fun createTodo(todo: String) {
        currentTodos.add(todo)
        todoMemory.create(todo)
    }

    fun finishTodo(todo: String) {
        currentTodos.remove(todo)
        todoMemory.finish(todo)
    }
}

interface TodoMemory {
    fun getHistory(): List<String>

    fun create(todo: String)

    fun finish(todo: String)
}

class TodoMemoryImpl(context: Context) : TodoMemory {
    override fun getHistory(): List<String> {
        TODO("Not yet implemented")
    }

    override fun create(todo: String) {
        TODO("Not yet implemented")
    }

    override fun finish(todo: String) {
        TODO("Not yet implemented")
    }
}
