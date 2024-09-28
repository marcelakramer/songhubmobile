package com.example.songhub.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.songhub.DAO.SongDAOLocal
import com.example.songhub.model.Song

@Database(entities = [Song::class], version = 1)
abstract class SongDatabase : RoomDatabase() {
    abstract fun songDao(): SongDAOLocal
}