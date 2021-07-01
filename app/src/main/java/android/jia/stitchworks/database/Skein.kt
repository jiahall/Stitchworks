package android.jia.stitchworks.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "skein_checklist")
data class Skein(
    @PrimaryKey(autoGenerate = false)
    val brandNo: String,

    @ColumnInfo(name= "skein_number")
    val skeinNo: String,

    @ColumnInfo(name = "thread_name")
    val threadName: String,

    @ColumnInfo(name = "family_number")
    val familyNumber: String,

    @ColumnInfo(name = "colour")
    val colour: String,

    @ColumnInfo(name="amount")
    var amountOfSkeins: Int,

    @ColumnInfo(name="in_use")
    var inUse: String,

    @ColumnInfo(name = "shopping_list")
    var shoppingList: String

)
