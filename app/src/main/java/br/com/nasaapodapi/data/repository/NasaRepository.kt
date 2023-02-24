package br.com.nasaapodapi.data.repository

import br.com.nasaapodapi.data.network.NetworkResult
import br.com.nasaapodapi.data.api.NasaApi
import br.com.nasaapodapi.data.model.NasaModel


class NasaRepository(private val client: NasaApi) {

    suspend fun getMovieDetail(date: String): NetworkResult<NasaModel> {

        return try {
            val response = client.getNasaApodData(date = date)
            if (response.isSuccessful)
                NetworkResult.Success(response.body() as NasaModel)
            else
                NetworkResult.Error("Network error")
        } catch (e: Exception) {
            NetworkResult.Error("Network error")
        }

//            val response = client.getNasaApodData(date=date)
//            if (response.isSuccessful) {
//                response.body() as NasaModel
//            }
    }
}