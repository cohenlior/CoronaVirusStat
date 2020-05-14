package com.example.coronavirusstat.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coronavirusstat.R
import com.example.coronavirusstat.ui.main.MainFragment
import kotlinx.coroutines.*

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment())
                .commitNow()
        }
    }
}
