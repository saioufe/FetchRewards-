package com.fetch.rewards

import android.app.Application
import android.os.Bundle
import android.speech.SpeechRecognizer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.fetch.rewards.di.usersModule
import com.shulalab.fetch_rewards.App
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AndroidApp : Application() {
    companion object {
        lateinit var INSTANCE: AndroidApp
    }


    override fun onCreate() {
        super.onCreate()


        INSTANCE = this

        startKoin {
            androidLogger()
            androidContext(this@AndroidApp)
            modules(
                usersModule()

            )
        }


    }

}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}