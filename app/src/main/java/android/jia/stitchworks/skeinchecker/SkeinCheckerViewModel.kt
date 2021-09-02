package android.jia.stitchworks.skeinchecker

import android.app.Application
import android.jia.stitchworks.database.Skein
import android.jia.stitchworks.database.SkeinDatabaseDao
import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.sql.DataSource

class SkeinCheckerViewModel(dataSource: SkeinDatabaseDao) : ViewModel() {

    val database = dataSource

    // Internally we use mutablelive data to update the list with live values
    private val _threads = MutableLiveData<List<Skein>>()

    //the external livedata threads is immutable, this is because we don't want it to be edited outside of the view model
    val threads: LiveData<List<Skein>>
        //overrid the get function to return the list in _thread
        get() = _threads


    val filterSkein = MutableStateFlow(FilterSkein.BY_ALL)
    val searchQuery = MutableStateFlow("")

    private val skeinFlow = searchQuery.flatMapLatest { database.getAll2(it) }

    val test = skeinFlow.asLiveData()


    fun searchOwned(query: String): LiveData<List<Skein>> {
        val searchQuery = "%$query%"
        return database.searchOwned(searchQuery).asLiveData()
    }

    fun getOwned() {
        viewModelScope.launch {
            _threads.value = database.getOwned()
        }
    }

    fun updateGetAll() {
        viewModelScope.launch {
            _threads.value = database.getAllThreads()
        }
    }

    fun getUnowned() {
        viewModelScope.launch {
            _threads.value = database.getUnowned()
        }
    }


}

enum class FilterSkein { BY_OWNED, BY_UNOWNED, BY_ALL, BY_INUSE, BY_SHOPPINGCART }