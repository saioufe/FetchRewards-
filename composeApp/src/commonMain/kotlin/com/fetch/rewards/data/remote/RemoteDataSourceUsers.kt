package com.shulalab.fetch_rewards.data.remote

import com.shulalab.fetch_rewards.domain.UserModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class RemoteDataSourceUsers(private val httpClient: HttpClient) {


    suspend fun getUsers(): Flow<Result<List<Pair<Int, List<UserModel>>>>> {
        return flow {
            try {
                // Fetch the raw list of users
                val response: List<UserModel> = httpClient
                    .get("https://fetch-hiring.s3.amazonaws.com/hiring.json")
                    .body()

                // Process the list: filter, sort, and group
                val processedData = response
                    .filter { !it.name.isNullOrBlank() } // Filter out items with null or blank `name`
                    .sortedWith(compareBy({ it.listId }, { it.name })) // Sort by `listId` and `name`
                    .groupBy { it.listId } // Group by `listId`
                    .toList() // Convert map to list of pairs (listId, list of items)

                emit(Result.success(processedData)) // Emit the processed data
            } catch (e: Exception) {
                emit(Result.failure(e)) // Emit failure if an exception occurs
            }
        }
    }


}