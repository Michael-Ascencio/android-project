package com.example.myapplication.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.Reminds
import com.example.myapplication.models.Saving
import com.example.myapplication.room.DataBaseRemindDao
import com.example.myapplication.room.DataBaseSavingDao
import com.example.myapplication.states.RemindsState
import com.example.myapplication.states.SavingState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ViewModelDatabase(
    private val daoRemind: DataBaseRemindDao,
    private val daoSaving: DataBaseSavingDao
) : ViewModel() {

    var remindState = mutableStateOf(RemindsState())
        private set

    var savingState = mutableStateOf(SavingState())
        private set

    // State for favorite reminders
    var favoritosStateRecordatorio = mutableStateOf(listOf<Reminds>())
        private set

    // State for favorite reminders
    var favoritosStateAhorro = mutableStateOf(listOf<Saving>())
        private set

    init {
        // Initialize and collect Reminds
        viewModelScope.launch {
            daoRemind.obtenerRecordatorios().collectLatest { recordatorios ->
                remindState.value = remindState.value.copy(listaRecordatorios = recordatorios)
            }
        }

        // Initialize and collect favorite Reminds
        viewModelScope.launch {
            daoRemind.obtenerFavoritos().collectLatest { favoritosAhorro ->
                favoritosStateRecordatorio.value = favoritosAhorro
            }
        }

        // Initialize and collect Savings
        viewModelScope.launch {
            daoSaving.obtenerAhorros().collectLatest { ahorros ->
                savingState.value = savingState.value.copy(listaAhorros = ahorros)
            }
        }

        // Initialize and collect favorite ahorro
        viewModelScope.launch {
            daoSaving.obtenerFavoritos().collectLatest { favoritos ->
                favoritosStateAhorro.value = favoritos
            }
        }
    }

    // Functions for Reminds
    fun agregarRecordatorio(recordatorio: Reminds) = viewModelScope.launch {
        daoRemind.agregarRecordatorio(recordatorio)
    }

    fun actualizarRecordatorio(recordatorio: Reminds) = viewModelScope.launch {
        daoRemind.actualizarRecordatorio(recordatorio)
    }

    fun eliminarRecordatorio(recordatorio: Reminds) = viewModelScope.launch {
        daoRemind.eliminarRecordatorio(recordatorio)
    }

    // Functions for Savings
    fun agregarAhorro(ahorro: Saving) = viewModelScope.launch {
        daoSaving.agregarAhorro(ahorro)
    }

    fun actualizarAhorro(ahorro: Saving) = viewModelScope.launch {
        daoSaving.actualizarAhorro(ahorro)
    }

    fun eliminarAhorro(ahorro: Saving) = viewModelScope.launch {
        daoSaving.eliminarAhorro(ahorro)
    }
}
