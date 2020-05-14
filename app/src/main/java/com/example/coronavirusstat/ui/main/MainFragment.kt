package com.example.coronavirusstat.ui.main

import android.os.Bundle
import android.transition.Fade
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.coronavirusstat.R
import com.example.coronavirusstat.model.Country
import com.example.coronavirusstat.ui.detail.CountryDetailFragment
import com.example.coronavirusstat.utils.hideKeyboard
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment.view.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


@ExperimentalCoroutinesApi
class MainFragment : Fragment(), CoroutineScope, SearchView.OnQueryTextListener {

    private var searchFor = ""

    private val viewModel: MainViewModel by viewModels()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.main_fragment, container, false)

        viewModel.countries.observe(viewLifecycleOwner, Observer { countries ->
            countries?.let { setViewPager(countries) }
        })

        viewModel.showProgressBar.observe(viewLifecycleOwner, Observer { showProgress ->
            progressBar.visibility = when (showProgress) {
                true -> View.VISIBLE
                else -> View.INVISIBLE
            }
        })

        view.searchView.setOnQueryTextListener(this)
        return view
    }

    private fun setViewPager(countries: List<Country>) {
        val adapter = CountryPagerAdapter(
            countries,
            countryPagerClickListener
        )
        countryViewPager.adapter = adapter
        countryViewPager.setPageTransformer(ZoomOutPageTransformer())
        hideKeyboard()
    }

    private var countryPagerClickListener = object : CountryPagerAdapter.OnClickListener {
        override fun onClickItem(
            country: Country,
            imageCountry: ImageView
        ) {

            val detailFragment: Fragment = CountryDetailFragment.newInstance(country)
            detailFragment.sharedElementEnterTransition = TransitionInflater.from(activity)
                .inflateTransition(R.transition.change_image_transform)
            detailFragment.sharedElementReturnTransition = TransitionInflater.from(activity)
                .inflateTransition(R.transition.change_image_transform)
            detailFragment.enterTransition = Fade()
            exitTransition = Fade()

            activity!!.supportFragmentManager
                .beginTransaction()
                .setReorderingAllowed(true)
                .addSharedElement(imageCountry, "MyTransition")
                .replace(R.id.container, detailFragment)
                .addToBackStack(null)
                .commit()
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