package android.jia.stitchworks.data

import android.net.Uri
import androidx.room.PrimaryKey
import java.util.*

data class Project(
    @PrimaryKey(autoGenerate = false)
    val projectName: String,

    var startDate: Date,

    var endDate: Date,

    var mainPicture: Uri

)