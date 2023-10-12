package com.fernando.petapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Defino el objeto Perro
@Entity
data class Pet(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "PrimerNombre") val nombreDelPerro: String?,
    @ColumnInfo(name = "Raza") val razaDelPerro: String?
)