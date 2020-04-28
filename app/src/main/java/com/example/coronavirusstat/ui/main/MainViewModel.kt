package com.example.coronavirusstat.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.example.coronavirusstat.model.Country
import com.example.coronavirusstat.network.CountriesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

@ExperimentalCoroutinesApi
class MainViewModel : ViewModel() {

    private val _countries = MutableLiveData<List<Country>>()

    val countries: LiveData<List<Country>>
        get() = _countries

    private val _showProgressBar = MutableLiveData<Boolean>()

    val showProgressBar: LiveData<Boolean>
        get() = _showProgressBar

    init {
        loadAllCountries()
    }

    fun loadCountriesBySearch(letters: String) {
        viewModelScope.launch {
            getCountriesBySearch(letters)
                .onStart { _showProgressBar.value = true }
                .onCompletion { _showProgressBar.value = false }
                .catch { exception ->
                    Log.e(
                        "${MainViewModel::class.java.name}: loadCountriesBySearch",
                        "Error fetch data: $exception"
                    )
                }
                .collect { list -> _countries.value = list }
        }
    }

    // this call was separated for demonstrations purposes only.
    // in production this api call can be merged with the call below -
    // based on the user's search we could just apply filter on the list.
    private fun getCountriesBySearch(letters: String): Flow<List<Country>> {
        return flow {
            val list = CountriesApi.instanceServiceApi.getCountries(false)
                .map { country -> mapCountryObject(country) }
                .filter { it.country?.toLowerCase(Locale.getDefault())?.contains(letters) ?: false }
            emit(list)
        }.flowOn(Dispatchers.IO)

    }

    private fun loadAllCountries() {
        viewModelScope.launch {
            val countryList = getCountryAsFlow().singleOrNull()
            _countries.value = countryList
        }
    }

    private fun getCountryAsFlow(): Flow<List<Country>> {
        return flow { emit(CountriesApi.instanceServiceApi.getCountries(true)) }
            .onStart { _showProgressBar.value = true }
            .onCompletion { _showProgressBar.value = false }
            .catch { exception ->
                Log.e(
                    "${MainViewModel::class.java.name}: loadAllCountries",
                    "Error fetch data: $exception"
                )
            }
    }

    // heavy operations on the object ;-)
    private fun mapCountryObject(country: Country): Country {
        return country.apply {
            this.country = "${country.country} added by search"
        }
    }
}
