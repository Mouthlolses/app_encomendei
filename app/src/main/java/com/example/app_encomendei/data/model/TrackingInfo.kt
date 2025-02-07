package com.example.app_encomendei.data.model

data class TrackingInfo(
    val code: String,     // Código de rastreio
    val status: String,   // Status atual da encomenda
    val date: String,      // Data do último evento
    val location: String    // Localização da encomenda
)
