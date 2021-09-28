package android.jia.stitchworks.dependencyInjection

import android.content.Context
import android.jia.stitchworks.database.SkeinDatabase
import android.jia.stitchworks.database.SkeinDatabaseDao
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
    fun provideSkeinDatabase(@ApplicationContext context: Context): SkeinDatabase {
        return SkeinDatabase.getInstance(context)
    }

    @Provides
    fun provideSkeinDao(skeinDatabase: SkeinDatabase): SkeinDatabaseDao {
        return skeinDatabase.skeinDatabaseDao
    }
}