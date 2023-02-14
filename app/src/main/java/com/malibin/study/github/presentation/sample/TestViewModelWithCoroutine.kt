package com.malibin.study.github.presentation.sample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

interface CountMemoryWithCoroutine {
    suspend fun save(history: String)
    suspend fun getHistories(): List<String>
}

class TestViewModelWithCoroutine(
    private val countMemory: CountMemoryWithCoroutine
) : ViewModel() {

    private val _count = MutableLiveData<Int>(0)
    val count: LiveData<Int> = _count

    private val _histories = MutableLiveData<List<String>>(listOf())
    val histories: LiveData<List<String>> = _histories

    fun increase() =
        viewModelScope.launch {
            _count.value = _count.value?.plus(1)
            countMemory.save("increase")
        }

    fun decrease() {
        viewModelScope.launch {
            _count.value = _count.value?.minus(1)
            countMemory.save("decrease")
        }
    }

    fun loadHistories() {
        viewModelScope.launch {
            _histories.value = countMemory.getHistories()
        }
    }
}
