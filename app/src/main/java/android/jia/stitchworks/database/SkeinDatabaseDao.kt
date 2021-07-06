package android.jia.stitchworks.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SkeinDatabaseDao {

    @Insert
     suspend fun insert(skein: Skein)

     @Query("SELECT * FROM skein_checklist WHERE brandNumber = :key")
    suspend fun get(key: String): Skein

    @Query("SELECT * FROM skein_checklist")
   fun getAllThreads(): LiveData<List<Skein>>

    @Query("DELETE FROM skein_checklist Where brandNumber = :key")
    suspend fun delete(key: String)

    //yeh we'll do this out as we can actually see shit

}