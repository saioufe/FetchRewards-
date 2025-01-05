package com.fetch.rewards.ui.viewModel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.fetch.rewards.ui.UsersEvent
import com.fetch.rewards.ui.UsersState
import com.shulalab.fetch_rewards.data.repository.UsersRepository
import com.shulalab.fetch_rewards.domain.UserModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsersViewModel(
    private val repository: UsersRepository,
) : ScreenModel {
    private val _state = MutableStateFlow(UsersState())
    val state = _state.asStateFlow()


    fun onEvent(event: UsersEvent) {
        when (event) {
            is UsersEvent.GetUsers -> getUsers()
        }
    }




    private fun getUsers() {
        if (_state.value.isLoading) return

        _state.update {
            it.copy(
                isLoading = true,
                isSuccess = false,
                isFailure = false,
                users = emptyList()
            )
        }

        screenModelScope.launch {
            repository.getUsers().collect { result ->
                result.onSuccess { users ->

                    Napier.d("saioufe this is the users -> $users")
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isSuccess = true,
                            users = users
                        )
                    }
                }.onFailure { exception ->
                    Napier.d("saioufe error fetching -> ${exception.message}")

                    _state.update {
                        it.copy(
                            isLoading = false,
                            isFailure = true,
                            errorMessage = exception.message ?: "Unknown error"
                        )
                    }
                }
            }
        }
    }


}