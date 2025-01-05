package com.fetch.rewards.di

import com.fetch.rewards.ui.viewModel.UsersViewModel
import com.shulalab.fetch_rewards.data.remote.RemoteDataSourceUsers
import com.shulalab.fetch_rewards.data.repository.UsersRepository
import com.shulalab.fetch_rewards.data.repository.UsersRepositoryImpl
import org.koin.dsl.module

fun usersModule() = module {

    single { RemoteDataSourceUsers(get()) }

    single<UsersRepository> { UsersRepositoryImpl(remoteDataSourceUsers = get()) }

    single { UsersViewModel(repository = get()) }
}