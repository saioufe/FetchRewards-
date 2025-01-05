package com.fetch.rewards.ui

import com.shulalab.fetch_rewards.domain.UserModel

data class UsersState(

    var users: List<Pair<Int, List<UserModel>>> = emptyList(),

    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isFailure: Boolean = false,
    val errorMessage: String = "",
)

