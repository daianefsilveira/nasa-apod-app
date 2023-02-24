package br.com.nasaapodapi.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.nasaapodapi.data.network.NetworkResult.*
import br.com.nasaapodapi.data.model.NasaModel
import br.com.nasaapodapi.data.network.NetworkResult
import br.com.nasaapodapi.data.repository.NasaRepository
import kotlinx.coroutines.launch

class NasaViewModel(
    private val nasaRepository: NasaRepository
): ViewModel() {

    val nasaApodData = MutableLiveData<NasaModel>()
    private val error = MutableLiveData<String>()

    fun getNasaApodData(date: String) {
        viewModelScope.launch {
            val result = nasaRepository.getMovieDetail(date)
            when (result) {
                is Success -> nasaApodData.postValue(result.data as NasaModel?)
                is Error -> error.postValue(result.errorMessage)
            }
        }
    }

    class NasaViewModelFactory (private val nasaRepository: NasaRepository) :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            NasaViewModel(nasaRepository) as T
    }
}
