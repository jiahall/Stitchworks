package android.jia.stitchworks.skeinadder

import android.graphics.Color
import android.jia.stitchworks.database.Skein
import android.jia.stitchworks.databinding.ListItemSmallSkeinBinding
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class SkeinAdderAdapter(val clickAdderListener: SkeinAdderListener) :
    ListAdapter<Skein, ViewHolder>(ViewHolder.SkeinDiffCallback()) {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(getItem(position)!!, clickAdderListener)
        holder.binding.executePendingBindings()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType:
        Int
    ): ViewHolder {
        return ViewHolder.from(parent)
    }


}

class ViewHolder private constructor(val binding: ListItemSmallSkeinBinding) :
    RecyclerView.ViewHolder(binding.root) {

//Usually this is where the viewholder defines each property because of binding they were inlined and so not needed

    fun bind(item: Skein, clickAdderListener: SkeinAdderListener) {

        binding.skein = item

        binding.clickListener = clickAdderListener

        binding.skeinListBrandNumber.text = item.brandNumber
        binding.skeinListName.text = item.threadName
        try {
            binding.skeinListColourValue.setBackgroundColor(Color.parseColor(item.colourValue))
        } catch (e: IllegalArgumentException) {
            Log.i("SkeinAdapter", "hi jia it was ${binding.skeinListBrandNumber.text}")

        }

    }

    class SkeinDiffCallback : DiffUtil.ItemCallback<Skein>() {
        override fun areItemsTheSame(oldItem: Skein, newItem: Skein): Boolean {
            return oldItem.brandNumber == newItem.brandNumber
        }

        override fun areContentsTheSame(oldItem: Skein, newItem: Skein): Boolean {
            return oldItem == newItem
        }
    }


    //The from() function needs to be in a companion object so it can be called on the ViewHolder class, not called on a ViewHolder instance.
    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemSmallSkeinBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(binding)
        }
    }
}

class SkeinAdderListener(val clickListener: (BrandNumber: String) -> Unit) {


    fun onClick(skein: Skein) = clickListener(skein.brandNumber)


}