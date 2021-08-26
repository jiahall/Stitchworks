package android.jia.stitchworks.skeinadder

import android.jia.stitchworks.database.SkeinDatabaseDao
import android.jia.stitchworks.skeinchecker.SkeinCheckerViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SkeinAdderViewModelFactory(
    private val dataSource: SkeinDatabaseDao

) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SkeinAdderViewModel::class.java)) {
            return SkeinAdderViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
