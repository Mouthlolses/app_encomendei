package com.example.app_encomendei.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_encomendei.data.model.TrackingInfo
import com.example.app_encomendei.data.repository.TrackingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class TrackingViewModel : ViewModel() {

    private val repository = TrackingRepository()

    private val _trackingInfo = MutableStateFlow<TrackingInfo?>(null)
    open val trackingInfo: StateFlow<TrackingInfo?> = _trackingInfo

    fun fetchTrackin(code: String) {
        viewModelScope.launch {
            _trackingInfo.value = repository.getTracking(code)
        }
    }
}


//MutableStateFlow → Atualiza a UI com os dados em tempo real.
//fetchTracking(code) → Busca os dados do repositório.