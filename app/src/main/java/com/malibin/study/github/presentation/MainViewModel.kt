package com.malibin.study.github.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malibin.study.github.domain.profile.GithubProfile
import com.malibin.study.github.domain.repository.GithubProfileRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val profileRepository: GithubProfileRepository,
) : ViewModel() {

    val githubId = MutableLiveData("")

    private val _githubProfile = MutableLiveData<GithubProfile?>()
    val githubProfile: LiveData<GithubProfile?> = _githubProfile

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    fun loadGithubProfile() {
        viewModelScope.launch {
            _isLoading.value = true

            profileRepository.getGithubProfile(githubId.value.orEmpty())
                .onSuccess { _githubProfile.value = it }
                .onFailure {
                    _githubProfile.value = null
                    _isError.value = true
                }
            _isLoading.value = false
        }
    }
}
