package android.jia.stitchworks.skeinchecker

import android.graphics.Color
import android.jia.stitchworks.R
import android.jia.stitchworks.database.Skein
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

class SkeinAdapter: RecyclerView.Adapter<ViewHolder>(), Filterable {
    var data = listOf<Skein>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType:
    Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }


}


class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView){

    val brandNumber : TextView =itemView.findViewById(R.id.skein_list_brand_number)
    val threadName: TextView = itemView.findViewById(R.id.skein_list_name)
    val colourValue: ImageView = itemView.findViewById(R.id.skein_list_colour_value)
    var amountOfSkeins: TextView = itemView.findViewById(R.id.skein_list_amount)
    var ownedYes: ImageView = itemView.findViewById(R.id.skein_list_owned_yes)
    var inShoppingCart: ImageView = itemView.findViewById(R.id.in_shopping_cart)







     fun bind(item: Skein){

        brandNumber.text =item.brandNumber
        threadName.text = item.threadName
         try {
             colourValue.setBackgroundColor(Color.parseColor(item.colourValue))
         }catch (e:IllegalArgumentException){
             Log.i("SkeinAdapter", "hi jia it was ${brandNumber.text}")

         }
         amountOfSkeins.text = item.amountOfSkeins.toString()
         if (!item.inUse){
             ownedYes.isGone = true
         }
         if (item.inShoppingCart){
             inShoppingCart.isGone = false
         }

    }
    //The from() function needs to be in a companion object so it can be called on the ViewHolder class, not called on a ViewHolder instance.
    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater
                .inflate(R.layout.list_item_skein, parent, false)
            return ViewHolder(view)
        }
    }
}
