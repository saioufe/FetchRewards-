package com.fetch.rewards.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

fun providehttpClientModule() = module {
    single {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true  // Ignore unexpected fields in JSON
                        isLenient = true          // Allow relaxed JSON parsing
                    },
                    contentType = ContentType.Application.Json // Specify JSON content type
                )
            }
        }
    }
}