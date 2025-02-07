package com.example.app_encomendei.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_encomendei.data.model.PackageStatus
import com.example.app_encomendei.data.model.TrackingInfo
import com.example.app_encomendei.data.repository.TrackingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

//MutableStateFlow → Atualiza a UI com os dados em tempo real.
//fetchTracking(code) → Busca os dados do repositório.

open class TrackingViewModel : ViewModel() {

    private val repository = TrackingRepository()

    private val _trackingInfo = MutableStateFlow<TrackingInfo?>(null)
    open val trackingInfo: StateFlow<TrackingInfo?> = _trackingInfo

    fun fetchTrackin(code: String) {
        viewModelScope.launch {
            // Aqui, recuperamos os dados da API
            val trackingResponse = repository.getTracking(code)

            // Supondo que a API retorne um status como string (por exemplo: "IT_TRANSFER")
            val status = when (trackingResponse?.status) {
                "IT_TRANSFER" -> PackageStatus.IT_TRANSFER
                "DELIVERED" -> PackageStatus.DELIVERED
                "PENDING" -> PackageStatus.PENDING
                else -> PackageStatus.UNKNOWN
            }

            // Atualizamos o _trackingInfo com o novo status e outras informações
            if (trackingResponse != null) {
                _trackingInfo.value = TrackingInfo(
                    code = trackingResponse.code,
                    status = trackingResponse.status,
                    location = trackingResponse.location,
                    date = trackingResponse.date,
                )
            }
        }
    }
}

