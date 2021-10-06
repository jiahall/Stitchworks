package android.jia.stitchworks.skeinadder

import android.jia.stitchworks.data.Skein
import android.jia.stitchworks.data.SkeinDao
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

const val SINGLE_THREAD = "NONE"

@HiltViewModel
class SkeinAdderViewModel @Inject internal constructor(
    private val dataSource: SkeinDao
) : ViewModel() {


    private var _undoList = MutableLiveData<MutableList<Triple<UndoEnum, String, String>?>>()
    val undoList: LiveData<MutableList<Triple<UndoEnum, String, String>?>>
        get() = _undoList


    init {
        _undoList.value = mutableListOf()
    }

    var startSkein = MutableLiveData<Skein?>()
    var endSkein = MutableLiveData<Skein?>()

    val searchQuery = MutableStateFlow("")
    val filterSkeinOption = MutableStateFlow(FilterSkeinOption.ADD_ONE)


    private val skeinFlow = combine(searchQuery, filterSkeinOption)
    { query, filterSkeinOption -> Pair(query, filterSkeinOption) }
        .flatMapLatest { (query, filterSkeinOption) ->
            dataSource.getSkeinsOptions(
                query,
                filterSkeinOption
            )
        }


    private val _threads = skeinFlow

    //the external livedata threads is immutable, this is because we don't want it to be edited outside of the view model
    val threads: LiveData<List<Skein>>
        //override the get function to return the list in _thread
        get() = _threads.asLiveData()

    private val _submitMessage = MutableLiveData<Boolean>()
    val submitMessage: LiveData<Boolean>
        get() = _submitMessage

    private val _clearThreads = MutableLiveData<Boolean>()
    val clearThreads: LiveData<Boolean>
        get() = _clearThreads

    private val _clearStartQuery = MutableLiveData<Boolean>()
    val clearStartQuery: LiveData<Boolean>
        get() = _clearStartQuery

    private val _clearEndQuery = MutableLiveData<Boolean>()
    val clearEndQuery: LiveData<Boolean>
        get() = _clearEndQuery


    fun passThreads(skein: Skein) {
        when (filterSkeinOption.value) {
            FilterSkeinOption.ADD_ONE -> {
                _undoList.value?.add(Triple(UndoEnum.ADD_ONE, skein.brandNumber, SINGLE_THREAD))
                _undoList.notifyObserver()
                addThread(skein.brandNumber)
            }
            FilterSkeinOption.REMOVE_ONE -> {
                _undoList.value?.add(Triple(UndoEnum.REMOVE_ONE, skein.brandNumber, SINGLE_THREAD))
                _undoList.notifyObserver()
                removeThread(skein.brandNumber)
            }

            else -> throw IllegalStateException("Invalid skein param value")
        }

    }

    fun addThread(brandNumber: String) {
        viewModelScope.launch {
            dataSource.addThread(brandNumber)
        }
    }

    fun removeThread(brandNumber: String) {
        viewModelScope.launch {
            dataSource.removeThread(brandNumber)
        }
    }

    fun addRange(skeinNumber: Int?, skeinNumber1: Int?) {
        viewModelScope.launch {
            if (skeinNumber != null) {
                if (skeinNumber1 != null) {
                    dataSource.addThreadRange(skeinNumber, skeinNumber1)
                }
            }
        }
    }

    fun removeRange(skeinNumber: Int?, skeinNumber1: Int?) {

        viewModelScope.launch {
            if (skeinNumber != null) {
                if (skeinNumber1 != null) {
                    dataSource.removeThreadRange(skeinNumber, skeinNumber1)
                }
            }
        }
    }

    fun onUndo() {
        val temper = _undoList.value!![_undoList.value!!.size.minus(1)]

        when (temper!!.first) {
            UndoEnum.ADD_ONE -> {
                removeThread(temper.second)
                _undoList.value?.removeLast()
                _undoList.notifyObserver()
            }

            UndoEnum.ADD_RANGE -> {
                removeRange(temper.second.toInt(), temper.third.toInt())
                // _undoList.value = null
                _undoList.value?.removeLast()
                _undoList.notifyObserver()
            }
            UndoEnum.REMOVE_ONE -> {
                addThread(temper.second)
                _undoList.value?.removeLast()
                _undoList.notifyObserver()
            }

            UndoEnum.REMOVE_RANGE -> {
                addRange(temper.second.toInt(), temper.third.toInt())

                _undoList.value?.removeLast()
                _undoList.notifyObserver()
            }
        }
    }

    fun onSubmit() {

        //AJIA SPLIT THIS INTO 2 METHODS
        if (startSkein.value == null || endSkein.value == null) {
            _submitMessage.value = true
        } else {
            val higher: Int
            val lower: Int

            if (startSkein.value!!.skeinNumber >= endSkein.value!!.skeinNumber) {
                higher = startSkein.value!!.skeinNumber
                lower = endSkein.value!!.skeinNumber

            } else {
                higher = endSkein.value!!.skeinNumber
                lower = startSkein.value!!.skeinNumber
            }


            when (filterSkeinOption.value) {

                FilterSkeinOption.ADD_RANGE -> {
                    _undoList.value?.add(
                        Triple(
                            UndoEnum.ADD_RANGE,
                            lower.toString(),
                            higher.toString()
                        )
                    )
                    _undoList.notifyObserver()
                    addRange(lower, higher)
                    _clearThreads.value = true
                }

                FilterSkeinOption.REMOVE_RANGE -> {
                    _undoList.value?.add(
                        Triple(
                            UndoEnum.REMOVE_RANGE,
                            lower.toString(),
                            higher.toString()
                        )
                    )
                    _undoList.notifyObserver()
                    removeRange(lower, higher)
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

    fun resetQuery(query: Int) {
        if (query.equals(1)) {
            _clearStartQuery.value = true
            _clearStartQuery.value = false
        } else {
            _clearEndQuery.value = true
            _clearEndQuery.value = false
        }
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

    //livedata only updates observers when set/post value is called,
    // meaning that if the LiveData's underlying data structure has changed e.g adding to a list, anything observing it won't be noticed
    //this is why we have this function
    fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }
}


enum class FilterSkeinOption { ADD_ONE, ADD_RANGE, REMOVE_ONE, REMOVE_RANGE }
enum class UndoEnum { ADD_ONE, ADD_RANGE, REMOVE_ONE, REMOVE_RANGE }