package com.shulalab.fetch_rewards.data.repository

import com.shulalab.fetch_rewards.data.remote.RemoteDataSourceUsers
import com.shulalab.fetch_rewards.domain.UserModel
import kotlinx.coroutines.flow.Flow

class UsersRepositoryImpl(
    val remoteDataSourceUsers: RemoteDataSourceUsers
) : UsersRepository {


    override suspend fun getUsers(): Flow<Result<List<UserModel>>> {
        return remoteDataSourceUsers.getUsers()
    }


}