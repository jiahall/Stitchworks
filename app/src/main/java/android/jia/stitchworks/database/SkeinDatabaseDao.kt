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

    @Query("SELECT * FROM skein_checklist WHERE amount= 0 AND (brandNumber LIKE '%' || :searchQuery || '%' OR thread_name LIKE '%' || :searchQuery || '%') ORDER BY skein_number ASC")
    fun getUnowned(searchQuery: String): Flow<List<Skein>>

    //we'll delete this when we have the other filter options set up
    @Query("SELECT * FROM skein_checklist WHERE amount >= 1  ORDER BY skein_number ASC")
    fun getOwned2(): Flow<List<Skein>>

    @Insert
    suspend fun insertAll(skein: List<Skein>)

    //youll need this for the skeinDetail page
    @Query("SELECT * FROM skein_checklist WHERE brandNumber = :key")
    suspend fun get(key: String): Skein

    @Query("UPDATE skein_checklist SET amount = 1 WHERE brandNumber = :brandNumber")
    suspend fun addThread(brandNumber: String)

    @Query("UPDATE skein_checklist SET amount = 1 WHERE skein_number BETWEEN :skeinNumber AND :skeinNumber1")
    suspend fun addThreadRange(skeinNumber: Int, skeinNumber1: Int)

    @Query("UPDATE skein_checklist SET amount = 0 WHERE brandNumber = :brandNumber")
    suspend fun removeThread(brandNumber: String)

    @Query("UPDATE skein_checklist SET amount = 0 WHERE skein_number BETWEEN :skeinNumber AND :skeinNumber1")
    suspend fun removeThreadRange(skeinNumber: Int, skeinNumber1: Int)

}