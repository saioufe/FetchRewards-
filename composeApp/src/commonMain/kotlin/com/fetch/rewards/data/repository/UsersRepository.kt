package com.shulalab.fetch_rewards.data.repository

import com.shulalab.fetch_rewards.domain.UserModel
import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    suspend fun getUsers(): Flow<Result<List<Pair<Int, List<UserModel>>>>>

}