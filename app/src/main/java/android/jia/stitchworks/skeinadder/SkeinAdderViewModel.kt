package android.jia.stitchworks.skeinadder

import android.jia.stitchworks.database.SkeinDatabaseDao
import androidx.lifecycle.ViewModel

class SkeinAdderViewModel(dataSource: SkeinDatabaseDao) : ViewModel() {
    val database = dataSource
}