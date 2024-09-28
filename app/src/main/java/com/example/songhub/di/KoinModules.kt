package com.example.songhub.di

import androidx.room.Room
import com.example.songhub.database.SongDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.example.songhub.repository.SongRepository
import com.example.songhub.ui.viewmodel.SongViewModel

val appModule = module {
    viewModelOf(::SongViewModel)
}

val storageModule = module {
    singleOf(::SongRepository)
    single {
        Room.databaseBuilder(
            androidContext(),
            SongDatabase::class.java, "songs-database.db"
        ).build()
    }
    single {
        get<SongDatabase>().songDao()
    }

}