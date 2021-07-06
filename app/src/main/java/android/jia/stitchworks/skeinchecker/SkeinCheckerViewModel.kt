package android.jia.stitchworks.skeinchecker

import android.app.Application
import android.jia.stitchworks.database.Skein
import android.jia.stitchworks.database.SkeinDatabaseDao
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.sql.DataSource

class SkeinCheckerViewModel(dataSource: SkeinDatabaseDao,
application: Application) : ViewModel() {

    val database = dataSource

     var threads = database.getAllThreads()

    private suspend fun insert(skein: Skein){
        database.insert(skein)
    }

    private suspend fun delete(name: String){
        database.delete(name)
    }

    init {
        //startData()
    }

     private fun startData() {
        viewModelScope.launch {
            insert(Skein("DMC-01","01","ClearBlue1","1","20", 0,true))
            insert(Skein("DMC-02","02","DustyBlue5","5","20", 2, true))
            insert(Skein("DMC-03","03","averageBlue8","8","20", 8, true))
            insert(Skein("DMC-04","04","ClearRed2","2","20", 4, inShoppingCart = true))
            insert(Skein("DMC-05","05","DustyRed6","6","20", 6, inShoppingCart = true))
            insert(Skein("DMC-06","06","AverageRed9","9","20", 7, inShoppingCart = true))
            insert(Skein("DMC-07","07","ClearGreen3","3","20", 0))
            insert(Skein("DMC-08","08","AverageGreen10","10","20", 0))
            insert(Skein("DMC-09","09","DustyGreen7","7","20", 0))
            insert(Skein("DMC-10","10","ClearBlack4","4","20", 0))

        }
    }
}