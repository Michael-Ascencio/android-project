package com.example.myapplication.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.models.Reminds
import com.example.myapplication.models.Saving

@Database(
    entities = [Saving::class],
    version = 1,
    exportSchema = false)

abstract class DataBaseSaving: RoomDatabase() {
    abstract fun savingDao(): DataBaseSavingDao
}