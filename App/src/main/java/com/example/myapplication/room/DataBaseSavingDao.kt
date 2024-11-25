package com.example.myapplication.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.models.Reminds
import com.example.myapplication.models.Saving
import kotlinx.coroutines.flow.Flow

@Dao
interface DataBaseSavingDao {

    @Query("SELECT * FROM ahorros")
    fun obtenerAhorros(): Flow<List<Saving>>

    @Query("SELECT * FROM ahorros WHERE id = :id")
    fun obtenerAhorrosPorId(id: Int): Flow<Saving>

    @Query("SELECT * FROM ahorros WHERE favorito = 1")
    fun obtenerFavoritos(): Flow<List<Saving>>

    @Insert
    suspend fun agregarAhorro(ahorros: Saving)

    @Update
    suspend fun actualizarAhorro(ahorros: Saving)

    @Delete
    suspend fun eliminarAhorro(ahorros: Saving)
}