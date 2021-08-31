package android.jia.stitchworks.skeinadder

import android.jia.stitchworks.database.Skein
import android.jia.stitchworks.database.SkeinDatabaseDao
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SkeinAdderViewModel(dataSource: SkeinDatabaseDao) : ViewModel() {
    val database = dataSource

    // Internally we use mutablelive data to update the list with live values
    private val _threads = MutableLiveData<List<Skein>>()

    //the external livedata threads is immutable, this is because we don't want it to be edited outside of the view model
    val threads: LiveData<List<Skein>>
        //overrid the get function to return the list in _thread
        get() = _threads

    init {
        updateGetAll()
    }

    fun updateGetAll() {
        viewModelScope.launch {
            _threads.value = database.getAllThreads()
        }
    }
}