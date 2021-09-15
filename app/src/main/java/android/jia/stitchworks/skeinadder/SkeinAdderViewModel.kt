package android.jia.stitchworks.skeinadder

import android.app.PendingIntent.getActivity
import android.jia.stitchworks.database.Skein
import android.jia.stitchworks.database.SkeinDatabaseDao
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class SkeinAdderViewModel(dataSource: SkeinDatabaseDao) : ViewModel() {

    val database = dataSource

    var startSkein = MutableLiveData<Skein?>()
    val endSkein = MutableLiveData<Skein?>()

    val searchQuery = MutableStateFlow("")
    val filterSkeinOption = MutableStateFlow(FilterSkeinOption.ADD_ONE)


    private val skeinFlow = combine(searchQuery, filterSkeinOption)
    { query, filterSkeinOption -> Pair(query, filterSkeinOption) }
        .flatMapLatest { (query, filterSkeinOption) ->
            database.getSkeinsOptions(
                query,
                filterSkeinOption
            )
        }

    // Internally we use mutablelive data to update the list with live values
    private val _threads = skeinFlow

    //the external livedata threads is immutable, this is because we don't want it to be edited outside of the view model
    val threads: LiveData<List<Skein>>
        //overrid the get function to return the list in _thread
        get() = _threads.asLiveData()

    private val _submitMessage = MutableLiveData<Boolean>()
    val submitMessage: LiveData<Boolean>
        get() = _submitMessage

    private val _clearThreads = MutableLiveData<Boolean>()
    val clearThreads: LiveData<Boolean>
        get() = _clearThreads


    fun addThread(brandNumber: String) {

        viewModelScope.launch {
            database.addThread(brandNumber)

        }

    }

    fun passThreads(skein: Skein) {
        when (filterSkeinOption.value) {
            FilterSkeinOption.ADD_ONE -> addThread(skein.brandNumber)
            FilterSkeinOption.REMOVE_ONE -> removeThread(skein.brandNumber)

            else -> throw IllegalStateException("Invalid skein param value")
        }

    }

    fun addRange(skeinNumber: Int?, skeinNumber1: Int?) {
        viewModelScope.launch {
            if (skeinNumber != null) {
                if (skeinNumber1 != null) {
                    database.addThreadRange(skeinNumber, skeinNumber1)
                }
            }
        }
    }

    fun removeThread(brandNumber: String) {
        viewModelScope.launch {
            database.removeThread(brandNumber)
        }
    }

    fun removeRange(skeinNumber: Int?, skeinNumber1: Int?) {
        viewModelScope.launch {
            if (skeinNumber != null) {
                if (skeinNumber1 != null) {
                    database.removeThreadRange(skeinNumber, skeinNumber1)
                }
            }
        }
    }

    fun onSubmit() {
        if (startSkein.value == null || endSkein.value == null) {
            _submitMessage.value = true
        } else {
            when (filterSkeinOption.value) {


                FilterSkeinOption.ADD_RANGE -> {
                    addRange(
                        startSkein.value?.skeinNumber,
                        endSkein.value?.skeinNumber
                    )
                    _clearThreads.value = true
                }

                FilterSkeinOption.REMOVE_RANGE -> {
                    removeRange(
                        startSkein.value?.skeinNumber,
                        endSkein.value?.skeinNumber
                    )
                    _clearThreads.value = true
                }

                else -> throw IllegalStateException("Invalid rating param value")
            }

        }
    }

    fun resetSubmitMessage() {
        _submitMessage.value = false
    }

    fun resetClearThreads() {
        _clearThreads.value = false
    }
    // }

    // fun searchDatabase(value: Int, query: String): LiveData<List<Skein>> {
    //    val searchQuery = "%$query%"
//
    //       return when (value) {
    //          1 -> database.searchUnownedDatabase(searchQuery).asLiveData()
    //         2 -> database.searchOwnedDatabase(searchQuery).asLiveData()
    //        else -> database.searchDatabase(searchQuery).asLiveData()
    //   }

    //}

    //fun getOwned() {
    //   viewModelScope.launch {
    //      _threads.value = database.getOwned()
    // }
    // }
}


enum class FilterSkeinOption { ADD_ONE, ADD_RANGE, REMOVE_ONE, REMOVE_RANGE }