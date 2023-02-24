package br.com.nasaapodapi.data.api

import br.com.nasaapodapi.data.model.NasaModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface NasaService {

    @GET("planetary/apod")
    suspend fun getNasaApodData(
        @Query("api_key") api_key: String = "1uhlnSPYL0PqbTzaYMHCBqdyuSoCmGx1PlM15xZI",
        @Query("date") date: String = "2021-11-25",
    ): Response<NasaModel>

}




