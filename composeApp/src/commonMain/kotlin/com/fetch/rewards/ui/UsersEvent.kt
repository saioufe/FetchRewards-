package com.fetch.rewards.ui

import androidx.compose.ui.unit.TextUnit



sealed interface UsersEvent {
    data object GetUsers : UsersEvent


}