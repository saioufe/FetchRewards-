package com.shulalab.fetch_rewards.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    @SerialName("id") var id: Int,
    @SerialName("listId") var listId: Int,
    @SerialName("name") var name: String?
) {
    companion object {
        val empty = UserModel(
            id = 0,
            listId = 0,
            name = null
        )
    }
}