package android.jia.stitchworks.database

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

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

    @Query("DELETE  FROM skein_checklist")
    suspend fun deleteAll()

    @Query("SELECT * FROM skein_checklist WHERE brandNumber LIKE :searchQuery")
        fun searchDatabase(searchQuery: String): Flow<List<Skein>>

    @Query("SELECT * FROM skein_checklist WHERE shopping_cart = '1' AND brandNumber LIKE :searchQuery")
    fun searchQueryShopping(searchQuery: String): Flow<List<Skein>>

        @Query("SELECT * FROM skein_checklist WHERE shopping_cart = '1' ")
        fun searchShopping(): LiveData<List<Skein>>




}