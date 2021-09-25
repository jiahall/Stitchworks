package android.jia.stitchworks.database

import androidx.room.PrimaryKey

data class Project(
    @PrimaryKey(autoGenerate = false)
    val projectName: String,


    )