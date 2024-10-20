package com.example.myapplication.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.models.Reminds

@Database(
    entities = [Reminds::class],
    version = 1,
    exportSchema = false)

abstract class DataBaseRemind: RoomDatabase() {
    abstract fun recordatorioDao(): DataBaseRemindDao
}