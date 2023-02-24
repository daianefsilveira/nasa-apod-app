package br.com.nasaapodapi.data.model

data class NasaModel(
    val date: String,
    val explanation: String,
    val hdurl: String,
    val mediaType: String,
    val version: String,
    val title: String,
    val url: String,
)