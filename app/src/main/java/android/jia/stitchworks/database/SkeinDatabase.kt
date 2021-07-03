package android.jia.stitchworks.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

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
                    )//JIA GOTTA CHECK WHY YOU NEED THE HISTORY DATABASE
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }
}