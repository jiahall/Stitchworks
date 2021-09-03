package android.jia.stitchworks.skeinchecker

import android.app.Application
import android.jia.stitchworks.database.Skein
import android.jia.stitchworks.database.SkeinDatabaseDao
import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest

class SkeinCheckerViewModel(dataSource: SkeinDatabaseDao) : ViewModel() {

    val database = dataSource

    val searchQuery = MutableStateFlow("")
    val filterSkein = MutableStateFlow(FilterSkein.BY_ALL)

    private val skeinFlow = combine(searchQuery, filterSkein)
    { query, filterSkein -> Pair(query, filterSkein) }
        .flatMapLatest { (query, filterSkein) -> database.getSkeins(query, filterSkein) }

    // Internally we use mutablelive data to update the list with live values
    private val _threads = skeinFlow


    //the external livedata threads is immutable, this is because we don't want it to be edited outside of the view model
    val threads: LiveData<List<Skein>>
        //overrid the get function to return the list in _thread
        get() = _threads.asLiveData()




}

enum class FilterSkein { BY_OWNED, BY_UNOWNED, BY_ALL, BY_INUSE, BY_SHOPPINGCART }