package com.example.app_encomendei.data.repository

import com.example.app_encomendei.data.model.TrackingInfo
import com.example.app_encomendei.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


//Aqui Chamamos a API e tratamos os dados

class TrackingRepository {
    suspend fun getTracking(code: String) : TrackingInfo? {
        return try {
            withContext(Dispatchers.IO) {      //Dispatchers.IO → Usa uma thread separada para chamadas de rede
                ApiService.api.getTackingInfo(code)
            }
        } catch (e: Exception) {                  //try/catch → Evita que erros da API quebrem o app.
            null   // Tratar erro adequadamente
        }
    }
}