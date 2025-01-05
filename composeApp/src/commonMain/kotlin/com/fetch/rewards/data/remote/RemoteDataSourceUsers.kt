package com.shulalab.fetch_rewards.data.remote

import com.shulalab.fetch_rewards.domain.UserModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class RemoteDataSourceUsers(private val httpClient: HttpClient) {


    suspend fun getUsers(): Flow<Result<List<UserModel>>> {
        return flow {
            try {
                val response =
                    httpClient.get("https://fetch-hiring.s3.amazonaws.com/hiring.json")
                        .body<List<UserModel>>()
                emit(Result.success(response))
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        }
    }

}