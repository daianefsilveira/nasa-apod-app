package br.com.nasaapodapi.di

import br.com.nasaapodapi.data.api.NasaService
import br.com.nasaapodapi.data.repository.NasaRepository
import br.com.nasaapodapi.ui.viewmodel.NasaViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModules = module {

    viewModel {
        NasaViewModel(
            nasaRepository = get()
        )
    }

    factory {
        NasaRepository(
            client = get()
        )
    }

    single {
        getRetrofitInstance()
    }
}

fun getRetrofitInstance(): NasaService {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.nasa.gov/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(NasaService::class.java)
}

