package android.jia.stitchworks.skeinchecker

import android.jia.stitchworks.data.SkeinDao
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SkeinCheckerViewModelFactory(
    private val dataSource: SkeinDao

): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SkeinCheckerViewModel::class.java)) {
            return SkeinCheckerViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
