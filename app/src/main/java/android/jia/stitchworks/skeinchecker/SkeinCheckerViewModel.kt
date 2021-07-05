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
        startData()
    }

     private fun startData() {
        viewModelScope.launch {


            /*insert(Skein("DMC-011", "011"))
            insert(Skein("DMC-021", "021"))
            insert(Skein("DMC-031", "031"))
            insert(Skein("DMC-041", "041"))
            insert(Skein("DMC-051", "051"))
            insert(Skein("DMC-061", "061"))
            insert(Skein("DMC-071", "071"))
            insert(Skein("DMC-0151", "0115"))
            insert(Skein("DMC-0251", "0215"))
            insert(Skein("DMC-0351", "0315"))
            insert(Skein("DMC-0451", "0415"))
            insert(Skein("DMC-0551", "0515"))
            insert(Skein("DMC-0651", "0615"))
            insert(Skein("DMC-0751", "0715"))*/
        }
    }
}