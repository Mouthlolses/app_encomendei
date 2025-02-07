package com.example.app_encomendei.data.remote

import com.example.app_encomendei.data.model.TrackingInfo
import retrofit2.http.GET
import retrofit2.http.Path

interface CorreiosApi {
    @GET("rastreamento/v1/{trackingCode}")
    suspend fun getTackingInfo(@Path("trackingCode") code: String): TrackingInfo
}




//@GET → Define a requisição HTTP GET.
//@Path("trackingCode") → Substitui {trackingCode} na URL pelo código real.
//suspend fun → Usa Kotlin Coroutines para chamadas assíncronas.
