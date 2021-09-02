package android.jia.stitchworks.skeinadder

import android.jia.stitchworks.database.Skein
import android.jia.stitchworks.database.SkeinDatabaseDao
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class SkeinAdderViewModel(dataSource: SkeinDatabaseDao) : ViewModel() {

    val database = dataSource

    // Internally we use mutablelive data to update the list with live values
    private val _threads = MutableLiveData<List<Skein>>()

    //the external livedata threads is immutable, this is because we don't want it to be edited outside of the view model
    val threads: LiveData<List<Skein>>
        //override the get function to return the list in _thread
        get() = _threads

    init {
        getUnowned()
    }


    fun getUnowned() {
        viewModelScope.launch {
            _threads.value = database.getUnowned()
        }
    }

    fun addThread(brandNumber: String) {
        viewModelScope.launch {
            database.addThread(brandNumber)
            _threads.value = database.getUnowned()
        }

    }

    fun removeThread(brandNumber: String) {
        viewModelScope.launch {
            database.removeThread(brandNumber)
            _threads.value = database.getOwned()
        }

    }

    fun searchDatabase(value: Int, query: String): LiveData<List<Skein>> {
        val searchQuery = "%$query%"

        return when (value) {
            1 -> database.searchUnownedDatabase(searchQuery).asLiveData()
            2 -> database.searchOwnedDatabase(searchQuery).asLiveData()
            else -> database.searchDatabase(searchQuery).asLiveData()
        }

    }

    fun getOwned() {
        viewModelScope.launch {
            _threads.value = database.getOwned()
        }
    }
}