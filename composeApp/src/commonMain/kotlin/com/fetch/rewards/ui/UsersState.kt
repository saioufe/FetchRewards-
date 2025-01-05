package com.fetch.rewards.ui

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.shulalab.fetch_rewards.domain.UserModel

data class UsersState(

    var users: List<UserModel> = emptyList(),

    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isFailure: Boolean = false,
    val errorMessage: String = "",
)

