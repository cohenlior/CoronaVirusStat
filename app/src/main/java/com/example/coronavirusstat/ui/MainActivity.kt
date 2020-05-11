package com.example.coronavirusstat.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.coronavirusstat.R
import com.example.coronavirusstat.model.Country
import com.example.coronavirusstat.ui.adapter.CountryListAdapter
import com.example.coronavirusstat.ui.adapter.CountryPagerAdapter
import com.example.coronavirusstat.ui.main.MainViewModel
import com.example.coronavirusstat.utils.hideKeyboard
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity(), CoroutineScope, SearchView.OnQueryTextListener {

    private var searchFor = ""

    private val viewModel: MainViewModel by viewModels()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        viewModel.countries.observe(this, Observer { countries ->
            countries?.let { setViewPager(countries) }
        })

        viewModel.showProgressBar.observe(this, Observer { showProgress ->
            progressBar.visibility = when (showProgress) {
                true -> View.VISIBLE
                else -> View.INVISIBLE
            }
        })

        searchView.setOnQueryTextListener(this)
    }

    private fun setViewPager(countries: List<Country>) {
        val adapter = CountryPagerAdapter(this, countries)
        countryViewPager.adapter = adapter
        countryViewPager.offscreenPageLimit = 3
        hideKeyboard()
    }

  /*  private val recyclerClickListener = object : CountryListAdapter.OnClickListener {
        override fun onClickItem() {
            Toast.makeText(this@MainActivity, "Country item Clicked!", Toast.LENGTH_SHORT).show()
        }
    }*/

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { viewModel.loadCountriesBySearch(it) }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        val searchText = newText.toString().trim()
        if (searchText == searchFor)
            return true

        searchFor = searchText

        launch {
            delay(1000)
            if (searchText != searchFor)
                return@launch
            newText?.let { viewModel.loadCountriesBySearch(it) }
        }
        return false
    }
}
