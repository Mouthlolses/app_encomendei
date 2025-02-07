package com.example.app_encomendei.data.repository

import com.example.app_encomendei.data.model.PackageStatus
import com.example.app_encomendei.data.model.TrackingInfo
import com.example.app_encomendei.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


//Aqui Chamamos a API e tratamos os dados
class TrackingRepository {
    suspend fun getTracking(code: String): TrackingInfo? {
        return try {
            withContext(Dispatchers.IO) {  // Usando uma thread separada para chamadas de rede
                val response = ApiService.api.getTackingInfo(code)  // Fazendo a chamada para a API

                // Supondo que a API retorne o status como string, mapeamos para o enum PackageStatus
                val status = when (response.status) {
                    "IT_TRANSFER" -> PackageStatus.IT_TRANSFER
                    "DELIVERED" -> PackageStatus.DELIVERED
                    "PENDING" -> PackageStatus.PENDING
                    else -> PackageStatus.UNKNOWN
                }

                // Retorna o TrackingInfo com o status convertido para PackageStatus
                TrackingInfo(
                    code = code,
                    status = status.toString(),
                    location = response.location,
                    date = response.date
                )
            }
        } catch (e: Exception) {  // Tratando erros da API
            null  // Retorna null em caso de erro (você pode personalizar a mensagem de erro)
        }
    }
}