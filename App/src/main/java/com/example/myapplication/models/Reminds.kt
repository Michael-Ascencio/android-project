package com.example.myapplication.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recordatorios")
data class Reminds (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "titulo")
    val titulo: String = "",
    @ColumnInfo(name = "repetir")
    val repetir: Boolean,
    @ColumnInfo(name = "imagen")
    val imagen: String = "",
    @ColumnInfo(name = "descripcion_corta")
    val descripcion_corta: String = "",
    @ColumnInfo(name = "descripcion_larga")
    val descripcion_larga: String = "",
    @ColumnInfo(name = "frecuencia")
    val frecuencia: String = "",
    @ColumnInfo(name = "fecha")
    val fecha: String = "",
    @ColumnInfo(name = "hora")
    val hora: String = "",
    @ColumnInfo(name = "favorito")
    val favorito: Boolean,
    @ColumnInfo(name = "borrado")
    val borrado: Boolean,
)