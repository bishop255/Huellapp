package com.example.huellapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
@Entity(tableName = "paseos")
data class Paseo(
    @PrimaryKey
    val id: String,
    val paseadorNombre: String,
    val perroNombre: String,
    val fecha: String,
    val hora: String,
    val duracion: Int,
    val estado: EstadoPaseo,
    val perroId: Int ,
)


enum class EstadoPaseo {
    PROGRAMADO, EN_CURSO, COMPLETADO, CANCELADO
}
