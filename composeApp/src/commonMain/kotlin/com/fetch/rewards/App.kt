package com.shulalab.fetch_rewards

import cafe.adriel.voyager.navigator.Navigator

@Composable
fun App() = AppTheme {
    MaterialTheme {
        Navigator(SignUpScreen)
    }
}