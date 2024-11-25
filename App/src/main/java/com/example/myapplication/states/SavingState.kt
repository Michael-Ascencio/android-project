package com.example.myapplication.states

import com.example.myapplication.models.Saving

data class SavingState(
    val listaAhorros: List<Saving> = emptyList()
)