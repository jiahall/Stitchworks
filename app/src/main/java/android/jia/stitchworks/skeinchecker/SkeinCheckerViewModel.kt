package android.jia.stitchworks.skeinchecker

import android.app.Application
import android.jia.stitchworks.database.SkeinDatabaseDao
import androidx.lifecycle.ViewModel
import javax.sql.DataSource

class SkeinCheckerViewModel(dataSource: SkeinDatabaseDao,
application: Application) : ViewModel() {

    val database = dataSource
}