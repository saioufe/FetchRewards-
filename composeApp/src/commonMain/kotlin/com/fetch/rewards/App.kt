package com.shulalab.fetch_rewards

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.fetch.rewards.ui.screen.UsersScreen
import com.shulalab.shula.theme.AppTheme

@Composable
fun App() = AppTheme {
    MaterialTheme {
        Navigator(UsersScreen())
    }
}