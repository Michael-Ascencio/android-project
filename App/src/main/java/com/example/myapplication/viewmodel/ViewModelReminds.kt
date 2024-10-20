package com.example.myapplication.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.Reminds
import com.example.myapplication.room.DataBaseRemindDao
import com.example.myapplication.states.RemindsState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ViewModelReminds (
    private val dao: DataBaseRemindDao): ViewModel()
{
    var state = mutableStateOf(RemindsState())
    private set

    init {
        viewModelScope.launch {
            dao.obtenerRecordatorios().collectLatest { recordatorios ->
                state.value = state.value.copy(listaRecordatorios = recordatorios)
            }
        }
    }

    fun agregarRecordatorio(recordatorio: Reminds) = viewModelScope.launch {
        dao.agregarRecordatorio(recordatorio = recordatorio)
    }

    fun actualizarRecordatorio(recordatorio: Reminds) = viewModelScope.launch {
        dao.actualizarRecordatorio(recordatorio = recordatorio)
    }

    fun eliminarRecordatorio(recordatorio: Reminds) = viewModelScope.launch {
        dao.eliminarRecordatorio(recordatorio = recordatorio)
    }
}