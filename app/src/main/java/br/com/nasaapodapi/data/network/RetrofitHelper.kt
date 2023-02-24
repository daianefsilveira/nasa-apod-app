package br.com.nasaapodapi.data.network

import br.com.nasaapodapi.data.api.NasaApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun getRetrofitInstance(): NasaApi {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.nasa.gov/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(NasaApi::class.java)
}
