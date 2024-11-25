package com.example.myapplication.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ahorros")
data class Saving (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "titulo")
    val titulo: String = "",
    @ColumnInfo(name = "imagen")
    val imagen: String = "",
    @ColumnInfo(name = "descripcion_corta")
    val descripcion_corta: String = "",
    @ColumnInfo(name = "descripcion_larga")
    val descripcion_larga: String = "",
    @ColumnInfo(name = "frecuencia")
    val frecuencia: String = "",
    @ColumnInfo(name = "monto_objetivo")
    val monto_objetivo: String = "",
    @ColumnInfo(name = "monto_actual")
    val monto_actual: String = "",
    @ColumnInfo(name = "fecha_limite")
    val fecha_limite: String = "",
    @ColumnInfo(name = "fecha_inicial")
    val fecha_inicial: String = "",
    @ColumnInfo(name = "hora")
    val hora: String = "",
    @ColumnInfo(name = "favorito")
    val favorito: Boolean,
    @ColumnInfo(name = "borrado")
    val borrado: Boolean,
)