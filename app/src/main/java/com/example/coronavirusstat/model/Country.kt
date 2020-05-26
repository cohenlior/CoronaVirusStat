package com.example.coronavirusstat.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Country(

	@field:SerializedName("continent")
	val continent: String? = null,

	@field:SerializedName("country")
    var country: String? = null,

	@field:SerializedName("cases")
	val cases: Int? = null,

	@field:SerializedName("critical")
	val critical: Int? = null,

	@field:SerializedName("active")
	val active: Int? = null,

	@field:SerializedName("testsPerOneMillion")
	val testsPerOneMillion: Int? = null,

	@field:SerializedName("recovered")
	val recovered: Int? = null,

	@field:SerializedName("tests")
	val tests: Int? = null,

	@field:SerializedName("countryInfo")
	val countryInfo: CountryInfo? = null,

	@field:SerializedName("updated")
	val updated: Long? = null,

	@field:SerializedName("deaths")
	val deaths: Int? = null,

	@field:SerializedName("todayCases")
	val todayCases: Int? = null,

	@field:SerializedName("todayDeaths")
	val todayDeaths: Int? = null
) : Parcelable

@Parcelize
data class CountryInfo(

	@field:SerializedName("flag")
	val flag: String? = null,

	@field:SerializedName("_id")
	val id: Int? = null,

	@field:SerializedName("iso2")
	val iso2: String? = null,

	@field:SerializedName("lat")
	val lat: Double? = null,

	@field:SerializedName("long")
	val jsonMemberLong: Double? = null,

	@field:SerializedName("iso3")
	val iso3: String? = null
) : Parcelable
