package com.malibin.study.github.presentation.sample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

interface CountMemory {
    fun save(history: String)
    fun getHistories(): List<String>
}

class TestViewModel(
    private val countMemory: CountMemory
) : ViewModel() {

    private val _count = MutableLiveData<Int>(0)
    val count: LiveData<Int> = _count

    private val _histories = MutableLiveData<List<String>>(listOf())
    val histories: LiveData<List<String>> = _histories

    fun increase() {
        _count.value = _count.value?.plus(1)
        countMemory.save("increase")
    }

    fun decrease() {
        _count.value = _count.value?.minus(1)
        countMemory.save("decrease")
    }

    fun loadHistories() {
        _histories.value = countMemory.getHistories()
    }
}
