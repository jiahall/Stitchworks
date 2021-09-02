package android.jia.stitchworks.database


import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SkeinDatabaseDao {

    @Insert
     suspend fun insert(skein: Skein)

    @Insert
    suspend fun insertAll(skein: List<Skein>)

    @Query("SELECT * FROM skein_checklist WHERE brandNumber = :key")
    suspend fun get(key: String): Skein


    @Query("SELECT * FROM skein_checklist WHERE amount >= 1 ORDER BY skein_number ASC")
    suspend fun getOwned(): List<Skein>

    @Query("SELECT * FROM skein_checklist WHERE amount >= 1 ORDER BY skein_number ASC")
    fun getOwned2(): Flow<List<Skein>>

    @Query("SELECT * FROM skein_checklist ORDER BY skein_number ASC")
    suspend fun getAllThreads(): List<Skein>

    @Query("SELECT * FROM skein_checklist WHERE amount = 0 ORDER BY skein_number ASC")
    suspend fun getUnowned(): List<Skein>

    @Query("DELETE FROM skein_checklist Where brandNumber = :key")
    suspend fun delete(key: String)

    @Query("DELETE  FROM skein_checklist")
    suspend fun deleteAll()

    @Query("SELECT * FROM skein_checklist WHERE brandNumber LIKE :searchQuery OR thread_name LIKE :searchQuery ORDER BY skein_number ASC")
    fun searchDatabase(searchQuery: String): Flow<List<Skein>>

    @Query("SELECT * FROM skein_checklist WHERE amount = 1 AND brandNumber LIKE :searchQuery OR thread_name LIKE :searchQuery ORDER BY skein_number ASC")
    fun searchOwned(searchQuery: String): Flow<List<Skein>>

    @Query("UPDATE skein_checklist SET amount = 1 WHERE brandNumber = :brandNumber")
    suspend fun addThread(brandNumber: String)

    @Query("UPDATE skein_checklist SET amount = 0 WHERE brandNumber = :brandNumber")
    suspend fun removeThread(brandNumber: String)

    @Query("SELECT * FROM skein_checklist WHERE amount= 0 AND brandNumber LIKE :searchQuery OR thread_name LIKE :searchQuery ORDER BY skein_number ASC")
    fun searchUnownedDatabase(searchQuery: String): Flow<List<Skein>>

    @Query("SELECT * FROM skein_checklist WHERE amount >= 1 AND brandNumber LIKE :searchQuery OR thread_name LIKE :searchQuery ORDER BY skein_number ASC")
    fun searchOwnedDatabase(searchQuery: String): Flow<List<Skein>>

    @Query("SELECT * FROM skein_checklist WHERE brandNumber LIKE '%' || :searchQuery || '%' ORDER BY skein_number ASC")
    fun getAll2(searchQuery: String): Flow<List<Skein>>


}