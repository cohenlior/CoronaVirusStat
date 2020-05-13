package com.example.coronavirusstat.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.coronavirusstat.R
import com.example.coronavirusstat.model.Country
import com.example.coronavirusstat.ui.adapter.CountryPagerAdapter
import com.example.coronavirusstat.ui.detail.CountryDetailFragment
import com.example.coronavirusstat.ui.main.MainFragment
import com.example.coronavirusstat.ui.main.MainViewModel
import com.example.coronavirusstat.utils.hideKeyboard
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

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
