package com.example.coronavirusstat.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coronavirusstat.R
import com.example.coronavirusstat.model.Country
import com.example.coronavirusstat.ui.adapter.CountryListAdapter
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class MainFragment : Fragment(), SearchView.OnQueryTextListener, CoroutineScope {

    private var searchFor = ""

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.countries.observe(viewLifecycleOwner, Observer { countries ->
            countries?.let { setRecyclerViewList(countries) }
        })

        viewModel.showProgressBar.observe(viewLifecycleOwner, Observer { showProgress ->
            progressBar.visibility = when (showProgress) {
                true -> View.VISIBLE
                else -> View.INVISIBLE
            }
        })

        searchView.setOnQueryTextListener(this)

    }

    private fun setRecyclerViewList(countries: List<Country>) {
        val adapter =
            CountryListAdapter(countryList = countries, clickListener = recyclerClickListener)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                LinearLayoutManager.VERTICAL
            )
        )
        recyclerView.adapter = adapter
    }

    private val recyclerClickListener = object : CountryListAdapter.OnClickListener {
        override fun onClickItem() {
            Toast.makeText(activity, "Country item Clicked!", Toast.LENGTH_SHORT).show()
        }
    }

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
