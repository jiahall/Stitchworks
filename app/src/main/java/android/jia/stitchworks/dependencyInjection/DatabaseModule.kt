package android.jia.stitchworks.dependencyInjection

import android.content.Context
import android.jia.stitchworks.data.AppDatabase
import android.jia.stitchworks.data.SkeinDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideSkeinDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideSkeinDao(appDatabase: AppDatabase): SkeinDao {
        return appDatabase.skeinDao
    }
}