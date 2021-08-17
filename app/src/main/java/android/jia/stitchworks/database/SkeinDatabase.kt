package android.jia.stitchworks.database

import android.content.Context
import android.content.res.Resources
import android.jia.stitchworks.SKEIN_DATA_FILENAME
import android.jia.stitchworks.SeedDatabaseWorker
import android.jia.stitchworks.SeedDatabaseWorker.Companion.KEY_FILENAME
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf

@Database(entities = [Skein::class], version = 1, exportSchema = true)
abstract class SkeinDatabase: RoomDatabase() {

    abstract val skeinDatabaseDao: SkeinDatabaseDao

    companion object{

        @Volatile
        private var INSTANCE: SkeinDatabase? = null

        fun getInstance(context: Context): SkeinDatabase{
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SkeinDatabase::class.java,
                        "skein_history_database"
                    ).addCallback(object : RoomDatabase.Callback(){
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>()
                                .setInputData(workDataOf(KEY_FILENAME to SKEIN_DATA_FILENAME))
                                .build()
                            WorkManager.getInstance(context).enqueue(request)
                        }
                    }).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }

        }



    }
}



