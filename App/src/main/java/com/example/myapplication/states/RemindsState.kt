package com.example.myapplication.states

import com.example.myapplication.models.Reminds

data class RemindsState(
    val listaRecordatorios: List<Reminds> = emptyList()
)