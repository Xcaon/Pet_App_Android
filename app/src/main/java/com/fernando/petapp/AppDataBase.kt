package com.fernando.petapp

import androidx.room.Database
import androidx.room.RoomDatabase

// Definimos la base de datos, que es el objeto que vamos a instanciar para usar la bd
@Database (entities = [Pet::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}