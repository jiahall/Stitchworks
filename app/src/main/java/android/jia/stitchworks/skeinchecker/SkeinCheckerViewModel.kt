package android.jia.stitchworks.skeinchecker

import android.jia.stitchworks.data.Skein
import android.jia.stitchworks.data.SkeinDao
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class SkeinCheckerViewModel @Inject internal constructor(private val dataSource: SkeinDao) :
    ViewModel() {


    val searchQuery = MutableStateFlow("")
    val filterSkein = MutableStateFlow(FilterSkein.BY_ALL)

    private val skeinFlow = combine(searchQuery, filterSkein)
    { query, filterSkein -> Pair(query, filterSkein) }
        .flatMapLatest { (query, filterSkein) -> dataSource.getSkeins(query, filterSkein) }

    // Internally we use mutablelive data to update the list with live values
    private val _threads = skeinFlow

    //the external livedata threads is immutable, this is because we don't want it to be edited outside of the view model
    val threads: LiveData<List<Skein>>
        //overrid the get function to return the list in _thread
        get() = _threads.asLiveData()




}

enum class FilterSkein { BY_OWNED, BY_UNOWNED, BY_ALL, BY_INUSE, BY_SHOPPINGCART }