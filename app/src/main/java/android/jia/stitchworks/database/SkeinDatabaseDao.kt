package android.jia.stitchworks.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SkeinDatabaseDao {

    @Insert
    suspend fun insert(skein: Skein)



    //yeh we'll do this out as we can actually see shit

}