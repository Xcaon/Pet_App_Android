package com.fernando.petapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    // Esto es una consulta
    @Query("SELECT * FROM pet")
    fun getAll(): List<Pet>

    @Insert
    fun insertarPerro(perros: List<Pet>)

    @Delete
    fun delete(pet: Pet)

}