package com.example.myapplication.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.models.Reminds
import kotlinx.coroutines.flow.Flow

@Dao
interface DataBaseRemindDao {

    @Query("SELECT * FROM recordatorios")
    fun obtenerRecordatorios(): Flow<List<Reminds>>

    @Query("SELECT * FROM recordatorios WHERE id = :id")
    fun obtenerRecordatorioPorId(id: Int): Flow<Reminds>

    @Query("SELECT * FROM recordatorios WHERE favorito = 1")
    fun obtenerFavoritos(): Flow<List<Reminds>>

    @Insert
    suspend fun agregarRecordatorio(recordatorio: Reminds)

    @Update
    suspend fun actualizarRecordatorio(recordatorio: Reminds)

    @Delete
    suspend fun eliminarRecordatorio(recordatorio: Reminds)
}