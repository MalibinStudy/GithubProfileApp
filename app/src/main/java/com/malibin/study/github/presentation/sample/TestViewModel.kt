package com.malibin.study.github.presentation.sample

import androidx.lifecycle.*
import kotlinx.coroutines.launch

interface CountMemory {
    fun save(history: String)
    fun getHistories(): List<String>
}

class TestViewModel(
    private val countMemory: CountMemory,
) : ViewModel() {

    private val _count = MutableLiveData<Int>(0)
    val count: LiveData<Int> = _count

    private val _histories = MutableLiveData<List<String>>()
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


//=======================================================================================


interface CountMemoryWithCoroutine {
    suspend fun save(history: String)
    suspend fun getHistories(): List<String>
}

class TestViewModelWithCoroutine(
    private val countMemory: CountMemoryWithCoroutine,
) : ViewModel() {

    private val _count = MutableLiveData<Int>(0)
    val count: LiveData<Int> = _count

    private val _histories = MutableLiveData<List<String>>()
    val histories: LiveData<List<String>> = _histories

    fun increase() {
        viewModelScope.launch {
            _count.value = _count.value?.plus(1)
            countMemory.save("increase")
        }
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