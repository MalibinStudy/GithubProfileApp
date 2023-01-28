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

    private val _githubProfile = MutableLiveData<GithubProfileUiState>()
    val githubProfile: LiveData<GithubProfileUiState> = _githubProfile

    fun loadGithubProfile() {
        viewModelScope.launch {
            _githubProfile.value = GithubProfileUiState.Loading

            profileRepository.getGithubProfile(githubId.value.orEmpty())
                .onSuccess { _githubProfile.value = GithubProfileUiState.Success(it) }
                .onFailure { _githubProfile.value = GithubProfileUiState.LoadFailed }
        }
    }
}

sealed interface GithubProfileUiState {
    object Loading : GithubProfileUiState
    object LoadFailed : GithubProfileUiState
    data class Success(val githubProfile: GithubProfile) : GithubProfileUiState
}
