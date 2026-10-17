package com.obzrai.observer.core.di

import android.content.Context
import androidx.room.Room
import com.obzrai.observer.core.storage.ObserverDatabase
import com.obzrai.observer.core.storage.dao.InterestTopicDao
import com.obzrai.observer.core.storage.dao.MediaAssetDao
import com.obzrai.observer.core.storage.dao.SavedItemDao
import com.obzrai.observer.feature.capture.DefaultMediaProjectionGateway
import com.obzrai.observer.feature.capture.MediaProjectionGateway
import com.obzrai.observer.feature.overlay.DefaultOverlayController
import com.obzrai.observer.feature.overlay.OverlayController
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): ObserverDatabase =
        Room.databaseBuilder(
            context,
            ObserverDatabase::class.java,
            "ai_observer.db",
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideSavedItemDao(db: ObserverDatabase): SavedItemDao = db.savedItemDao()

    @Provides
    fun provideInterestTopicDao(db: ObserverDatabase): InterestTopicDao = db.interestTopicDao()

    @Provides
    fun provideMediaAssetDao(db: ObserverDatabase): MediaAssetDao = db.mediaAssetDao()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class BindingsModule {
    @Binds
    @Singleton
    abstract fun bindMediaProjectionGateway(
        impl: DefaultMediaProjectionGateway,
    ): MediaProjectionGateway

    @Binds
    @Singleton
    abstract fun bindOverlayController(
        impl: DefaultOverlayController,
    ): OverlayController
}
