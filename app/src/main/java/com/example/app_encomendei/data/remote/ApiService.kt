package com.example.app_encomendei.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Criando Singleton para configurar Retrofit

object ApiService {
    private const val BASE_URL = "https://api.correios.com.br/"  // Base URL fictícia

    val api: CorreiosApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CorreiosApi::class.java)
    }
}