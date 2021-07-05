package android.jia.stitchworks.skeinchecker

import android.jia.stitchworks.R
import android.jia.stitchworks.database.Skein
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SkeinAdapter: RecyclerView.Adapter<ViewHolder>() {
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


}


class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
    val skeinBrand : TextView =itemView.findViewById(R.id.basic_skein_list_name)

    val skeinNumber: TextView = itemView.findViewById(R.id.basic_skein_list_number)

    public fun bind(item: Skein){
        val res = itemView.context.resources
        skeinBrand.text =
            item.brandNo
        skeinNumber.text = item.skeinNo
    }
    //The from() function needs to be in a companion object so it can be called on the ViewHolder class, not called on a ViewHolder instance.
    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater
                .inflate(R.layout.item_skein_basic, parent, false)
            return ViewHolder(view)
        }
    }
}
