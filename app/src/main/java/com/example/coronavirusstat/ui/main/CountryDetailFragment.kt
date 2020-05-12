package com.example.coronavirusstat.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coronavirusstat.R
import com.example.coronavirusstat.model.Country
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.detail_fragment.*
import kotlinx.coroutines.*

@ExperimentalCoroutinesApi
class CountryDetailFragment : Fragment() {

    private var country: Country? = null

    companion object {
        const val ARG_COUNTRY = "country"

        fun newInstance(country: Country) =
            CountryDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_COUNTRY, country)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.apply {
            country = getParcelable(ARG_COUNTRY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        countryName.text = "${country?.country}"
        todayCases.text = "${country?.todayCases}"
        recovered.text = "${country?.recovered}"

        country?.countryInfo?.flag.let {
            Picasso.get()
                .load(it)
                .centerCrop()
                .fit()
                .into(imageCountry)
        }
    }
}
