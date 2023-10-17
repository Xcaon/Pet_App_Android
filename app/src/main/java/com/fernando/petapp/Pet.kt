package com.fernando.petapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Defino el objeto Perro
@Entity
data class Pet(
    @PrimaryKey(autoGenerate = true) var id: Int? = 0,
    @ColumnInfo(name = "PrimerNombre") val nombre: String,
    @ColumnInfo(name = "Raza") val raza: String,
    @ColumnInfo(name = "url") val imagenUrl: String?
)