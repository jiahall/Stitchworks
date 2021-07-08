package android.jia.stitchworks.skeinchecker

import android.app.Application
import android.jia.stitchworks.database.Skein
import android.jia.stitchworks.database.SkeinDatabaseDao
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import javax.sql.DataSource

class SkeinCheckerViewModel(dataSource: SkeinDatabaseDao) : ViewModel() {

    val database = dataSource

     var threads = database.getAllThreads()

    private suspend fun insert(skein: Skein){
        database.insert(skein)
    }

    private suspend fun delete(name: String){
        database.delete(name)
    }

    private suspend fun deleteAll(){
        database.deleteAll()
    }

    init {
        //startData()
    }

     private fun startData() {
        viewModelScope.launch {
            deleteAll()
            insert(Skein("DMC-01","01","LightBlue1","1","#03A9F4", 0,true))
            insert(Skein("DMC-02","02","DarkBlue5","5","#023A6C", 2, true))
            insert(Skein("DMC-03","03","averageBlue8","8","#1B7FAC", 8, true))
            insert(Skein("DMC-04","04","LightRed2","2","#FF4D4D", 4, inShoppingCart = true))
            insert(Skein("DMC-05","05","DarkRed6","6","#780000", 6, inShoppingCart = true))
            insert(Skein("DMC-06","06","AverageRed9","9","#C80000", 7, inShoppingCart = true))
            insert(Skein("DMC-07","07","LightGreen3","3","#58FF87", 0))
            insert(Skein("DMC-08","08","AverageGreen10","10","#31B155", 0))
            insert(Skein("DMC-09","09","DarkGreen7","7","#045C1D", 0))
            insert(Skein("DMC-10","10","Black4","4","#FF000000", 0))

        }
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Skein>>{
        return database.searchDatabase(searchQuery).asLiveData()
    }
}