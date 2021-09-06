package android.jia.stitchworks.database


import android.jia.stitchworks.skeinadder.FilterSkeinOption
import android.jia.stitchworks.skeinchecker.FilterSkein
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SkeinDatabaseDao {

    fun getSkeins(query: String, filterSkein: FilterSkein): Flow<List<Skein>> =
        when (filterSkein) {
            FilterSkein.BY_ALL -> getAll(query)
            FilterSkein.BY_OWNED -> getOwned(query)
            FilterSkein.BY_UNOWNED -> getUnowned(query)
            else -> getOwned2()
        }

    fun getSkeinsOptions(query: String, filterSkeinOption: FilterSkeinOption): Flow<List<Skein>> =
        when (filterSkeinOption) {
            FilterSkeinOption.ADD_RANGE -> getUnowned(query)
            FilterSkeinOption.ADD_ONE -> getUnowned(query)
            FilterSkeinOption.REMOVE_ONE -> getOwned(query)
            FilterSkeinOption.REMOVE_RANGE -> getOwned(query)


        }

    @Query("SELECT * FROM skein_checklist WHERE brandNumber LIKE '%' || :searchQuery || '%' OR thread_name LIKE '%' || :searchQuery || '%' ORDER BY skein_number ASC")
    fun getAll(searchQuery: String): Flow<List<Skein>>

    @Query("SELECT * FROM skein_checklist WHERE amount >= 1 AND (brandNumber LIKE '%' || :searchQuery || '%' OR thread_name LIKE '%' || :searchQuery || '%') ORDER BY skein_number ASC")
    fun getOwned(searchQuery: String): Flow<List<Skein>>

    @Query("SELECT * FROM skein_checklist WHERE amount >= 1  ORDER BY skein_number ASC")
    fun getOwned2(): Flow<List<Skein>>

    @Query("SELECT * FROM skein_checklist WHERE amount= 0 AND (brandNumber LIKE '%' || :searchQuery || '%' OR thread_name LIKE '%' || :searchQuery || '%') ORDER BY skein_number ASC")
    fun getUnowned(searchQuery: String): Flow<List<Skein>>

    @Query("SELECT * FROM skein_checklist WHERE amount= 0 ORDER BY skein_number ASC")
    fun getUnowned2(): Flow<List<Skein>>

    @Insert
    suspend fun insert(skein: Skein)

    @Insert
    suspend fun insertAll(skein: List<Skein>)

    @Query("SELECT * FROM skein_checklist WHERE brandNumber = :key")
    suspend fun get(key: String): Skein

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

    @Query("UPDATE skein_checklist SET amount = 1 WHERE brandNumber = :brandNumber")
    suspend fun addThread(brandNumber: String)

    @Query("UPDATE skein_checklist SET amount = 0 WHERE brandNumber = :brandNumber")
    suspend fun removeThread(brandNumber: String)


}