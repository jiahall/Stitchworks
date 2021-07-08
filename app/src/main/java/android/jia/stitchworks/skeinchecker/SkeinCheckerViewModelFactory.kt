package android.jia.stitchworks.skeinchecker

import android.app.Application
import android.jia.stitchworks.database.SkeinDatabaseDao
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.sql.DataSource

class SkeinCheckerViewModelFactory(
    private val dataSource: SkeinDatabaseDao

): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SkeinCheckerViewModel::class.java)) {
            return SkeinCheckerViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
