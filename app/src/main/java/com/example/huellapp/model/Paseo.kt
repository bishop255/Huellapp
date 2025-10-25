package com.example.huellapp.model

import java.time.LocalDateTime

data class Paseo (
    val id: String = "",
    val perroId: String = "",
    val paseadorId: String = "",
    val fechaHora: LocalDateTime? = null,
    val duracion: Int = 30,
    val estadoPaseo: String = ""

)