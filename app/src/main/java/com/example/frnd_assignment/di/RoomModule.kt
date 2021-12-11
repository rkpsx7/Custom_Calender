package com.example.frnd_assignment.di

import android.content.Context
import androidx.room.Room
import com.example.frnd_assignment.roomDB.AppRoomDatabase
import com.example.frnd_assignment.roomDB.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {
    @Singleton
    @Provides
    fun providesTaskDao(db: AppRoomDatabase): TaskDao {
        return db.getTaskDao()
    }

    @Singleton
    @Provides
    fun provideAppRoomDb(@ApplicationContext appContext: Context): AppRoomDatabase {
        return Room.databaseBuilder(
            appContext,
            AppRoomDatabase::class.java,
            "TaskDB"
        ).build()
    }
}