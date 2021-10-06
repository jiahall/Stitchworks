package android.jia.stitchworks.skeinadder

import android.jia.stitchworks.data.SkeinDao
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SkeinAdderViewModelFactory(
    private val dataSource: SkeinDao

) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SkeinAdderViewModel::class.java)) {
            return SkeinAdderViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
