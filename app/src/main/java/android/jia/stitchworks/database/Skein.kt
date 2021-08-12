package android.jia.stitchworks.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "skein_checklist")
data class Skein(
    @PrimaryKey(autoGenerate = false)
    //we have the brand and the number for it to be unique
    val brandNumber: String,


    @ColumnInfo(name= "skein_number")
    val skeinNumber: Int,
    

       @ColumnInfo(name = "thread_name")
      val threadName: String,

      @ColumnInfo(name = "family_number")
      val familyNumber: String,

      @ColumnInfo(name = "colour_value")
      val colourValue: String,
    //gotta change that to hexColour

      @ColumnInfo(name="amount")
      var amountOfSkeins: Int,

      @ColumnInfo(name="in_use")
      var inUse: Boolean= false,

      @ColumnInfo(name = "shopping_cart")
      var inShoppingCart: Boolean= false

  )
