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

    @Update
    suspend fun update(skein: Skein)

    @Query("SELECT * from skein_checklist WHERE in_use= 'Yes'")
    suspend fun getShoppingList(): LiveData<List<Skein>>

    @Query("SELECT * from skein_checklist ORDER BY family_number DESC")
    suspend fun orderByFamily(): LiveData<List<Skein>>

    @Query("SELECT * FROM skein_checklist WHERE amount =+ 1")
    suspend fun getOwnedSkeins(): LiveData<List<Skein>>

    @Query("SELECT * FROM skein_checklist ORDER BY brandNo DESC")
    suspend fun getAllDMC(): LiveData<List<Skein>>

    //yeh we'll do this out as we can actually see shit

}