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
    var shoppingList = database.searchShopping()

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
            insert(Skein("DMC-01",1,"LightBlue1","1","#03A9F4", 0,true))
            insert(Skein("DMC-02",2,"DarkBlue5","5","#023A6C", 2, true))
            insert(Skein("DMC-03",3,"averageBlue8","8","#1B7FAC", 8, true))
            insert(Skein("DMC-04",4,"LightRed2","2","#FF4D4D", 4, inShoppingCart = true))
            insert(Skein("DMC-05",5,"DarkRed6","6","#780000", 6, inShoppingCart = true))
            insert(Skein("DMC-06",6,"AverageRed9","9","#C80000", 7, inShoppingCart = true))
            insert(Skein("DMC-07",7,"LightGreen3","3","#58FF87", 0))
            insert(Skein("DMC-08",8,"AverageGreen10","10","#31B155", 0))
            insert(Skein("DMC-09",9,"DarkGreen7","7","#045C1D", 0))
            insert(Skein("DMC-10",10,"Black4","4","#FF000000", 0))

        }
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Skein>> {
        return database.searchDatabase(searchQuery).asLiveData()
    }


//GOTTA SLAP THIS SHIT INTO COROUTRINES

    fun searchQueryDatabase(searchQuery: String): LiveData<List<Skein>>{
        return database.searchQueryShopping(searchQuery).asLiveData()
    }
}